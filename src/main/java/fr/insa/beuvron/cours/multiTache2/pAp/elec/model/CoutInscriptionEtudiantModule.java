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
package fr.insa.beuvron.cours.multiTache2.pAp.elec.model;

import java.util.Objects;
import java.util.Random;

/**
 * 
 * @author francois
 */
public class CoutInscriptionEtudiantModule {

    public static final double COUT_PROHIBITIF = 10000;

    private Etudiant etudiant;
    private Module module;
    private double cout;

    public CoutInscriptionEtudiantModule(Etudiant etudiant, Module module, double cout) {
        this.etudiant = etudiant;
        this.module = module;
        this.cout = cout;
    }

    public static CoutInscriptionEtudiantModule coutAlea(Etudiant e, Module m, Random r) {
        double cout;
        double alea = r.nextDouble();
        if (alea < 0.4) {
            // 40% des modules "interdits"
            cout = COUT_PROHIBITIF;
        } else if (alea < 0.8) {
            // 40% inscription normale
            cout = 0;
        } else if (alea < 0.9) {
            // 10% inscription deconseillée
            cout = 100;
        } else {
            // 10% inscription prioritaire
            cout = -100;
        }
        return new CoutInscriptionEtudiantModule(e, m,cout);
    }

    /**
     * @return the etudiant
     */
    public Etudiant getEtudiant() {
        return etudiant;
    }

    /**
     * @return the module
     */
    public Module getModule() {
        return module;
    }

    /**
     * @return the cout
     */
    public double getCout() {
        return cout;
    }

    /**
     * @param cout the cout to set
     */
    public void setCout(double cout) {
        this.cout = cout;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.etudiant);
        hash = 29 * hash + Objects.hashCode(this.module);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoutInscriptionEtudiantModule other = (CoutInscriptionEtudiantModule) obj;
        if (!Objects.equals(this.etudiant, other.etudiant)) {
            return false;
        }
        if (!Objects.equals(this.module, other.module)) {
            return false;
        }
        return true;
    }

    
}
