/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;
import java.util.Date;

/**
 *
 * @author neder
 */
public class Ordonnance {
    private int id ; 
     private String contenue ; 
    private Date dateord ;
    private int idrdv_id ; 
    
     
     public Ordonnance(){
     
     }

    public Ordonnance(int id) {
        this.id = id;
    }

    public Ordonnance(int id, String contenue, Date dateord, int idrdv_id) {
        this.id = id;
        this.contenue = contenue;
        this.dateord = dateord;
        this.idrdv_id = idrdv_id;
    }

    public Ordonnance(String contenue, Date dateord, int idrdv_id) {
        this.contenue = contenue;
        this.dateord = dateord;
        this.idrdv_id = idrdv_id;
    }
     


    public Ordonnance(int id, String contenue, Date dateord) {
        this.id = id;
        this.contenue = contenue;
        this.dateord = dateord;
    }

    public Ordonnance(String contenue, Date dateord) {
        this.contenue = contenue;
        this.dateord = dateord;
    }

    public Ordonnance(int id, String contenue) {
        this.id = id;
        this.contenue = contenue;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public Date getDateord() {
        return dateord;
    }

    public void setDateord(Date dateord) {
        this.dateord = dateord;
    }

    public int getIdrdv_id() {
        return idrdv_id;
    }

    public void setIdrdv_id(int idrdv_id) {
        this.idrdv_id = idrdv_id;
    }

    @Override
    public String toString() {
        return "Ordonnance{" + "id=" + id + ", contenue=" + contenue + ", dateord=" + dateord + ", idrdv_id=" + idrdv_id + '}';
    }

  
    

    

    
 
      
}
