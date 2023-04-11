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
import models.Medicaments;
import models.Rappel;

/**
 *
 * @author user
 */
public class MedicamentsService {
    
    private Connection cnx;
    
    public MedicamentsService() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    public boolean addMedicaments(Medicaments m) {
    try {
        String qry = "INSERT INTO medicaments(rappel_id, nommed, dosage, heureprise) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, m.getRappel_id());
        pstmt.setString(2, m.getNommed());
        pstmt.setString(3, m.getDosage());
        pstmt.setString(4, m.getHeurePrise());
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            return true;
        } else {
            return false;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
}

    
    public List<Medicaments> AfficherMedicaments() {
        List<Medicaments> Medicaments = new ArrayList<>();
        try {
            String qry = "SELECT * FROM medicaments";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Medicaments me = new Medicaments();
                me.setId(rs.getInt("id"));
                me.setRappel_id(rs.getInt("rappel_id"));
                me.setNommed(rs.getString("nommed"));
                me.setDosage(rs.getString("dosage"));
                me.setHeurePrise(rs.getString("heureprise"));
                Medicaments.add(me);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Medicaments;
    }
    
    public void SupprimerMedicaments(int id) {
        try {
            String qry = "DELETE FROM medicaments WHERE id = ?";
            PreparedStatement pstmt = cnx.prepareStatement(qry);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
   public boolean updateMedicaments(Medicaments m) {
    try {
        
        String qry = "UPDATE medicaments SET rappel_id = ?, nommed = ?, dosage = ?, heureprise = ? WHERE id = ?";

        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, m.getRappel_id());
        pstmt.setString(2, m.getNommed());
        pstmt.setString(3, m.getDosage());
        pstmt.setString(4, m.getHeurePrise());
        pstmt.setInt(5, m.getId());
        pstmt.executeUpdate();
        return true;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
}

    
}
