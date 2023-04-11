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
public class Rappel {
    private int id;
    private String message,nomrappel ;

    public Rappel() {
    }

    public Rappel(String message, String nomrappel) {
        this.message = message;
        this.nomrappel = nomrappel;
    }

   
    public Rappel(int id, String message, String nomrappel) {
        this.id = id;
       
        this.message = message;
        this.nomrappel = nomrappel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }
*/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNomrappel() {
        return nomrappel;
    }

    public void setNomrappel(String nomrappel) {
        this.nomrappel = nomrappel;
    }

    @Override
    public String toString() {
        return "Rappel{" + "id=" + id + ",  message=" + message + ", nomrappel=" + nomrappel + '}';
    }
    
    

    
}
