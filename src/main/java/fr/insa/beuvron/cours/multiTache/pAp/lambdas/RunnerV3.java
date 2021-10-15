/*
Copyright 2000- Francois de Bertrand de Beuvron

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
package fr.insa.beuvron.cours.multiTache.pAp.lambdas;

/**
 * test GIT
 *
 * @author francois
 */
public class RunnerV3 {
    
    public static final int NBR_PAR_DEFAUT = 20;

    private int nbrThread;
    private long nbrIter;

    public RunnerV3(int nbrThread, long nbrIter) {
        this.nbrThread = nbrThread;
        this.nbrIter = nbrIter;
    }

    public class MyRun3 implements Runnable {

        private String nom;

        public MyRun3(String nom) {
            this.nom = nom;
        }

        @Override
        public void run() {
            for (long i = 0; i < nbrIter; i++) {
                System.out.println(this.nom + " : " + i);
            }
        }

    }

    public void runPara() {

        for (int i = 0; i < this.nbrThread; i++) {
            MyRun3 r = this.new MyRun3("T" + i);
            Thread t = new Thread(r);
            t.start();

        }
    }

    public static void main(String[] args) {
        RunnerV3 run = new RunnerV3(5, 10);
        run.runPara();
    }

}
