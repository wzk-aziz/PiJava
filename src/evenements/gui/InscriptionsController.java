/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.gui;

import evenements.entities.Inscriptions;
import evenements.services.InscriptionsCRUD;
import evenements.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class InscriptionsController implements Initializable {

  
    @FXML
    private Label message;
    
    private InscriptionsCRUD icrud = new InscriptionsCRUD();

    @FXML
    private Button btninscription;
    @FXML
    private TextArea tfdescriptionev;
    @FXML
    private DatePicker tfdateev;
    @FXML
    private TextField tfnomev;
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfidev;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      // Initialiser la date par défaut du DatePicker à aujourd'hui
        tfdateev.setValue(LocalDate.now());
    }

   

    
    @FXML
    private void inscription(ActionEvent event) {
        
        try {
        // Récupérer l'id_utilisateur depuis la zone de texte tfid
        int id_u = Integer.parseInt(tfid.getText());

        // Vérifier si l'utilisateur existe dans la base de données
        String selectQuery = "SELECT COUNT(*) FROM utilisateur WHERE id = ?";
        PreparedStatement selectStmt = MyConnection.getInstance().getCnx().prepareStatement(selectQuery);
        selectStmt.setInt(1, id_u);
        ResultSet selectResult = selectStmt.executeQuery();

        selectResult.next();
        int utilisateurCount = selectResult.getInt(1);

        if (utilisateurCount == 0) {
            // Afficher une alerte avec le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("L'utilisateur avec l'id " + id_u + " n'existe pas, veuillez vérifier l'id entré");
            alert.showAndWait();
            return;
        }

        // Récupérer l'id_evenement depuis la zone de texte tfnomev
        int evenementId = Integer.parseInt(tfidev.getText());
        String date = tfdateev.getValue().toString();
        String descInscri = tfdescriptionev.getText();

        // Vérifier si la description contient au moins 7 caractères
        if (descInscri.length() < 7) {
            // Afficher une alerte avec le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La description doit contenir au moins 7 caractères");
            alert.showAndWait();
            
            return;
        }

        // Créer un objet Inscriptions à partir des données saisies
        Inscriptions inscription = new Inscriptions(id_u, evenementId, date, descInscri);

        // Appeler la méthode ajouterInscriptions de l'interface ICrud pour ajouter l'inscription à la base de données
        icrud.ajouterInscriptions(inscription);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Inscription ajoutée avec succès");
        alert.showAndWait();

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    } catch (NumberFormatException ex) {
        // Afficher une alerte avec le message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Les champs doivent contenir des valeurs numériques");
        alert.showAndWait();
    }   
    }
    
    private String nomTableSelectionnee;
   public void setNomTable(String nomTable) {
    this.nomTableSelectionnee = nomTable;
    tfnomev.setText(nomTable);
}
   public void setIdTable(String idTable) {
    this.tfidev.setText(idTable);
}

    @FXML
    private void retour(ActionEvent event) {
        
        
          try {
        Parent inscriptionParent = FXMLLoader.load(getClass().getResource("front.fxml"));
        Scene inscriptionScene = new Scene(inscriptionParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscriptionScene);
        window.show();
    } catch (IOException e) {
    }
    }

}
