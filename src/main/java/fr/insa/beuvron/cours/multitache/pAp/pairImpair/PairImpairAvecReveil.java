/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multitache.pAp.pairImpair;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class PairImpairAvecReveil {

    private Thread tPair;
    private Thread tImpair;

    public static final long MAX_ITER = 30000000L;

    public static void duBoulot(long maxIter) {
        double x = 0;
        long combien = (long) (Math.random() * maxIter);
        for (long bosse = 0; bosse
                < combien; bosse++) {
            x = x + Math.cos(x);
        }
    }

    public PairImpairAvecReveil() {
        this.tPair = new Thread(new Pair());
        this.tImpair = new Thread(new Impair());
    }

    public class Pair implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 10; i = i + 2) {
                duBoulot(MAX_ITER);
                System.out.println("Pair : " + i);
                System.out.println("pair réveille impair");
                tImpair.interrupt();
                if (i < 10) {
                    try {
                        System.out.println("pair attend impair");
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException ex) {
                        System.out.println("pair se réveille");
                    }
                }
            }
        }

    }

    public class Impair implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 9; i = i + 2) {
                duBoulot(MAX_ITER);
                try {
                    System.out.println("impair attend pair");
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException ex) {
                    System.out.println("impair se réveille");
                }
                System.out.println("Impair : " + i);
                System.out.println("impair réveille pair");
                tPair.interrupt();
            }
        }

    }

    public void gogogo() {
        this.tPair.start();
        this.tImpair.start();
        try {
            this.tPair.join();
            this.tImpair.join();
        } catch (InterruptedException ex) {
            throw new Error("impossible", ex);
        }
        System.out.println("c'est fini");

    }

    public static void main(String[] args) {
        PairImpairAvecReveil pi = new PairImpairAvecReveil();
        pi.gogogo();
    }

}
