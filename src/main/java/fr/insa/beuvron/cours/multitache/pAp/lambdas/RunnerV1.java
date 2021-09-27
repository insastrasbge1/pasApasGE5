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
package fr.insa.beuvron.cours.multitache.pAp.lambdas;

/**
 *  test GIT
 * @author francois
 */
public class RunnerV1 {
    
    private int nbrThread;
    private long nbrIter;

    public RunnerV1(int nbrThread, long nbrIter) {
        this.nbrThread = nbrThread;
        this.nbrIter = nbrIter;
    }

    
    public void runPara() {

        for (int i = 0; i < this.nbrThread; i++) {
            MyRun r = new MyRun("T"+i,this.nbrIter);
            Thread t = new Thread(r);
            t.start();
                    
        }
    }
    
    public static void main(String[] args) {
        System.out.println("def : " + RunnerV2.NBR_PAR_DEFAUT);
        RunnerV1 run = new RunnerV1(5, 10);
        run.runPara();
    }

}
