/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
private Medicaments selectedMedicament;

    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnajoutermedicaments;
    @FXML
    private TextField dosagemed;
    @FXML
    private TableView<Medicaments> tableMedicaments;
    @FXML
    private TableColumn<Medicaments,Integer> colId;
    @FXML
    private TableColumn<Medicaments,String> colNom;
    @FXML
    private TableColumn<Medicaments,Integer> colDosage;
    @FXML
    private TableColumn<Medicaments,String> colHeurePrise;
    @FXML
    private ChoiceBox<String> heureChoiceBox;
    @FXML
    private Button btntaken;
    @FXML
    private Button btnhome;
    @FXML
    private ComboBox<String> nommedComboBox;
    @FXML
    private ComboBox<Integer> idrappelComboBox;
    
    private MedicamentsService medicamentsservice;
    private ObservableList<models.Medicaments> data;
    @FXML
    private TableColumn<Medicaments, Integer> colrappelid;
    @FXML
    private Button btnverif;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
    //populate combobox nommed:
    medicamentsservice= new MedicamentsService();
     try {
            ResultSet rs = medicamentsservice.getNommedforcombobox();
            while (rs.next()) {
                nommedComboBox.getItems().add(rs.getString("nommed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     
     //populate comboboxidrappel
     
      try {
            ResultSet rsid = medicamentsservice.getIdrappelforcombobox();
            while (rsid.next()) {
                idrappelComboBox.getItems().add(rsid.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      ObservableList<String> hours = FXCollections.observableArrayList();
for (int i = 0; i < 24; i++) {
    hours.add(String.format("%02d:00", i));
    hours.add(String.format("%02d:30", i));
}
heureChoiceBox.setItems(hours);
// Initialize the table view columns
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colrappelid.setCellValueFactory(new PropertyValueFactory<>("rappel_id"));
    colNom.setCellValueFactory(new PropertyValueFactory<>("nommed"));
    colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
    colHeurePrise.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHeurePrise()));
    // Initialize the table view data and load data from database
    data = FXCollections.observableArrayList();
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
    Medicaments selectedMed = tableMedicaments.getSelectionModel().getSelectedItem();
    if (selectedMed == null) {
        // If no medication is selected, display an error message and return
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Medication Selected");
        alert.setContentText("Please select a medication to update");
        alert.showAndWait();
        return;
    }

    // Fill the input fields with the data of the selected medication
    nommedComboBox.setValue(selectedMed.getNommed());
    dosagemed.setText(String.valueOf(selectedMed.getDosage()));
    heureChoiceBox.setValue(selectedMed.getHeurePrise());
    idrappelComboBox.setValue(selectedMed.getRappel_id());

    // Disable the update button until the user modifies the medication fields
    btnupdate.setDisable(true);

    // Enable the update button when the user modifies any medication field
    ChangeListener<String> fieldChangeListener = (observable, oldValue, newValue) -> {
        btnupdate.setDisable(false);
    };
    nommedComboBox.valueProperty().addListener(fieldChangeListener);
    dosagemed.textProperty().addListener(fieldChangeListener);
    heureChoiceBox.valueProperty().addListener(fieldChangeListener);
   
    // Update the medication using the MedicamentsService class when the update button is pressed
    btnupdate.setOnAction((ActionEvent event2) -> {
        selectedMed.setNommed(nommedComboBox.getValue());
         selectedMed.setDosage(Integer.parseInt(dosagemed.getText()));
        selectedMed.setHeurePrise(heureChoiceBox.getValue());
        selectedMed.setRappel_id(idrappelComboBox.getValue());
        mcrud.updateMedicaments(selectedMed);
        btnupdate.setDisable(true);
       // Clear the input fields and reload the data in the table view
        clearFields();
        loadData();
        // Remove the field change listener to avoid unnecessary updates
        nommedComboBox.valueProperty().removeListener(fieldChangeListener);
        dosagemed.textProperty().removeListener(fieldChangeListener);
        heureChoiceBox.valueProperty().removeListener(fieldChangeListener);
       
        // Display a confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Medication Updated");
        alert.setContentText("The medication has been successfully updated");
        alert.showAndWait();
    });
    }
    
    private void clearFields() {
    nommedComboBox.setValue(null);
    dosagemed.setText("");
    heureChoiceBox.setValue(null);
    idrappelComboBox.setValue(null);
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
        String nom = nommedComboBox.getValue();
    String dosageStr = dosagemed.getText().trim();
    String heure = heureChoiceBox.getValue();
    Integer id = idrappelComboBox.getValue();
    int dosage;

    // Check that all input fields are not empty
    if (nom == null || dosageStr.isEmpty() || heure == null || id == null) {
        // If any of the fields are empty, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Fields");
        alert.setContentText("Please fill in all fields");
        alert.showAndWait();
        return;
    }

    try {
        dosage = Integer.parseInt(dosageStr);
    } catch (NumberFormatException e) {
        // If the input value of dosage cannot be parsed to an integer, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Dosage");
        alert.setContentText("Please enter a valid dosage (an integer)");
        alert.showAndWait();
        return;
    }

    Medicaments m = new Medicaments();
    m.setNommed(nom);
    m.setDosage(dosage);
    m.setHeurePrise(heure);
    m.setRappel_id(id);

    if (mcrud.addMedicaments(m)) {
        // If the medication was added successfully, update the table view and clear the input fields
        loadData();
        nommedComboBox.getSelectionModel().clearSelection();
        dosagemed.clear();
        heureChoiceBox.getSelectionModel().clearSelection();
        idrappelComboBox.getSelectionModel().clearSelection();
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
private void takenmed(ActionEvent event) {
    // Get the selected medication from the table view
    Medicaments selectedMedicament = tableMedicaments.getSelectionModel().getSelectedItem();
    if (selectedMedicament == null) {
        // Show an error message if no medication is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No medication selected");
        alert.setContentText("Please select a medication to mark as taken.");
        alert.showAndWait();
        return;
    }
    
    // Mark the selected medication as taken in the database and decrement nbpl in inventory
    int medicationId = selectedMedicament.getId();
    int dosage = selectedMedicament.getDosage(); // get the dosage from the selected medication
    medicamentsservice.markMedicationAsTaken(medicationId, dosage);
    
    // Show a confirmation message
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Medication taken");
    alert.setHeaderText(null);
    alert.setContentText("The medication has been marked as taken.");
    alert.showAndWait();
    
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
    private void checkInventory(ActionEvent event) {
               try {
        Parent inscrParent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
        Scene inscrscene = new Scene(inscrParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscrscene);
        window.show();
    } catch (IOException e) {
    }
       
        
    }
    
    
   
}