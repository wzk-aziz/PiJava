/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.entities;
//import java.util.Date;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.Callback;


public class Evenements {
private int id;
    private String titre;
    private String description;
    private String date;
    private String photo;

    public Evenements(String titre, String description, String date, String photo) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.photo = photo;
    }

    public Evenements(int id, String titre, String description, String date, String photo) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Evenements{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", date=" + date + ", photo=" + photo + '}';
    }

    
    
    
    
    
    
    
    
    
    
    
    
}
