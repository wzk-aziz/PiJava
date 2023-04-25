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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author user
 */
public class MedicamentsService {
    
    private Connection cnx;
    private RappelService rappelService;
     private InventoryService inventoryService;
    public MedicamentsService() {
        cnx = MyConnection.getInstance().getCnx();
         inventoryService = new InventoryService();
         rappelService=new RappelService();
    }
   /* 
     public boolean addMedicaments(Medicaments m) {
    try {
        String qry = "INSERT INTO medicaments(rappel_id, nommed, dosage, heureprise) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, m.getRappel_id());
        pstmt.setString(2, m.getNommed());
        pstmt.setInt(3, m.getDosage());
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
*/
    
    
  
 public boolean addMedicaments(Medicaments m) {
    try {
        String qry = "INSERT INTO medicaments(rappel_id, nommed, dosage, heureprise) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, m.getRappel_id());
        pstmt.setString(2, m.getNommed());
        pstmt.setInt(3, m.getDosage());
        pstmt.setString(4, m.getHeurePrise());
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            //local time is dd/mm/yy,hh:mm:ss:msms
            // Check if medication is due for intake and send SMS notification if necessary
            //parse to make heure de prise same format as local time hope it works
            
            LocalTime heureprise = LocalTime.parse(m.getHeurePrise());
            if (heureprise.equals(LocalTime.now())) {
                String message = "Temps pour prendre vos medicaments";
                SmsService smsService = new SmsService();
                try {
                    SmsService.sendSms("+21654260859", message);
                } catch (Exception ex) {
                    System.out.println("Failed to send SMS: " + ex.getMessage());
                }
            }
            
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
                me.setDosage(rs.getInt("dosage"));
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
            pstmt.setInt(3, m.getDosage());
            pstmt.setString(4, m.getHeurePrise());
            pstmt.setInt(5, m.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
public List<Medicaments> searchByName(String nommed) {
    List<Medicaments> medications = new ArrayList<>();
    try {
        String qry = "SELECT * FROM medicaments WHERE nommed LIKE ?";
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setString(1, "%" + nommed + "%");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Medicaments m = new Medicaments();
            m.setId(rs.getInt("id"));
            m.setRappel_id(rs.getInt("rappel_id"));
            m.setNommed(rs.getString("nommed"));
            m.setDosage(rs.getInt("dosage"));
            m.setHeurePrise(rs.getString("heureprise"));
            medications.add(m);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return medications;
}
public void markMedicationAsTaken(int id, int dosage) {
    try {
        // Get the medication based on ID
        String qrySelect = "SELECT * FROM medicaments WHERE id = ?";
        PreparedStatement pstmtSelect = cnx.prepareStatement(qrySelect);
        pstmtSelect.setInt(1, id);
        ResultSet rs = pstmtSelect.executeQuery();
        
        // Decrement the number of pills in inventory
        if (rs.next()) {
            String medicationName = rs.getString("nommed");
            String qryDecrement = "UPDATE inventory SET nbpl = nbpl - ? WHERE nommed = ?";
            PreparedStatement pstmtDecrement = cnx.prepareStatement(qryDecrement);
            pstmtDecrement.setInt(1, dosage);
            pstmtDecrement.setString(2, medicationName);
            pstmtDecrement.executeUpdate();
            
            // Check if inventory falls below 10 and display alert message
            String qryInventory = "SELECT nbpl FROM inventory WHERE nommed = ?";
            PreparedStatement pstmtInventory = cnx.prepareStatement(qryInventory);
            pstmtInventory.setString(1, medicationName);
            ResultSet rsInventory = pstmtInventory.executeQuery();
            if (rsInventory.next()) {
                int nbpl = rsInventory.getInt("nbpl");
                if (nbpl < 10) {
                    System.out.println("Alert: Inventory for " + medicationName + " is low. Restock needed.");
                }
            }
        }
        
        // Update the medication as taken
        String qryUpdate = "UPDATE medicaments SET pris = true WHERE id = ?";
        PreparedStatement pstmtUpdate = cnx.prepareStatement(qryUpdate);
        pstmtUpdate.setInt(1, id);
        pstmtUpdate.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public void checkInventory(int threshold) {
    try {
        String qrySelect = "SELECT * FROM inventory WHERE nbpel < ?";
        PreparedStatement pstmtSelect = cnx.prepareStatement(qrySelect);
        pstmtSelect.setInt(1, threshold);
        ResultSet rs = pstmtSelect.executeQuery();
        while (rs.next()) {
            String medicationName = rs.getString("nommed");
            int numPills = rs.getInt("nbpel");
            System.out.println("WARNING: " + medicationName + " inventory is low. Number of pills remaining: " + numPills);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
}
public ResultSet getNommedforcombobox() throws SQLException {
        Statement stmt = cnx.createStatement();
        String query = "SELECT nommed FROM inventory";
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }


public ResultSet getIdrappelforcombobox() throws SQLException {
        Statement stmt = cnx.createStatement();
        String query = "SELECT id FROM rappel";
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }



}