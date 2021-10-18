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
package fr.insa.beuvron.cours.multiTache.exemplesCours.trie;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tri récursif parallèle. Les Threads sont définis ici avec des lambda expression
 * (ce serait la même chose avec une syntaxe un peu plus lourde en utilisant
 * des classes locales).
 * Cela permet de garder la structure quasiment identique à celle de la version
 * séquentielle.
 * @author francois
 */
public class TriParalleleLambda {

    public static void tri(int[] tab) {
        triBorne(tab, 0, tab.length - 1);
    }

    public static void triBorne(int[] tab, int min, int max) {
//        System.out.println("sorting " + Arrays.toString(tab) + " between " + min + " and " + max);
        if (max - min < 2) {
            if (tab[min] > tab[max]) {
                int temp = tab[min];
                tab[min] = tab[max];
                tab[max] = temp;
            }
        } else {
            int milieu = (max + min) / 2;
            Thread tInf = new Thread(() -> {
                triBorne(tab, min, milieu);
            });
            Thread tSupp = new Thread(() -> {
                triBorne(tab, milieu + 1, max);
            });
            tInf.start();
            tSupp.start();
            
            try {
                tInf.join();
                tSupp.join();
            } catch (InterruptedException ex) {
                throw new Error(ex);
            }

            fusion(tab, min, max);
        }
//        System.out.println("sorted " + Arrays.toString(tab) + " between " + min + " and " + max);
    }

    private static void fusion(int[] tab, int min, int max) {
        int[] fu = new int[max - min + 1];
        int milieu = (max + min) / 2;
        int cur1 = min;
        int cur2 = milieu + 1;
        for (int i = 0; i < fu.length; i++) {
            if (cur1 > milieu) {
                // plus rien dans la première partie
                fu[i] = tab[cur2];
                cur2++;
            } else if (cur2 > max) {
                // plus rien dans la seconde partie
                fu[i] = tab[cur1];
                cur1++;
            } else if (tab[cur1] <= tab[cur2]) {
                fu[i] = tab[cur1];
                cur1++;
            } else {
                fu[i] = tab[cur2];
                cur2++;
            }
        }
        for (int i = 0; i < fu.length; i++) {
            tab[min + i] = fu[i];
        }
    }

    public static boolean testTrie(int[] tab) {
        boolean res = true;
        int i = 0;
        while (res && i < tab.length - 1) {
            res = tab[i] <= tab[i + 1];
            i++;
        }
        return res;
    }

    public static int[] tabAlea(int size, int borneMax) {
        int[] res = new int[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * borneMax);
        }
        return res;
    }

    public static void test(int size, int bmax) {
        int[] t = TriParalleleLambda.tabAlea(size, bmax);
        System.out.println("trie tableau taille : " + size
                + " (0 <= e < " + bmax + ")");
        long deb = System.currentTimeMillis();
        tri(t);
        long duree = System.currentTimeMillis() - deb;
        System.out.println("test : " + TriParalleleLambda.testTrie(t));
        System.out.println("in " + duree + " ms");

    }

    public static void main(String[] args) {
        test(1000, TriSequentiel.BMAX);
    }

}
