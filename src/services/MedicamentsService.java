/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import util.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Medicaments;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

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
            m.setDosage(rs.getString("dosage"));
            m.setHeurePrise(rs.getString("heureprise"));
            medications.add(m);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return medications;
}
public void generateExcel() {
    List<Medicaments> medicamentsList = AfficherMedicaments();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Medicaments Data");
    XSSFRow headerRow = sheet.createRow(0);

    // Add column names to the header row
    XSSFCell cell = headerRow.createCell(0);
    cell.setCellValue("ID");
    cell = headerRow.createCell(1);
    cell.setCellValue("Rappel ID");
    cell = headerRow.createCell(2);
    cell.setCellValue("Nommed");
    cell = headerRow.createCell(3);
    cell.setCellValue("Dosage");
    cell = headerRow.createCell(4);
    cell.setCellValue("Heureprise");

    // Add medicaments data to the rows
    int rowNum = 1;
    for (Medicaments medicament : medicamentsList) {
        XSSFRow row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(medicament.getId());
        row.createCell(1).setCellValue(medicament.getRappel_id());
        row.createCell(2).setCellValue(medicament.getNommed());
        row.createCell(3).setCellValue(medicament.getDosage());
        row.createCell(4).setCellValue(medicament.getHeurePrise());
    }

    try {
        // Write the workbook to an output stream
        FileOutputStream outputStream = new FileOutputStream("medicaments.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        System.out.println("Medicaments data written to Excel file successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
}
