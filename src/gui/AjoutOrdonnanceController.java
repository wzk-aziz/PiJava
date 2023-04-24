/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import services.ServiceOrdonnance ;
import entites.Ordonnance;
import entites.RDV;
import entites.User;
import java.io.IOException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import services.ServiceRdv;
import services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author neder
 */
public class AjoutOrdonnanceController implements Initializable {

    @FXML
    private JFXDatePicker DateO;
    @FXML
    private TextArea contenueO;
    @FXML
    private Button ajoutbtn;
 ServiceOrdonnance so = new ServiceOrdonnance() ;
    @FXML
    private ComboBox<String> comboPO;
    ServiceRdv cc = new ServiceRdv();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          ObservableList<String> patientListO = FXCollections.observableArrayList();
          ServiceRdv su = new ServiceRdv();
          ObservableList<RDV> obList = su.affichageRdv();
          for (RDV rdv : obList) {
   
        patientListO.add(rdv.getTitre());
    
}

comboPO.setItems(patientListO);
  }
    public int getRdvId(String titreP) {
      ServiceRdv su = new ServiceRdv();
    ObservableList<RDV> obList = su.affichageRdv();

    for (RDV rdv : obList) {
        if (rdv.getTitre().equals(titreP)) {
            return rdv.getId();
        }
    }

    return -1; 

    
    }   
    @FXML
    private void selectPatientOrd(ActionEvent event) {
         String titreP = comboPO.getSelectionModel().getSelectedItem().toString();
    int rdvId = getRdvId(titreP);
    System.out.println("Selected RDV ID: " + rdvId);
    }

    @FXML
    private void AjoutOrdHandle(ActionEvent event) {
        
         String contenue  = contenueO.getText();
       LocalDate dateo = DateO.getValue();
       ZoneId defaultZoneId = ZoneId.systemDefault();
         Date date = (Date) Date.from(dateo.atStartOfDay(defaultZoneId).toInstant());
       
              
          if (contenue.isEmpty()) {
        showAlert("contenue obligatoire", "contenue doit être non vide");
          }
     
     else {
           ServiceRdv su = new ServiceRdv();
              RDV rdv = su.getUserbyNom(comboPO.getSelectionModel().getSelectedItem().toString());
            Ordonnance ord = new Ordonnance(contenue, date,rdv.getId());
            so.ajouterOrd(ord);
            showAlert("ordonnance   ajouté", "ordonnance ajouté avec succès");
    }

    
}

    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    @FXML
    private void retourL(ActionEvent event) {
          try {
                  Parent   root = FXMLLoader.load(getClass().getResource("AffichageOrdonnance.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                };
    }

    
    }
    

