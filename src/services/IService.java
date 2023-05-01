/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entites.RDV;
import java.sql.Time;

import java.util.List;
import java.util.Date;


/**
 *
 * @author neder
 */
public interface IService <T> {
    public void ajouterRdv(RDV R);
    public List<RDV>affichageRdv ();
    public void supprimerRdv(int id);
    public RDV getRdv(int id);
    public void modifierRdv(RDV R);
     public List<RDV>affichageRdvTrieer ();
   public void   acceptRejectRdv(RDV R,String acceptReject);
   public RDV getRdvbyid(int idrdv);
   public RDV getUserbyNom(String titre); 
     public List<RDV>affichageRdvTrieerbynom ();
        public boolean isAvailable(Date dateRdv, Time starttime , Time endtime);
  
    
     
}
