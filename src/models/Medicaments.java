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
public class Medicaments {
    
    private int id;
  
    private String nommed,dosage;
    private String heurePrise;
    private int rappel_id;

    public Medicaments(String nommed, String dosage, String heurePrise, int rappel_id) {
        this.nommed = nommed;
        this.dosage = dosage;
        this.heurePrise = heurePrise;
        this.rappel_id = rappel_id;
    }

    public Medicaments(int id, String nommed, String dosage, String heurePrise, int rappel_id) {
        this.id = id;
        this.nommed = nommed;
        this.dosage = dosage;
        this.heurePrise = heurePrise;
        this.rappel_id = rappel_id;
    }

    public Medicaments() {
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getHeurePrise() {
        return heurePrise;
    }

    public void setHeurePrise(String heurePrise) {
        this.heurePrise = heurePrise;
    }

    public int getRappel_id() {
        return rappel_id;
    }

    public void setRappel_id(int rappel_id) {
        this.rappel_id = rappel_id;
    }

    @Override
    public String toString() {
        return "Medicaments{" + "id=" + id + ", nommed=" + nommed + ", dosage=" + dosage + ", heurePrise=" + heurePrise + ", rappel_id=" + rappel_id + '}';
    }

   
    
    
    
}
