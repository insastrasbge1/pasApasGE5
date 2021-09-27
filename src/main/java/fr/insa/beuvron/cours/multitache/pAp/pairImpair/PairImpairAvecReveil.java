/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multitache.pAp.pairImpair;

/**
 *
 * @author francois
 */
public class PairImpairAvecReveil {

    private Thread tPair;
    private Thread tImpair;

    public PairImpairAvecReveil() {
        this.tPair = new Thread(new Pair());
        this.tImpair = new Thread(new Impair());
    }

    public class Pair implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= 10; i = i + 2) {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                    if (i == 6) {
                        tImpair.interrupt();
                    }
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
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException ex) {
                    throw new Error("ca ne devrait pas arriver");
                }
                System.out.println("Impair : " + i);
            }
        }

    }

    public void gogogo() {
        this.tPair.start();
        this.tImpair.start();

    }

    public static void main(String[] args) {
        PairImpairAvecReveil pi = new PairImpairAvecReveil();
        pi.gogogo();
    }

}
