/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.sockets.chat;

import fr.insa.beuvron.cours.multiTache.sockets.INetAdressUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class Server {

    private List<GestionnaireClient> clients = new ArrayList<>();
    private LinkedList<String> toSendAll = new LinkedList<>();

    private boolean[] serverReady = new boolean[]{false};

    private InetAddress hostToConnect;
    private int portToConnect;

    public InetAddress waitForInetAddress() {
        synchronized (this.serverReady) {
            while (!this.serverReady[0]) {
                try {
                    this.serverReady.wait();
                } catch (InterruptedException ex) {
                    throw new Error("no interrupt", ex);
                }
            }
            return this.hostToConnect;
        }
    }

    public int waitForPort() {
        synchronized (this.serverReady) {
            while (!this.serverReady[0]) {
                try {
                    this.serverReady.wait();
                } catch (InterruptedException ex) {
                    throw new Error("no interrupt", ex);
                }
            }
            return this.portToConnect;
        }
    }

    public void start() {
        Connection connect = this.new Connection();
        SendAll sa = this.new SendAll();
        sa.start();
        connect.start();
    }

    public class Connection extends Thread {

        @Override
        public void run() {

            try {
                Inet4Address adr = INetAdressUtil.
                        premiereAdresseNonLoopback();
                int port = 0;
//                InetAddress adr = Inet4Address.getByName("192.168.56.1");
//                int port = 49627;
                ServerSocket ss = new ServerSocket(port, 10, adr);
                synchronized (serverReady) {
                    hostToConnect = ss.getInetAddress();
                    portToConnect = ss.getLocalPort();
                    serverReady[0] = true;
                    serverReady.notifyAll();
                }
                System.out.println("server running");
                System.out.println("host : " + ss.getInetAddress());
                System.out.println("port : " + ss.getLocalPort());

                // pbPaP
                while (true) {
                    Socket soc = ss.accept();
                    System.out.println("nouveau client : " + soc);
                    GestionnaireClient nouveau = new GestionnaireClient(soc);
                    nouveau.start();
                    synchronized (clients) {
                        clients.add(nouveau);
                    }

                }
            } catch (IOException ex) {
                throw new Error(ex);
            }

        }
    }

    public class GestionnaireClient {

        private Socket socket;
        private Reception recoit;
        private Envoie envoyeur;
        private Optional<String> nomCLient = Optional.empty();

        public String getNom() {
            if (this.nomCLient.isEmpty()) {
                return "<client sans nom : " + this.hashCode() + ">";
            } else {
                return this.nomCLient.get();
            }
        }

        public GestionnaireClient(Socket socket) {
            this.socket = socket;
            this.recoit = new Reception();
            this.envoyeur = new Envoie();

        }

        public void start() {
            this.recoit.start();
            this.envoyeur.start();
        }

        /**
         * @return the recoit
         */
        public Reception getRecoit() {
            return recoit;
        }

        /**
         * @return the envoyeur
         */
        public Envoie getEnvoyeur() {
            return envoyeur;
        }

        public class Reception extends Thread {

            @Override
            public void run() {
                try ( BufferedReader bin = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(),
                                Charset.forName("UTF8")))) {

                    String nextLine;
                    if ((nextLine = bin.readLine()) != null) {
                        System.out.println("GestionnaireClient " + getNom() + " : nom recu : " + nextLine);
                        nomCLient = Optional.of(nextLine);
                    }
                    while ((nextLine = bin.readLine()) != null) {
                        System.out.println("message recu from Client " + getNom() + " : " + nextLine);
                        synchronized (toSendAll) {
                            toSendAll.add(getNom() + " : " + nextLine);
                            System.out.println("GestionnaireClient " + getNom() + " notifyAll sur toSendAll");
                            toSendAll.notifyAll();
                        }

                    }

                } catch (IOException ex) {
                    throw new Error(ex);
                }
            }
        }

        public class Envoie extends Thread {

            public LinkedList<String> toSend = new LinkedList<>();

            public void addToSend(String mess) {
                synchronized (toSend) {
                    toSend.add(mess);
                    toSend.notifyAll();
                }
            }

            @Override
            public void run() {
                System.out.println("Sender of GestionnaireClient de " + getNom() + " started");
                try ( BufferedWriter bout = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(),
                                Charset.forName("UTF8")))) {
                    while (true) {
                        synchronized (toSend) {
                            while (!toSend.isEmpty()) {
                                String nextLine = toSend.pop();
                                System.out.println("Gestionnaire client de "
                                        + getNom() + " : sending \"" + nextLine + "\" to client");
                                bout.append(nextLine + "\n");
                                bout.flush();

                            }
                            try {
                                System.out.println("Sender of GestionnaireClient de " + getNom() + " en attente");
                                toSend.wait();
                                System.out.println("Sender of GestionnaireClient " + getNom() + " réveillé");
                            } catch (InterruptedException ex) {
                                throw new Error("unexpected : ", ex);
                            }

                        }
                    }

                } catch (IOException ex) {
                    throw new Error(ex);
                }
            }
        }
    }

    public class SendAll extends Thread {

        @Override
        public void run() {
            System.out.println("starting SendAll");
            while (true) {
                synchronized (toSendAll) {
                    while (!toSendAll.isEmpty()) {
                        String nextLine = toSendAll.pop();
                        synchronized (clients) {
                            for (GestionnaireClient unClient : clients) {
                                System.out.println("SendAll : sending \"" + nextLine + "\" to GestionnaireClient " + unClient.getNom());
                                unClient.getEnvoyeur().addToSend(nextLine);
                            }
                        }
                    }
                    try {
                        System.out.println("SendAll attends un nouveau message");
                        toSendAll.wait();
                        System.out.println("SendAll réveillé");
                    } catch (InterruptedException ex) {
                        throw new Error(ex);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }

}
