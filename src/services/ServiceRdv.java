/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entites.RDV ;
import java.util.List;
import java.sql.*;
import utils.MYDB;
import java.util.Date;
import java.sql.Time ;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Calendar ;

/**
 *
 * @author neder
 */
public class ServiceRdv implements IService<RDV> {
    ObservableList<RDV>obListR = FXCollections.observableArrayList();
    Connection conn ; 
    public ServiceRdv(){
      conn = MYDB.getInstance().getConnection();
    }

    @Override
    public void ajouterRdv(RDV R) {
     
       String  req ="INSERT INTO rdv(date_rdv,starttime,endtime,titre,etat,id_patient_id,iduserrdv_id )values(?,?,?,?,?,?,?)";

       PreparedStatement stm;
        try {
            stm = conn.prepareStatement(req);
                     stm.setDate(1, new java.sql.Date(R.getDate_rdv().getTime()));
                    stm.setTime(2, R.getStarttime());           
                    stm.setTime(3, R.getEndtime());

                    stm.setString(4, R.getTitre());
                    stm.setString(5, R.getEtat());
                      stm.setInt(6, R.getId_patient_id());
                        stm.setInt(7, R.getIduserrdv_id());
   


        
            stm.executeUpdate();
                    System.out.println("rendez vous ajoutée");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public ObservableList<RDV> affichageRdv() {
         String req= "SELECT * FROM rdv";;
        List<RDV>listR = new ArrayList<>();
        
        
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(req);
            
            while(rs.next()) {
                int id= rs.getInt("id");
                 int idPatient= rs.getInt("id_patient_id");
                Date date_rdv = rs.getDate("date_rdv");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                String titre = rs.getString("titre");
               String etat = rs.getString("etat");
              
                
                RDV p = new RDV(id,date_rdv,starttime,endtime,titre,etat,idPatient);
              
               obListR.add(p);

              
            }   
            
            
            
        }catch(Exception ex) {
            System.out.println("exception ="+ex.getMessage() );
        }
        return obListR;
    }

    @Override
    public void supprimerRdv(int id) {
       String req="DELETE from rdv WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("rendez vous supprimé avec succés!!!");
            
        }catch (SQLException ex) {
        Logger.getLogger(ServiceRdv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @Override
    public void modifierRdv(RDV R) {
         String req ="UPDATE rdv  SET date_rdv =?, starttime=?, endtime=?, titre=?, etat=? WHERE id=?";
      
        
       PreparedStatement stm;
        try {
            stm = conn.prepareStatement(req);
                 stm.setDate(1, new java.sql.Date(R.getDate_rdv().getTime()));
                    stm.setTime(2, R.getStarttime());           
                    stm.setTime(3, R.getEndtime());
                    stm.setString(4, R.getTitre());
                    stm.setString(5, R.getEtat());
   
        stm.setInt(6, R.getId());

        
            stm.executeUpdate();
                    System.out.println("rendez vous modifié avec succés");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public RDV getRdv(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<RDV> affichageRdvTrieer() {
        String req= "SELECT * FROM rdv order by date_rdv DESC";;
        List<RDV>listR = new ArrayList<>();
        
        
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(req);
            
            while(rs.next()) {
                int id= rs.getInt("id");
                Date date_rdv = rs.getDate("date_rdv");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                String titre = rs.getString("titre");
               String etat = rs.getString("etat");
               int idPatient= rs.getInt("id_patient_id");
                
                RDV p = new RDV(id,date_rdv,starttime,endtime,titre,etat,idPatient);
              
               obListR.add(p);

              
            }   
            
            
            
        }catch(Exception ex) {
            System.out.println("exception ="+ex.getMessage() );
        }
        return obListR;
    }
     @Override
    public List<RDV> affichageRdvTrieerbynom() {
          String req= "SELECT * FROM rdv order by titre ASC";;
        List<RDV>listR = new ArrayList<>();
        
        
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(req);
            
            while(rs.next()) {
                int id= rs.getInt("id");
                Date date_rdv = rs.getDate("date_rdv");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                String titre = rs.getString("titre");
               String etat = rs.getString("etat");
               int idPatient= rs.getInt("id_patient_id");
                
                RDV p = new RDV(id,date_rdv,starttime,endtime,titre,etat,idPatient);
              
               obListR.add(p);

              
            }   
            
            
            
        }catch(Exception ex) {
            System.out.println("exception ="+ex.getMessage() );
        }
        return obListR;
    }

    @Override
    public void acceptRejectRdv(RDV R, String acceptReject) {
       String req ="UPDATE rdv  SET etat = ? WHERE id = ?";
      
       PreparedStatement stm;
        try {
            stm = conn.prepareStatement(req);
 
                    stm.setString(1, acceptReject);
      
        stm.setInt(2, R.getId());

        
            stm.executeUpdate();
                    System.out.println("rendez vous modifié avec succés");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public RDV getRdvbyid(int idrdv) {
       String req= "SELECT * FROM rdv WHERE id =?";

        RDV r = new RDV();
        try{
            //Statement statement = conn.createStatement();
            //ResultSet rs = statement.executeQuery(req);
                  PreparedStatement statement = conn.prepareStatement(req);
        statement.setInt(1, idrdv);
        ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                int id= rs.getInt("id");
                Date date_rdv = rs.getDate("date_rdv");
                Time starttime = rs.getTime("starttime");
                Time endtime = rs.getTime("endtime");
                String titre = rs.getString("titre");
               String etat = rs.getString("etat");
               int idPatient= rs.getInt("id_patient_id");
                  r = new RDV(id,date_rdv,starttime,endtime,titre,etat,idPatient);
              
                System.out.println(r);
             

              
            }   
            
            
            
        }catch(Exception ex) {
            System.out.println("exception ="+ex.getMessage() );
        }
        
        return r;
    }

    @Override
    public RDV getUserbyNom(String titre) {
        String req = "SELECT * FROM rdv WHERE titre = ?";
    RDV rdv = null;
    try {
        PreparedStatement ps = conn.prepareStatement(req);
        ps.setString(1, titre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            rdv = new RDV(rs.getInt("id"), rs.getString("titre"));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération de la liste : " + ex.getMessage());
    }
    return rdv;
    }

    @Override
    public boolean isAvailable(Date dateRdv, Time starttime , Time endtime) {
       // récupérer la liste de tous les rendez-vous existants
    List<RDV> rdvs = affichageRdv();

    // Vérifier si la date du rendez-vous est un samedi ou dimanche
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateRdv);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
        return false;
    }

    // vérifier si la date et l'heure du rendez-vous en paramètre sont disponibles
    for (RDV rdv : rdvs) {
        if (dateRdv.equals(rdv.getDate_rdv())) {
            // la date du rendez-vous en paramètre correspond à une date de rendez-vous existant
            return false;
        } else if (dateRdv.before(rdv.getEndtime()) && dateRdv.after(rdv.getStarttime())) {
            // la date du rendez-vous en paramètre est comprise entre la date de début et la date de fin d'un rendez-vous existant
            return false;
        }
    }

    // si la date et l'heure du rendez-vous en paramètre ne correspondent à aucun rendez-vous existant, alors elles sont disponibles
    return true;
}

   

   

    
    }

 