/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Inventory;
import services.InventoryService;

/**
 * FXML Controller class
 */
public class InventoryController implements Initializable {
    private ObservableList<Inventory> inventoryData;
    private InventoryService icrud = new InventoryService();
    private ObservableList<Inventory> data = FXCollections.observableArrayList();

    @FXML
    private TextField nommed;

    @FXML
    private ChoiceBox<Integer> nbplChoiceBox;

    @FXML
    private Button btnajouterinv;

    @FXML
    private Button btnsupprimerinv;

    @FXML
    private TableView<Inventory> plTable;

    @FXML
    private TableColumn<Inventory, String> nomcol;

    @FXML
    private TableColumn<Inventory, Integer> plcol;
    
    @FXML
    private TableColumn<Inventory, Integer> idcol;

    private InventoryService inventoryService;
    @FXML
    private Button homebtn;
    @FXML
    private Button btnrestock;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up choice box
        ObservableList<Integer> choices = FXCollections.observableArrayList();
        for (int i = 0; i <= 30; i++) {
            choices.add(i);
        }
        nbplChoiceBox.setItems(choices);

        // Set up table view
        inventoryService = new InventoryService();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nommed"));
        plcol.setCellValueFactory(new PropertyValueFactory<>("nbpl"));
        plTable.setItems(data);
        loadData();
    }

    private void loadData() {
        data.clear();
        data.addAll(icrud.AfficherInventory());
        // Check if any inventory item has nbpl less than 10
    for (Inventory inv : data) {
        if (inv.getNbpl() < 10) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Le nombre de pellules de  " + inv.getNommed() + " est faible. Réapprovisionnez-le dès maintenant.");
            alert.showAndWait();
            break; // Show the alert only once
        }
    }
    }

    @FXML
    private void addinv(ActionEvent event) {
         String nommedText = nommed.getText().trim();
    int nbpl = nbplChoiceBox.getValue();

    // Check if required fields are not empty
    if (!nommedText.isEmpty() && nbpl > 0) {
        // Create new Inventory object and add it to database
        Inventory inv = new Inventory(nommedText, nbpl);
        icrud.addInventory(inv);
        data.add(inv);
        plTable.refresh();
        nommed.clear();
        nbplChoiceBox.setValue(0);
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
    }
}

    @FXML
    private void deleteinv(ActionEvent event) {
        // Get selected inventory item and remove it from database and table view
    Inventory selectedItem = plTable.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
        icrud.SupprimerInventory(selectedItem.getId());
        data.remove(selectedItem);
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Veuillez sélectionner un élément à supprimer.");
        alert.showAndWait();
    }
    }

    @FXML
    private void gohome(ActionEvent event) {
     try {
        Parent inscrParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene inscrscene = new Scene(inscrParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscrscene);
        window.show();
    } catch (IOException e) {
    }
        
    
    }

   @FXML
private void restockinv(ActionEvent event) {
    // Get selected inventory item and the desired number of items
    Inventory selectedItem = plTable.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Restock pills");
        dialog.setHeaderText("Restocker :" + selectedItem.getNommed());
        dialog.setContentText("Enter the new number of pills (max 30):");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Parse the user input and update the inventory item
            int nbpl = Integer.parseInt(result.get());
            if (nbpl > 30) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Le nombre de médicaments ne peut pas dépasser 30.");
                alert.showAndWait();
                return;
            }
            selectedItem.setNbpl(nbpl);
            icrud.updateInventory(selectedItem);
            plTable.refresh();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Veuillez sélectionner un médicament à réapprovisionner.");
        alert.showAndWait();
    }
}
}