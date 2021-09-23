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
 *  
 * @author francois
 */
public class RunnerV1 {

    public static void runPara(int nbrThread, int nbrIter) {

        for (int i = 0; i < nbrThread; i++) {
            MyRun r = new MyRun("T"+i,10*i);
            Thread t = new Thread(r);
            t.start();
                    
        }
    }
    
    public static void main(String[] args) {
        runPara(5, 1000);
    }

}
