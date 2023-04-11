/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.entities;

/**
 *
 * @author rania
 */
public class Inscriptions {
   private int id;
    private int utilisateur_id;
    private int evenements_id ;
    private String dateinscrievent;
    private String descins;

    public Inscriptions(int utilisateur_id, int evenements_id, String dateinscrievent, String descins) {
        this.utilisateur_id = utilisateur_id;
        this.evenements_id = evenements_id;
        this.dateinscrievent = dateinscrievent;
        this.descins = descins;
    }

    public Inscriptions(int id, int utilisateur_id, int evenements_id, String dateinscrievent, String descins) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.evenements_id = evenements_id;
        this.dateinscrievent = dateinscrievent;
        this.descins = descins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public int getEvenements_id() {
        return evenements_id;
    }

    public void setEvenements_id(int evenements_id) {
        this.evenements_id = evenements_id;
    }

    public String getDateinscrievent() {
        return dateinscrievent;
    }

    public void setDateinscrievent(String dateinscrievent) {
        this.dateinscrievent = dateinscrievent;
    }

    public String getDescins() {
        return descins;
    }

    public void setDescins(String descins) {
        this.descins = descins;
    }

    @Override
    public String toString() {
        return "Inscriptions{" + "id=" + id + ", utilisateur_id=" + utilisateur_id + ", evenements_id=" + evenements_id + ", dateinscrievent=" + dateinscrievent + ", descins=" + descins + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}






