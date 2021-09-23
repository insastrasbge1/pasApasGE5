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
package fr.insa.beuvron.cours.multiTache.pasapasge5.electifs.model;

import fr.insa.beuvron.cours.m3New.tds.TD1.ExemplePersonnesAlea;
import fr.insa.beuvron.utils.matrice.MatriceToText;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 *  v2
 * @author francois
 */
public class Database {

    private List<Etudiant> etudiants;
    private List<Module> modules;
    private List<CoutInscriptionEtudiantModule> coutsInscription;

    public Database() {
        this.etudiants = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.coutsInscription = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Database{" + "etudiants=" + etudiants.size() + ", modules=" + modules.size() + 
                ", coutsInscription=" + coutsInscription.size() + '}';
    }

    
    public String toStringDetails() {
        StringBuilder res = new StringBuilder();
        res.append("---------------- les étudiants ---------------------\n");
        for (Etudiant e : this.etudiants) {
            res.append(e.toString());
            res.append("\n");
        }
        res.append("---------------- les modules ---------------------\n");
        for (Module m : this.modules) {
            res.append(m.toString());
            res.append("\n");
        }
        String[][] matCouts = new String[this.etudiants.size()+1][this.modules.size()+1];
        matCouts[0][0] = "";
        for(int nm = 0 ; nm < this.modules.size() ; nm ++) {
            matCouts[0][nm+1] = this.modules.get(nm).getIntitule();
        }
        for (int ne = 0 ; ne < this.etudiants.size() ; ne ++) {
            Etudiant e = this.etudiants.get(ne);
            matCouts[ne+1][0] = e.getNom() + " " + e.getPrenom();
            for (int nm = 0 ; nm < this.modules.size() ; nm ++) {
                Module m = this.modules.get(nm);
                matCouts[ne+1][nm+1] = "" + this.findCout(e, m);
            }
        }
        res.append("----------------- couts d'inscription -----------------\n");
        res.append(MatriceToText.formatMat(matCouts, true));
        return res.toString();
    }

    /**
     * @return the etudiants
     */
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    /**
     * @return the modules
     */
    public List<Module> getModules() {
        return modules;
    }

    public boolean addEtudiant(Etudiant e) {
        if (this.etudiants.contains(e)) {
            return false;
        } else {
            this.etudiants.add(e);
            return true;
        }
    }

    public boolean addModule(Module m) {
        if (this.modules.contains(m)) {
            return false;
        } else {
            this.modules.add(m);
            return true;
        }
    }
    
    public Double findCout(Etudiant e,Module m) {
        int pos = this.coutsInscription.indexOf(new CoutInscriptionEtudiantModule(e, m, 0));
        if (pos == -1) {
            return null;
        } else {
            return this.coutsInscription.get(pos).getCout();
        }
    }

    public void addEtudiantAlea(int nbr, Random r) {
        List<String> noms = ExemplePersonnesAlea.nomsAlea();
        List<String> prenoms = ExemplePersonnesAlea.nomsAlea();
        int i = 0;
        while (i < nbr) {
            Etudiant alea = new Etudiant(
                    noms.get(r.nextInt(noms.size())),
                    prenoms.get(r.nextInt(prenoms.size()))
            );
            if (this.addEtudiant(alea)) {
                i++;
            }
        }
    }

    public void addModulesAlea(int nbr, Random r) {
        int i = 0;
        while (i < nbr) {
            Module alea = new Module(
                    "mod " + String.format("%03d", r.nextInt(1000)),
                    20 + r.nextInt(20));
            if (this.addModule(alea)) {
                i++;
            }
        }
    }

    public void generateCoutsAlea(Random r) {
        this.coutsInscription.clear();
        for (Etudiant e : this.etudiants) {
            for (Module m : this.modules) {
                this.coutsInscription.add(
                        CoutInscriptionEtudiantModule.coutAlea(e, m, r));
            }
        }
    }

    public static Database databaseAlea(int nbrEtudiants, int nbrModules, Random r) {
        Database res = new Database();
        res.addEtudiantAlea(nbrEtudiants, r);
        res.addModulesAlea(nbrModules, r);
        res.generateCoutsAlea(r);
        return res;
    }

    public static void test1() {
        Database dbalea = Database.databaseAlea(10, 8, new Random());
        System.out.println("database aléa : \n" + dbalea.toStringDetails());
    }

    public static void main(String[] args) {
        Database.test1();

    }

}
