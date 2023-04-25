/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author user
 */
public class Inventory {
    private int id;
    private String nommed;
    private int nbpl;

    public Inventory() {
    }

    public Inventory(String nommed, int nbpl) {
        this.nommed = nommed;
        this.nbpl = nbpl;
    }

    public Inventory(int id, String nommed, int nbpl) {
        this.id = id;
        this.nommed = nommed;
        this.nbpl = nbpl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNommed() {
        return nommed;
    }

    public void setNommed(String nommed) {
        this.nommed = nommed;
    }

    public int getNbpl() {
        return nbpl;
    }

    public void setNbpl(int nbpl) {
        this.nbpl = nbpl;
    }

    @Override
    public String toString() {
        return "Inventory{" + "id=" + id + ", nommed=" + nommed + ", nbpl=" + nbpl + '}';
    }

    
}
