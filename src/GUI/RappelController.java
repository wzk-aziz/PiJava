/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Rappel;
import services.RappelService;

public class RappelController implements Initializable {
    private RappelService rcrud = new RappelService();

    @FXML
    private TableView<Rappel> tableRappel;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private TextField nomrappel;
    @FXML
    private TextField messagerappel;
    @FXML
    private Button btnajouterrappel;
    @FXML
    private Button btnmed;
   
    @FXML
    private TableColumn<Rappel, String> colNom;
    @FXML
    private TableColumn<Rappel, String> colMessage;
    
    private ObservableList<Rappel> data = FXCollections.observableArrayList();
    @FXML
    private Button btnhome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
       colNom.setCellValueFactory(new PropertyValueFactory<>("nomrappel"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        
        tableRappel.setItems(data);
        loadData();
    }

    private void loadData() {
        data.clear();
        data.addAll(rcrud.AfficherRappel());
    }

    @FXML
    private void updaterappel(ActionEvent event) {
        Rappel r = tableRappel.getSelectionModel().getSelectedItem();
        if (r == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur de sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un rappel à mettre à jour!");
            alert.showAndWait();
        } else {
            r.setNomrappel(nomrappel.getText());
            r.setMessage(messagerappel.getText());
            rcrud.update(r);
            loadData();
        }
        // Set the values of the input fields to the selected medication's information
        messagerappel.setText(r.getMessage());
    }

    @FXML
    private void deleterappel(ActionEvent event) {
        Rappel r = tableRappel.getSelectionModel().getSelectedItem();
        if (r == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur de sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un rappel à supprimer!");
            alert.showAndWait();
        } else {
            rcrud.SupprimerRappel(r.getId());
            loadData();
        }
    }

    private void medicaments(ActionEvent event) {
        // Ajoutez ici le code pour changer de vue et afficher la liste des médicaments
            try {
        Parent inscrParent = FXMLLoader.load(getClass().getResource("Medicaments.fxml"));
        Scene inscrscene = new Scene(inscrParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscrscene);
        window.show();
    } catch (IOException e) {
    }
    }

    @FXML
    
private void addrappel(ActionEvent event) {
    String nom = nomrappel.getText();
    String message = messagerappel.getText();
    
    if (!nom.isEmpty() && !message.isEmpty()) {
        Rappel r = new Rappel(nom, message);
        rcrud.AjouterRappel(r);
        data.add(r);
        tableRappel.refresh();
        nomrappel.clear();
        messagerappel.clear();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
    }
}

    @FXML
    private void afficherRappelSelected(MouseEvent event) {
   
    
    }

    @FXML
    private void gerermedicaments(ActionEvent event) {
            try {
        Parent inscrParent = FXMLLoader.load(getClass().getResource("Medicaments.fxml"));
        Scene inscrscene = new Scene(inscrParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscrscene);
        window.show();
    } catch (IOException e) {
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
    
    
   

}
