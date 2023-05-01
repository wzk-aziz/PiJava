/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceRdv;
import entites.RDV;
import java.io.IOException;

import java.util.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import services.ServiceUser;
import entites.User ;
import java.time.Duration;
import utils.SendMail;


/**
 * FXML Controller class
 *
 * @author neder
 */
public class AjouterRDVController implements Initializable {

    @FXML
    private JFXTimePicker startR;
    @FXML
    private JFXTimePicker endR;
    @FXML
    private JFXDatePicker dateR;
     
    @FXML
    private TextField nomR;
    @FXML
    private TextField etatR;
    @FXML
    private Button ajoutbtn;

     ServiceRdv sr = new ServiceRdv() ;
    @FXML
    private ComboBox<String> comboPatient;
    @FXML
    private ComboBox<String> comboMedecin;
    ServiceUser cc = new ServiceUser();
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          etatR.setText("Pending");
        etatR.setEditable(false);
       ObservableList<String> patientList = FXCollections.observableArrayList();
ObservableList<String> medecinList = FXCollections.observableArrayList();

ServiceUser su = new ServiceUser();

ObservableList<User> obList = su.affichageUser();

for (User user : obList) {
    if (user.getRole() == 1) {
        patientList.add(user.getNom());
    } else if (user.getRole() == 2) {
        medecinList.add(user.getNom());
    }
}

comboPatient.setItems(patientList);
comboMedecin.setItems(medecinList);
    }
     
    public int getUserId(String userName) {
   ServiceUser su = new ServiceUser();
    ObservableList<User> obList = su.affichageUser();

    for (User user : obList) {
        if (user.getNom().equals(userName)) {
            return user.getId();
        }
    }

    return -1; // Si l'utilisateur n'est pas trouvée, retourne -1.
}
 @FXML
    private void selectPatient(ActionEvent event) {
           String userName = comboPatient.getSelectionModel().getSelectedItem().toString();
    int userId = getUserId(userName);
    System.out.println("Selected user ID: " + userId);
    }

    @FXML
    private void selectMedecin(ActionEvent event) {
         String userName = comboMedecin.getSelectionModel().getSelectedItem().toString();
    int userId = getUserId(userName);
    System.out.println("Selected user ID: " + userId);
    }    

    @FXML
    private void AjoutRdvHandel(ActionEvent event)throws Exception  {
             String titre  = nomR.getText();
    String etat  = etatR.getText();
    LocalDate dateRdv = dateR.getValue();
    LocalTime heureDebut = startR.getValue();
    LocalTime heureFin = endR.getValue();

    if (titre.isEmpty()) {
        showAlert("Nom obligatoire", "Le nom doit être non vide");
    } else if (etat.isEmpty()) {
        showAlert("État obligatoire", "L'état doit être non vide");
    } else if (dateRdv == null || dateRdv.isBefore(LocalDate.now())) {
        showAlert("Date invalide", "Veuillez saisir une date valide ");
    }  else if (heureDebut == null || heureDebut.isBefore(LocalTime.of(9, 0)) || heureDebut.isAfter(LocalTime.of(16, 0))|| (heureDebut.getMinute() != 0 && heureDebut.getMinute() != 30)) {
        showAlert("Heures invalides", "heure de debut entre 9h du matin et 16:30 du soir");
    }  else if (heureDebut == null || heureDebut.isBefore(LocalTime.of(9, 0)) || heureDebut.isAfter(LocalTime.of(16, 0))|| (heureDebut.getMinute() != 0 && heureDebut.getMinute() != 30)) {
        showAlert("Heures invalides", "la consultation demmare a heure pile ou a 30 minutes");
         
    } else if (heureFin == null || heureFin.isBefore(LocalTime.of(9, 0)) || heureFin.isAfter(LocalTime.of(17, 0))  ) {
        showAlert("Heures invalides", "heure de fin entre 9:30h du matin et 17 du soir");
     
    } else if (heureDebut == null || heureFin == null ||  Duration.between(heureDebut, heureFin).toMinutes() < 30) {
        showAlert("Heures invalides", "la consultation est de 30 minutes ");
        
    } 
    else if (heureDebut == null || heureFin == null ||  Duration.between(heureDebut, heureFin).toMinutes() > 30) {
        showAlert("Heures invalides", "la consultation est de 30 minutes ");  
    } 
       else {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(dateRdv.atStartOfDay(defaultZoneId).toInstant());
        Time timeDebut = Time.valueOf(heureDebut);
        Time timeFin = Time.valueOf(heureFin);
          // Check if the selected date and time slot is available
        boolean isAvailable = sr.isAvailable(date, timeDebut, timeFin);

        if (!isAvailable) {
            showAlert("heure et date non disponible", "Le créneau sélectionné n'est pas disponible");
             } else {
        ServiceUser su = new ServiceUser();
            User user = su.getUserbyNom(comboPatient.getSelectionModel().getSelectedItem().toString());
            User u = su.getUserbyNom(comboMedecin.getSelectionModel().getSelectedItem().toString());
        RDV rdv = new RDV(date, timeDebut, timeFin, titre, etat ,user.getId(), u.getId());
        sr.ajouterRdv(rdv);
        showAlert("Rendez-vous ajouté", "Rendez-vous ajouté avec succès");
        // Récupération de l'utilisateur concerné par le rendez-vous
User medecin = su.getUserById(u.getId());

String sujet = "Nouveau rendez-vous";
String corps = "<html><body style='background-color:#ecf0f1;'>";
corps += "<h1 style='color:#228032;'>Nouveau rendez-vous</h1>";
corps += "<p style='color:#1abc9c;'>Bonjour " + medecin.getPrenom() + ",</p>";
corps += "<p style='color:#3498db;'>Nous vous informons qu'un nouveau rendez-vous a été ajouté :</p>";
corps += "<ul>";
corps += "<li><strong style='color:#3a8eba;'>Date :</strong> <span style='color:#34495e;'>" + dateRdv + "</span></li>";
corps += "<li><strong style='color:#3a8eba;'>Heure de début :</strong> <span style='color:#34495e;'>" + heureDebut + "</span></li>";
corps += "<li><strong style='color:#3a8eba;'>Heure de fin :</strong> <span style='color:#34495e;'>" + heureFin + "</span></li>";
corps += "<li><strong style='color:#3a8eba;'>Nom du patient :</strong> <span style='color:#34495e;'>" + titre + "</span></li>";
corps += "<li><strong style='color:#3a8eba;'>Etat :</strong> <span style='color:#34495e;'>" + etat + "</span></li>";
corps += "</ul>";
corps += "<p style='color:#3498db;'>Cordialement,<br>L'équipe médicale</p>";
corps += "</body></html>";

SendMail sendEmail = new SendMail("pidev.chronicaid@gmail.com", "qajfcqlxtcwxinnx", medecin.getEmail(), sujet, corps);
        }
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
    private void retour(ActionEvent event) {
        try {
                  Parent   root = FXMLLoader.load(getClass().getResource("AffichageRdv.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                };
    }

   
    
}
