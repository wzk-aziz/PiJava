/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.intefaces;

import evenements.entities.Inscriptions;
import java.util.List;


public interface InterfaceInscriptions {
    public void ajouterInscriptions(Inscriptions a);

    public void modifierInscriptions(Inscriptions a);

    public void supprimerInscriptions(int id);


    public List<Inscriptions> afficherInscriptions();
    
}
