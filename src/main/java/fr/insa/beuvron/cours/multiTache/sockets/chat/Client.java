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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class Client {

    private Socket socketClient;

    public void start() {
        try {
//            String host = ConsoleFdB.entreeString("hostname : ");
//            int port = ConsoleFdB.entreeInt("port : ");
            String host = "192.168.56.1";
            int port = 49627;

//            String nom = ConsoleFdB.entreeString("votre nom :");
            String nom = "toto " + (int) (Math.random()*10000);
            this.socketClient = new Socket(host, port);
            try ( BufferedWriter bout = new BufferedWriter(
                    new OutputStreamWriter(this.socketClient.getOutputStream(),
                            Charset.forName("UTF8")))) {
                System.out.println("Client " + nom + " : sending name \"" + nom + "\" to server");
                bout.append(nom + "\n");
                bout.flush();
                // OUBLI PaP
                EcouteurClient ec = this.new EcouteurClient();
                ec.start();
                while (true) {
                    String mess = ConsoleFdB.entreeString("message : ");
                    bout.append(mess + "\n");
                    bout.flush();
                }

            }
        } catch (IOException ex) {
            throw new Error(ex);
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
                    System.out.println(nextLine + "\n");
                }

            } catch (IOException ex) {
                throw new Error(ex);
            }
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.start();
    }
}
