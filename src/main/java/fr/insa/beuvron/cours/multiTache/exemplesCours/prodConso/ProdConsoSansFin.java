/*
 Copyright 2000-2014 Francois de Bertrand de Beuvron

 This file is part of CoursBeuvron.

 CoursBeuvron is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 CoursBeuvron is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multiTache.exemplesCours.prodConso;

/**
 * des threads produisent une ressource, d'autres la consomme (s'il en reste au
 * moins une disponible
 *
 * attention : 
 *   - il n'y a aucun mécanisme d'arrêt : ce programme "tourne" indéfiniment
 *   - il n'y a pas de limite à la production : surproduction "infinie" possible
 *
 * @author francois
 */
public class ProdConsoSansFin {

    public static class Ressource {

        private int quantite = 0;

        public synchronized int consommeRes() {
            while (this.quantite <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            this.quantite --;
            return this.quantite;
        }
        
        public synchronized void produitRes() {
            this.quantite ++;
            this.notifyAll();
        }
    }

    public static class Producteur implements Runnable {
        
        private String name;
        private Ressource res;
        private long minTimeToProduct;
        private long maxTimeToProduct;

        public Producteur(String name,Ressource res, long minTimeToProduct, long maxTimeToProduct) {
            this.name = name;
            this.res = res;
            this.minTimeToProduct = minTimeToProduct;
            this.maxTimeToProduct = maxTimeToProduct;
        }

        @Override
        public void run() {
            while(true) {
                System.out.println(this.name + " : " + "debute prod");
                try {
                    Thread.sleep(aleaBetween(minTimeToProduct, maxTimeToProduct));
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
                res.produitRes();
                System.out.println(this.name + " : " + "fin prod");
            }
            
        }

    }
    
    public static class Consommateur implements Runnable {
        
        private String name;
        private Ressource res;
        private long minTimeToConsume;
        private long maxTimeToConsume;

        public Consommateur(String name,Ressource res, long minTimeToConsume, long maxTimeToConsume) {
            this.name = name;
            this.res = res;
            this.minTimeToConsume = minTimeToConsume;
            this.maxTimeToConsume = maxTimeToConsume;
        }

        @Override
        public void run() {
            while(true) {
                System.out.println(this.name + " : " + "a faim");

                int obtenu = res.consommeRes();
                System.out.println(this.name + " : " + "mange " + obtenu);
                try {
                    Thread.sleep(aleaBetween(minTimeToConsume, maxTimeToConsume));
                } catch (InterruptedException ex) {
                    throw new Error("pas d'interrupt dans cet exemple");
                }
            }
            
        }

    }
    
    public static void tropDeConso() {
        int nbrConso = 3;
        int nbrProd = 2;
        Ressource miam = new Ressource();
        Consommateur[] consos = new Consommateur[nbrConso];
        Producteur[] prods = new Producteur[nbrProd];
        for (int i = 0; i < prods.length; i++) {
            prods[i] = new Producteur("P" + i,miam, 500, 2000);
            new Thread(prods[i]).start();
        }
        for (int i = 0; i < consos.length; i++) {
            consos[i] = new Consommateur("C" + i,miam, 100, 300);
            new Thread(consos[i]).start();            
        }
    }
    
    public static void equilibreConsoProd() {
        int nbrConso = 2;
        int nbrProd = 2;
        Ressource miam = new Ressource();
        Consommateur[] consos = new Consommateur[nbrConso];
        Producteur[] prods = new Producteur[nbrProd];
        for (int i = 0; i < prods.length; i++) {
            prods[i] = new Producteur("P" + i,miam, 100, 300);
            new Thread(prods[i]).start();
        }
        for (int i = 0; i < consos.length; i++) {
            consos[i] = new Consommateur("C" + i,miam, 100, 300);
            new Thread(consos[i]).start();            
        }
    }
    
    public static void grosseProd() {
        int nbrConso = 1;
        int nbrProd = 2;
        Ressource miam = new Ressource();
        Consommateur[] consos = new Consommateur[nbrConso];
        Producteur[] prods = new Producteur[nbrProd];
        for (int i = 0; i < prods.length; i++) {
            prods[i] = new Producteur("P" + i,miam, 500, 1000);
            new Thread(prods[i]).start();
        }
        for (int i = 0; i < consos.length; i++) {
            consos[i] = new Consommateur("C" + i,miam, 500, 1000);
            new Thread(consos[i]).start();            
        }
    }
    
    public static void main(String[] args) {
//        tropDeConso();
//        equilibreConsoProd();
        grosseProd();
    }
    
    public static long aleaBetween(long min,long max) {
        long delta = max - min;
        return min + (long) (Math.random() * (delta +1));
    }
    
    public static String threadName() {
        return Thread.currentThread().getName();
    }

}
