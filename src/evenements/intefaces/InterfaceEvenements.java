/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.intefaces;

import evenements.entities.Evenements;
import java.util.List;


public interface InterfaceEvenements {

    public void ajouterEvenements(Evenements r);

    public void modifierEvenements(Evenements r);

    public void supprimerEvenements(int id);


    public List<Evenements> afficherEvenements();
}
