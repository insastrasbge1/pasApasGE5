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
package fr.insa.beuvron.cours.multiTache2.pAp.lambdas;

/**
 * test GIT
 * avec lambda
 * @author francois
 */
public class RunnerV5 {

    public static final int NBR_PAR_DEFAUT = 20;

    private int nbrThread;
    private long nbrIter;

    public RunnerV5(int nbrThread, long nbrIter) {
        this.nbrThread = nbrThread;
        this.nbrIter = nbrIter;
    }

    public void runPara() {

        for (int i = 0; i < this.nbrThread; i++) {
            String leNom = "T" + i;
            Thread t = new Thread(() -> {
                    for (long j = 0; j < nbrIter; j++) {
                        System.out.println(leNom + " : " + j);
                    }
            });
            t.start();

        }
    }

    public static void main(String[] args) {
        RunnerV5 run = new RunnerV5(50, 10);
        run.runPara();
    }

}
