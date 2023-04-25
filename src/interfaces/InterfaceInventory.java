/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import models.Inventory;

/**
 *
 * @author user
 */
public interface InterfaceInventory {
    public void addInventory(Inventory i);
     public List<Inventory> AfficherInventory();
      public void SupprimerInventory(int id);
       public boolean updateInventory(Inventory i) ;
       public List<Inventory> searchByNameinv(String nommed);
}
