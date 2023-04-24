/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entites.RDV;
import entites.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MYDB;
/**
 *
 * @author neder
 */
public class ServiceUser {
        ObservableList<RDV>obListR = FXCollections.observableArrayList();
         ObservableList<User>obListU = FXCollections.observableArrayList();
    ObservableList<User>obListnom = FXCollections.observableArrayList();
    Connection conn ; 
    public ServiceUser(){
      conn = MYDB.getInstance().getConnection();
    }
    
    public User getUserById(int idUser){
      
           String req= "SELECT * FROM utilisateur WHERE id =?";

        User u = new User();
        try{
            //Statement statement = conn.createStatement();
            //ResultSet rs = statement.executeQuery(req);
                  PreparedStatement statement = conn.prepareStatement(req);
        statement.setInt(1, idUser);
        ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                int id= rs.getInt("id");
                int role= rs.getInt("role");
                String numtel = rs.getString("numtel");
               String email = rs.getString("email");
               String adresse = rs.getString("adresse");
               String login = rs.getString("login");
               String nom = rs.getString("nom");
               String prenom = rs.getString("prenom");
                  u = new User(id, role, nom, prenom, login, adresse, numtel, email);
              
                System.out.println(u);
             

              
            }   
            
            
            
        }catch(Exception ex) {
            System.out.println("exception ="+ex.getMessage() );
        }
        
        return u;
        
    }
     public  ObservableList<User> affichageUser() {
           String req="SELECT * FROM utilisateur ";
         List<User>listU = new ArrayList<>();
        
        
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(req);
            
            while(rs.next()) {
                int id= rs.getInt("id");
                int role= rs.getInt("role");
                String numtel = rs.getString("numtel");
               String email = rs.getString("email");
               String adresse = rs.getString("adresse");
               String login = rs.getString("login");
               String nom = rs.getString("nom");
               String prenom = rs.getString("prenom");
                
                User U = new User (id, role, nom, prenom, login, adresse, numtel, email);
              
               obListU.add(U);

              
            }   
            
            
            
        }catch(Exception ex) {
            System.out.println("exception ="+ex.getMessage() );
        }
        return obListU ;
    }
      
   public User getUserDocteur(String nom) {
    String req = "SELECT nom FROM utilisateur WHERE role = 2";
    User user = null;
    try {
        PreparedStatement ps = conn.prepareStatement(req);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new User(rs.getInt("id"), rs.getString("nom"));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération du nom de medecin : " + ex.getMessage());
    }
    return user;
}
    public User getUserPateint(String nom) {
    String req = "SELECT nom FROM utilisateur WHERE role = 1";
    User user = null;
    try {
        PreparedStatement ps = conn.prepareStatement(req);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new User(rs.getInt("id"), rs.getString("nom"));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération du nom de medecin : " + ex.getMessage());
    }
    return user;
}
    public User getUserbyNom(String nom) {
    String req = "SELECT * FROM utilisateur WHERE nom = ?";
    User user = null;
    try {
        PreparedStatement ps = conn.prepareStatement(req);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new User(rs.getInt("id"), rs.getString("nom"));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération de la liste : " + ex.getMessage());
    }
    return user;
}
}
