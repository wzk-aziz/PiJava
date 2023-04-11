/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import models.Medicaments;

/**
 *
 * @author user
 */
public interface InterfaceMedicaments {
    public void addMedicaments(Medicaments m);
    
    public List<Medicaments> AfficherMedicaments();
    
    public void SupprimerMedicaments(int id);
    
    public void updateMedicaments(Medicaments m);
}
