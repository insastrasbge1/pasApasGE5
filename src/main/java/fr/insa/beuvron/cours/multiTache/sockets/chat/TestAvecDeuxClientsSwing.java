/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.sockets.chat;

import java.net.InetAddress;

/**
 *
 * @author francois
 */
public class TestAvecDeuxClientsSwing {

    public static void test2ClientsSwing() {
        Server s = new Server();
        s.start();
        InetAddress adre = s.waitForInetAddress();
        int port = s.waitForPort();
        ClientSwing c1 = new ClientSwing(adre,port,"toto");
        ClientSwing c2 = new ClientSwing(adre,port,"titi");
        c1.setVisible(true);
        c2.setVisible(true);

    }
    
    public static void main(String[] args) {
        test2ClientsSwing();
    }
}
