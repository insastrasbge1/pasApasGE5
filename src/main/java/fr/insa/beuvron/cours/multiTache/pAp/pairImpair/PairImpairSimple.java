/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.pAp.pairImpair;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class PairImpairSimple {

    public static class Pair implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 10; i = i + 2) {
                try {
                    Thread.sleep((long) (Math.random() * 200));
                } catch (InterruptedException ex) {
                    throw new Error("ca ne devrait pas arriver");
                }
                System.out.println("Pair : " + i);
            }
        }

    }

    public static class Impair implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 9; i = i + 2) {
                try {
                    Thread.sleep((long) (Math.random() * 200));
                } catch (InterruptedException ex) {
                    throw new Error("ca ne devrait pas arriver");
                }
                System.out.println("Impair : " + i);
            }
        }

    }

    public static void gogogo() {
        Thread tp = new Thread(new Pair());
        Thread ti = new Thread(new Impair());
        tp.start();
        ti.start();
        try {
            tp.join();
            ti.join();
        } catch (InterruptedException ex) {
            throw new Error("impossible",ex);
        }
        System.out.println("c'est fini");

    }

    public static void main(String[] args) {
        gogogo();
    }

}
