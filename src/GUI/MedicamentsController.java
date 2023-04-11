/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import javafx.scene.control.TableView;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Medicaments;
import services.MedicamentsService;
import services.RappelService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MedicamentsController implements Initializable {
private TableView<Medicaments> medicamentsTable;
  @FXML
    private TableView<Medicaments> tableMedicaments;
    @FXML
    private TableColumn<Medicaments, Integer> colId;
    @FXML
    private TableColumn<Medicaments, Integer> colRappelId;
    @FXML
    private TableColumn<Medicaments, String> colNom;
    @FXML
    private TableColumn<Medicaments, String> colDosage;
    @FXML
    private TableColumn<Medicaments, String> colHeurePrise;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private TextField nommed;
    @FXML
    private Button btnajoutermedicaments;
    @FXML
    private Button btnmed;
    @FXML
    private TextField heuremed;
    @FXML
    private TextField idrappel;
    @FXML
    private TextField dosagemed;

     private MedicamentsService medicamentsService;
    private RappelService rappelService;
    private ObservableList<Medicaments> medicamentsList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      medicamentsService = new MedicamentsService();
        rappelService = new RappelService();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRappelId.setCellValueFactory(new PropertyValueFactory<>("rappel_id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nommed"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colHeurePrise.setCellValueFactory(new PropertyValueFactory<>("heurePrise"));

        medicamentsList = FXCollections.observableArrayList(medicamentsService.AfficherMedicaments());
        tableMedicaments.setItems(medicamentsList);
    }    

    @FXML
    private void afficherMedSelected(MouseEvent event) {
        Medicaments m = tableMedicaments.getSelectionModel().getSelectedItem();
        if (m != null) {
            nommed.setText(m.getNommed());
            idrappel.setText(Integer.toString(m.getRappel_id()));
            dosagemed.setText(m.getDosage());
            heuremed.setText(m.getHeurePrise());
        }
    }

    @FXML
    private void updatemed(ActionEvent event) {
         Medicaments m = tableMedicaments.getSelectionModel().getSelectedItem();
    if (m != null) {
        // Vérifier que tous les champs sont remplis
        if (nommed.getText().isEmpty() || idrappel.getText().isEmpty() || dosagemed.getText().isEmpty() || heuremed.getText().isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur de saisie");
    alert.setContentText("Tous les champs sont requis");
    alert.showAndWait();
    return;
}
int rappel_id;
try {
    rappel_id = Integer.parseInt(idrappel.getText());
} catch (NumberFormatException e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur de saisie");
    alert.setContentText("L'identifiant de rappel doit être un entier");
    alert.showAndWait();
    return;
}

if (!heuremed.getText().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur de saisie");
    alert.setContentText("L'heure de prise doit être au format hh:mm");
    alert.showAndWait();
    return;
}

if (medicamentsService.updateMedicaments(m)) {
    medicamentsList.set(medicamentsList.indexOf(m), m);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Mise à jour réussie");
    alert.setContentText("Médicament mis à jour avec succès");
    alert.showAndWait();
} else {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setContentText("Erreur lors de la mise à jour du médicament");
    alert.showAndWait();
}

    }

    }

    @FXML


private void deletmed(ActionEvent event) {
   Medicaments selectedMedicament = medicamentsTable.getSelectionModel().getSelectedItem();
    if (selectedMedicament != null) {
        medicamentsService.SupprimerMedicaments(selectedMedicament.getId());
        medicamentsList.remove(selectedMedicament);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression réussie");
        alert.setContentText("Médicament supprimé avec succès");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez sélectionner un médicament à supprimer");
        alert.showAndWait();
    }
}




    @FXML
    private void addmed(ActionEvent event) {
        // Vérifier que tous les champs sont remplis
    if (nommed.getText().isEmpty() || idrappel.getText().isEmpty() || dosagemed.getText().isEmpty() || heuremed.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("Tous les champs sont requis");
        alert.showAndWait();
        return;
    }
    
    // Vérifier que l'identifiant de rappel est un entier
    int rappel_id;
    try {
        rappel_id = Integer.parseInt(idrappel.getText());
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("L'identifiant de rappel doit être un entier");
        alert.showAndWait();
        return;
    }
    
    // Vérifier que l'heure de prise est au format hh:mm
    if (!heuremed.getText().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("L'heure de prise doit être au format hh:mm");
        alert.showAndWait();
        return;
    }
    
    // Créer un nouveau médicament avec les valeurs saisies
    Medicaments newMedicament = new Medicaments(nommed.getText(), dosagemed.getText(), heuremed.getText(), rappel_id);
    
    // Ajouter le médicament à la base de données
    if (medicamentsService.addMedicaments(newMedicament)) {
        medicamentsList.add(newMedicament);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout réussi");
        alert.setContentText("Le médicament a été ajouté avec succès");
        alert.showAndWait();
        
        // Réinitialiser les champs de saisie
        nommed.setText("");
        idrappel.setText("");
        dosagemed.setText("");
        heuremed.setText("");
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Erreur lors de l'ajout du médicament");
        alert.showAndWait();
    }
    }

    @FXML
    private void gerermedicaments(ActionEvent event) {
         try {
        Parent inscrParent = FXMLLoader.load(getClass().getResource("Rappel.fxml"));
        Scene inscrscene = new Scene(inscrParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscrscene);
        window.show();
    } catch (IOException e) {
    }
    }
    
}
