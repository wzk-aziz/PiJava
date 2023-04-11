/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.services;

import evenements.entities.Evenements;
import evenements.utils.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import evenements.intefaces.InterfaceEvenements;

/**
 *
 * @author rania
 */
public class EvenementsCRUD implements InterfaceEvenements {

    @Override
    public void ajouterEvenements(Evenements r) {
      try {

            String requete1 = "INSERT INTO Evenements(titre,description,date,photo) VALUES(?,?,STR_TO_Date(?,'%Y-%m-%d'),?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setString(1, r.getTitre());
            pst.setString(2, r.getDescription());

            pst.setString(3, r.getDate());
            pst.setString(4, r.getPhoto());
            pst.executeUpdate();
            System.out.println("Evenements ajoutee avec succes !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
        
        
        
        
        
        
        
         
    }

    @Override
    public void modifierEvenements(Evenements r) {
        try {
            String requete4;
            requete4 = " UPDATE Evenements SET titre=?,description=?,date=?,photo=? WHERE id=?" ;
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, r.getTitre());
            pst.setString(2, r.getDescription());

            pst.setString(3, r.getDate());
            pst.setString(4, r.getPhoto());
            pst.setInt(5, r.getId());
               pst.executeUpdate();
            System.out.println("Evenements modifie");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }   
        
        
        
        
        
    }

    @Override
    public void supprimerEvenements(int id) {
        try {
            String req = "DELETE FROM Evenements WHERE id = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Evenements supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    

        
        
        
    }

    @Override
    public List<Evenements> afficherEvenements() {
ArrayList<Evenements> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM Evenements";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            
              while (rs.next()) {
                Evenements r = new Evenements(rs.getInt("id"), rs.getString("titre"),rs.getString("description"), rs.getString("date"), rs.getString("photo"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            }
        return myList;        
        
        
        
    }
   
}