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
import javafx.beans.property.SimpleStringProperty;
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

/**
 * FXML Controller class
 *
 * @author user
 */
public class MedicamentsController implements Initializable {
private MedicamentsService mcrud = new MedicamentsService();
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnajoutermedicaments;
    @FXML
    private TextField nommed;
    @FXML
    private TextField heuremed;
    @FXML
    private TextField dosagemed;
    @FXML
    private TextField idrappel;
    @FXML
    private TableView<Medicaments> tableMedicaments;
    @FXML
    private TableColumn<Medicaments, Integer> colId;
    @FXML
    private TableColumn<Medicaments, String> colNom;
    @FXML
    private TableColumn<Medicaments, String> colDosage;
    @FXML
    private TableColumn<Medicaments, String> colHeurePrise;
 private ObservableList<models.Medicaments> data;
    @FXML
    private Button btnrappel;

    public MedicamentsController() {
        this.data = FXCollections.observableArrayList();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
         colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nommed"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colHeurePrise.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHeurePrise().toString()));

        tableMedicaments.setItems(data);
        loadData();
    }

    private void loadData() {
        data.clear();
        data.addAll(mcrud.AfficherMedicaments());
    }    

    @FXML
    private void updatemed(ActionEvent event) {
    
     // Get the selected medication from the table view
    Medicaments m = tableMedicaments.getSelectionModel().getSelectedItem();
    if (m == null) {
        // If no medication is selected, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Medication Selected");
        alert.setContentText("Please select a medication to update");
        alert.showAndWait();
        return;
    }

    // Get the updated values from the input fields
    String nom = nommed.getText().trim();
    String dosage = dosagemed.getText().trim();
    String heure = heuremed.getText().trim();
    String idStr = idrappel.getText().trim();

    // Check that all input fields are not empty
    if (nom.isEmpty() || dosage.isEmpty() || heure.isEmpty() || idStr.isEmpty()) {
        // If any of the fields are empty, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Fields");
        alert.setContentText("Please fill in all fields");
        alert.showAndWait();
        return;
    }

    // Validate that the rappel_id field is a valid integer
    int rappel_id;
    try {
        rappel_id = Integer.parseInt(idStr);
    } catch (NumberFormatException e) {
        // If the rappel_id field is not a valid integer, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Rappel ID");
        alert.setContentText("Please enter a valid integer for the Rappel ID field");
        alert.showAndWait();
        return;
    }

    // Validate that the heurePrise field is in the format "hh:mm"
    if (!heure.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")) {
        // If the heurePrise field is not in the correct format, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Heure de Prise");
        alert.setContentText("Please enter a valid time in the format hh:mm for the Heure de Prise field");
        alert.showAndWait();
        return;
    }

    // Update the medication with the new values using the MedicamentsService class
    m.setNommed(nom);
    m.setDosage(dosage);
    m.setHeurePrise(heure);
    m.setRappel_id(rappel_id);
    if (mcrud.updateMedicaments(m)) {
        // If the medication was updated successfully, update the table view and clear the input fields
        loadData();
        nommed.clear();
        dosagemed.clear();
        heuremed.clear();
        idrappel.clear();
    } else {
        // If an error occurred while updating the medication, display an error message
        // (you can modify this to show a more user-friendly message)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Update Failed");
        alert.setContentText("An error occurred while updating the medication. Please try again later.");
        alert.showAndWait();
    }
    
    }

    @FXML
    private void deletmed(ActionEvent event) {
         // Get the selected medication from the table view
    Medicaments selectedMed = tableMedicaments.getSelectionModel().getSelectedItem();
    if (selectedMed == null) {
        // If no medication is selected, display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Medication Selected");
        alert.setContentText("Please select a medication to delete");
        alert.showAndWait();
        return;
    }

    // Display a confirmation dialog to the user to confirm the deletion
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Confirm Deletion");
    confirmAlert.setHeaderText("Confirm Deletion of Medication");
    confirmAlert.setContentText("Are you sure you want to delete the selected medication?");
    Optional<ButtonType> result = confirmAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // If the user confirms the deletion, delete the medication using the MedicamentsService class
        MedicamentsService medService = new MedicamentsService();
        medService.SupprimerMedicaments(selectedMed.getId());

        // Update the table view to reflect the deleted medication
        loadData();
    }
        
    }

    @FXML
    private void addmed(ActionEvent event) {
         String nom = nommed.getText().trim();
    String dosage = dosagemed.getText().trim();
    String heure = heuremed.getText().trim();
    String idStr = idrappel.getText().trim();

    // Check that all input fields are not empty
    if (nom.isEmpty() || dosage.isEmpty() || heure.isEmpty() || idStr.isEmpty()) {
        // If any of the fields are empty, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Fields");
        alert.setContentText("Please fill in all fields");
        alert.showAndWait();
        return;
    }

    // Validate that the rappel_id field is a valid integer
    int rappel_id;
    try {
        rappel_id = Integer.parseInt(idStr);
    } catch (NumberFormatException e) {
        // If the rappel_id field is not a valid integer, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Rappel ID");
        alert.setContentText("Please enter a valid integer for the Rappel ID field");
        alert.showAndWait();
        return;
    }

    // Validate that the heurePrise field is in the format "hh:mm"
    if (!heure.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")) {
        // If the heurePrise field is not in the correct format, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Heure de Prise");
        alert.setContentText("Please enter a valid time in the format hh:mm for the Heure de Prise field");
        alert.showAndWait();
        return;
    }

    // Create a new Medicaments object with the provided information
    Medicaments m = new Medicaments();
    m.setNommed(nom);
    m.setDosage(dosage);
    m.setHeurePrise(heure);
    m.setRappel_id(rappel_id);

    // Add the new medication to the database using the MedicamentsService class
    if (mcrud.addMedicaments(m)) {
        // If the medication was added successfully, update the table view and clear the input fields
        loadData();
        nommed.clear();
        dosagemed.clear();
        heuremed.clear();
        idrappel.clear();
    } else {
        // If an error occurred while adding the medication, display an error message
        // (you can modify this to show a more user-friendly message)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Database Error");
        alert.setContentText("An error occurred while adding the medication");
        alert.showAndWait();
    }
        
        
    }



    @FXML
    private void gererappel(ActionEvent event) {
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
