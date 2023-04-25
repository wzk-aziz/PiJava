/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author user
 */
import java.sql.Connection;
import util.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Rappel;

public class RappelService {
    private Connection cnx;

    public RappelService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void AjouterRappel(Rappel ra) {
        try {
            String qry = "INSERT INTO rappel(message,nomrappel) VALUES (?, ?)";
            PreparedStatement stm = cnx.prepareStatement(qry);
            //stm.setInt(1, ra.getUtilisateur_id());
            stm.setString(1, ra.getMessage());
             stm.setString(2, ra.getNomrappel());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Rappel> AfficherRappel() {
        List<Rappel> Rappel = new ArrayList<>();
        try {
            String qry = "SELECT * FROM rappel";
            PreparedStatement stm = cnx.prepareStatement(qry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Rappel r = new Rappel();
                r.setId(rs.getInt("id"));
                r.setMessage(rs.getString("message"));
               // r.setUtilisateur_id(rs.getInt("utilisateur_id"));
                r.setNomrappel(rs.getString("nomrappel"));
                Rappel.add(r);
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Rappel;
    }

    public void SupprimerRappel(int id) {
        try {
            String qry = "DELETE FROM rappel WHERE id = ?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update(Rappel ra) {
        try {
            String qry = "UPDATE rappel SET  message = ?,nomrappel= ? WHERE id = ?";
            PreparedStatement stm = cnx.prepareStatement(qry);
           
            stm.setString(1, ra.getMessage());
            stm.setString(2,ra.getNomrappel());
            stm.setInt(3, ra.getId());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}