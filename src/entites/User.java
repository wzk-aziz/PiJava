/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author neder
 */
public class User {
    private int id,role;
    private String nom,prenom,login,adresse,numtel,email;

    public User(int id) {
        this.id = id;
    }

    public User(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

   

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public int getRole() {
        return role;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getNumtel() {
        return numtel;
    }

    public String getEmail() {
        return email;
    }

    public User() {
    }

    public User(int id, int role, String nom, String prenom, String login, String adresse, String numtel, String email) {
        this.id = id;
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.adresse = adresse;
        this.numtel = numtel;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", role=" + role + ", nom=" + nom + ", prenom=" + prenom + ", login=" + login + ", adresse=" + adresse + ", numtel=" + numtel + ", email=" + email + '}';
    }
    
    
}
