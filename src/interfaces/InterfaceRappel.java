/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;
import java.util.List;
import models.Rappel;
/**
 *
 * @author user
 */
public interface InterfaceRappel {
    
    void ajouterRappel(Rappel ra);

    List<Rappel> afficherRappel();

    void supprimerRappel(int id);

    void updateRappel(Rappel ra);

}
