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
public class MyRun implements Runnable{
    
    private String nom ;
    private int nbrIter;
    
     public MyRun(String nom,int nbrIter) {
        this.nom = nom;
        this.nbrIter = nbrIter;
    }
    
    

    @Override
    public void run() {
        for(int i = 0 ; i < this.nbrIter ; i ++) {
            System.out.println(this.nom + " : " +i);
        }
    }
    
}
