/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.cours.multitache.pasapasge5.electifs.model;

/**
 *
 * @author francois
 */
public class Module {
    
    private String intitule;
    private int nbrPlace;

    public Module(String intitule, int nbrPlace) {
        this.intitule = intitule;
        this.nbrPlace = nbrPlace;
    }

    /**
     * @return the intitule
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * @return the nbrPlace
     */
    public int getNbrPlace() {
        return nbrPlace;
    }

    /**
     * @param nbrPlace the nbrPlace to set
     */
    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    @Override
    public String toString() {
        return "Module{" + "intitule=" + intitule + ", nbrPlace=" + nbrPlace + '}';
    }
    
    
    
}
