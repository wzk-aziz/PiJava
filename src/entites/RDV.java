/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;
import java.util.Date;
import java.sql.Time ;

/**
 *
 * @author neder
 */
public class RDV {
    private int id ; 
    private Date date_rdv ;
    private Time starttime , endtime ;
    private String titre,etat ; 
     private int id_patient_id;
      private int iduserrdv_id ;
    
    public RDV(){
    }

    public RDV(int id, Date date_rdv, Time starttime, Time endtime, String titre, String etat, int id_patient_id) {
        this.id = id;
        this.date_rdv = date_rdv;
        this.starttime = starttime;
        this.endtime = endtime;
        this.titre = titre;
        this.etat = etat;
        this.id_patient_id = id_patient_id;
    }

    public RDV(int id, Date date_rdv, Time starttime, Time endtime, String titre, String etat, int id_patient_id, int iduserrdv_id) {
        this.id = id;
        this.date_rdv = date_rdv;
        this.starttime = starttime;
        this.endtime = endtime;
        this.titre = titre;
        this.etat = etat;
        this.id_patient_id = id_patient_id;
        this.iduserrdv_id = iduserrdv_id;
    }

    public RDV(Date date_rdv, Time starttime, Time endtime, String titre, String etat, int id_patient_id, int iduserrdv_id) {
        this.date_rdv = date_rdv;
        this.starttime = starttime;
        this.endtime = endtime;
        this.titre = titre;
        this.etat = etat;
        this.id_patient_id = id_patient_id;
        this.iduserrdv_id = iduserrdv_id;
    }
    
    

    public RDV(int id, Date date_rdv, Time starttime, Time endtime, String titre, String etat) {
        this.id = id;
        this.date_rdv = date_rdv;
        this.starttime = starttime;
        this.endtime = endtime;
        this.titre = titre;
        this.etat = etat;
    }

     public RDV(int id ){
        this.id = id;
    } 

    

    @Override
    public String toString() {
        return "RDV{" + "id=" + id + ", date_rdv=" + date_rdv + ", starttime=" + starttime + ", endtime=" + endtime + ", titre=" + titre + ", etat=" + etat + ", id_patient_id=" + id_patient_id + ", iduserrdv_id=" + iduserrdv_id + '}';
    }

    public int getIduserrdv_id() {
        return iduserrdv_id;
    }

    public void setIduserrdv_id(int iduserrdv_id) {
        this.iduserrdv_id = iduserrdv_id;
    }

   


    

    public RDV(Date date_rdv, Time starttime, Time endtime, String titre, String etat) {
        this.date_rdv = date_rdv;
        this.starttime = starttime;
        this.endtime = endtime;
        this.titre = titre;
        this.etat = etat;
    }

    public int getId_patient_id() {
        return id_patient_id;
    }

    public void setId_patient_id(int id_patient_id) {
        this.id_patient_id = id_patient_id;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_rdv() {
        return date_rdv;
    }

    public void setDate_rdv(Date date_rdv) {
        this.date_rdv = date_rdv;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    
    
    
    
}
