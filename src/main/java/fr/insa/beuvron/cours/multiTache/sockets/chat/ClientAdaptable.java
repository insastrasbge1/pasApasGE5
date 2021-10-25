/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.sockets.chat;

import fr.insa.beuvron.utils.ConsoleFdB;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 *
 * @author francois
 */
public abstract class ClientAdaptable {

    public abstract void gereNouveauMessage(String mess);

    private Socket socketClient;
    private EcouteurClient ecouteur;
    private SenderClient sender;
    private LinkedList<String> toSend = new LinkedList<>();

    public ClientAdaptable(String host, int port, String nom) {
        try {
            this.socketClient = new Socket(host, port);
            BufferedWriter bout = new BufferedWriter(
                    new OutputStreamWriter(this.socketClient.getOutputStream(),
                            Charset.forName("UTF8")));
            bout.append(nom + "\n");
            bout.flush();

            this.ecouteur = this.new EcouteurClient();
            this.sender = this.new SenderClient();
            this.ecouteur.start();
            this.sender.start();
        } catch (IOException ex) {
            throw new Error(ex);
        }

    }

    public void send(String mess) {
        synchronized (this.toSend) {
            toSend.add(mess);
            toSend.notifyAll();
        }
    }

    public class EcouteurClient extends Thread {

        @Override
        public void run() {
            try ( BufferedReader bin = new BufferedReader(
                    new InputStreamReader(socketClient.getInputStream(),
                            Charset.forName("UTF8")))) {

                String nextLine;
                while ((nextLine = bin.readLine()) != null) {
                    gereNouveauMessage(nextLine);
                }

            } catch (IOException ex) {
                throw new Error(ex);
            }
        }
    }

    public class SenderClient extends Thread {

        @Override
        public void run() {
            try ( BufferedWriter bout = new BufferedWriter(
                    new OutputStreamWriter(socketClient.getOutputStream(),
                            Charset.forName("UTF8")))) {

                while (true) {
                    synchronized (toSend) {
                        while (!toSend.isEmpty()) {
                            String nextLine = toSend.pop();
                            bout.append(nextLine + "\n");
                            bout.flush();
                        }
                        try {
                            toSend.wait();
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

    public static void testEmulateClientBasicFromAdaptable() {
        String host = ConsoleFdB.entreeString("hostname : ");
        int port = ConsoleFdB.entreeInt("port : ");
        String nom = ConsoleFdB.entreeString("votre nom :");
        ClientAdaptable ca = new ClientAdaptable(host, port, nom) {
            @Override
            public void gereNouveauMessage(String mess) {
                System.out.println(mess);
            }
        };
        while (true) {
            String mess = ConsoleFdB.entreeString("message : ");
            ca.send(mess);
        }

    }

    public static void main(String[] args) {
        testEmulateClientBasicFromAdaptable();
    }

}
