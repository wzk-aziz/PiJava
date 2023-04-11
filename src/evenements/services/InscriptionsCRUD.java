/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.services;

import evenements.entities.Inscriptions;
import evenements.utils.MyConnection;
import evenements.entities.Evenements;
import evenements.entities.Inscriptions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import evenements.intefaces.InterfaceInscriptions;
import java.sql.Statement;

/**
 *
 * @author rania
 */
public class InscriptionsCRUD implements InterfaceInscriptions{

    @Override
    public void ajouterInscriptions(Inscriptions a) {
         try {

            String requete1 = "INSERT INTO Inscriptions(utilisateur_id ,evenements_id ,dateinscrievent,descins) VALUES(?,?,STR_TO_Date(?,'%Y-%m-%d'),?)";
            PreparedStatement pst = evenements.utils.MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setInt(1, a.getUtilisateur_id());
            pst.setInt(2, a.getEvenements_id());

            pst.setString(3, a.getDateinscrievent());
            pst.setString(4, a.getDescins());
            pst.executeUpdate();
            System.out.println("Inscri ajoutee avec succes !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
        
        
    }

    @Override
    public void modifierInscriptions(Inscriptions a) {
        try {
            String requete4;
            requete4 = " UPDATE Inscriptions SET utilisateur_id =?,evenements_id=?,dateinscrievent=?,descins=? WHERE id=?" ;
            PreparedStatement pst = evenements.utils.MyConnection.getInstance().getCnx().prepareStatement(requete4);
             pst.setInt(1, a.getUtilisateur_id());
            pst.setInt(2, a.getEvenements_id());

            pst.setString(3, a.getDateinscrievent());
            pst.setString(4, a.getDescins());
            
            pst.setInt(5, a.getId());
               pst.executeUpdate();
            System.out.println("inscri modifie");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }   
        
        
        
        
        
        
    }

    @Override
    public void supprimerInscriptions(int id) {
         try {
            String req = "DELETE FROM Inscriptions WHERE id = " + id;
            Statement st = evenements.utils.MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Inscri supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @Override
    public List<Inscriptions> afficherInscriptions() {
        ArrayList<Inscriptions> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM Inscriptions";
            Statement st = evenements.utils.MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            
              while (rs.next()) {
                Inscriptions r = new Inscriptions(rs.getInt("id"), rs.getInt("utilisateur_id"),rs.getInt("evenements_id"), rs.getString("dateinscrievent"), rs.getString("descins"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            }
        return myList;        
        
        
        
    }
    }

