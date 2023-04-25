/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.sql.Connection;
import util.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Inventory;


/**
 *
 * @author user
 */
public class InventoryService {
    
    private Connection cnx;
     public InventoryService() {
        cnx = MyConnection.getInstance().getCnx();
    }
     
     
     
     public void addInventory(Inventory i){
      try {
        String query = "INSERT INTO inventory (nommed, nbpl) VALUES (?, ?)";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setString(1, i.getNommed());
        pst.setInt(2, i.getNbpl());
        pst.executeUpdate();
        System.out.println("Inventory added successfully");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
     }
     
     public List<Inventory> AfficherInventory() {
        List<Inventory> Inventory = new ArrayList<>();
        try {
            String qry = "SELECT * FROM inventory";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Inventory i = new Inventory();
               i.setId(rs.getInt("id"));
               i.setNommed(rs.getString("nommed"));
               i.setNbpl(rs.getInt("nbpl"));

                Inventory.add(i);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Inventory;
    }
     
     
     public void SupprimerInventory(int id) {
        try {
            String qry = "DELETE FROM inventory WHERE id = ?";
            PreparedStatement pstmt = cnx.prepareStatement(qry);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     
      public boolean updateInventory(Inventory i) {
    try {
        
        String qry = "UPDATE inventory  SET nommed = ?, nbpl = ? WHERE id = ?";
                        
        PreparedStatement pstmt = cnx.prepareStatement(qry);

        pstmt.setString(1, i.getNommed());
        pstmt.setInt(2, i.getNbpl());
        pstmt.setInt(3, i.getId());
        pstmt.executeUpdate();
        return true;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
}
     
      
      
      public List<Inventory> searchByNameinv(String nommed) {
    List<Inventory> inventory = new ArrayList<>();
    try {
        String qry = "SELECT * FROM inventory WHERE nommed LIKE ?";
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setString(1, "%" + nommed + "%");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Inventory i = new Inventory();
            
             i.setId(rs.getInt("id"));
               i.setNommed(rs.getString("nommed"));
               i.setNbpl(rs.getInt("nbpl"));
            inventory.add(i);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return inventory;
}

      
}
