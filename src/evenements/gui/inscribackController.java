/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.gui;

import evenements.entities.Evenements;
import evenements.entities.Inscriptions;
import evenements.services.EvenementsCRUD;
import evenements.services.InscriptionsCRUD;
import evenements.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class inscribackController implements Initializable {

    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colTitre;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colPhoto;
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnAvis;
    @FXML
    private ImageView image1;
    @FXML
    private Button btnretour;
    @FXML
    private TableView<Inscriptions> tabinscri;
    public ObservableList<Inscriptions> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */        InscriptionsCRUD rcd = new InscriptionsCRUD();
    @FXML
    private Button btnevent;

    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        show();
    }    
public void show() {
        try {
            String requete = "SELECT * FROM inscriptions";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Inscriptions r = new Inscriptions(rs.getInt("id"),rs.getInt("utilisateur_id"), rs.getInt("evenements_id"), rs.getString("dateinscrievent"), rs.getString("descins"));
                data.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("utilisateur_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("evenements_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateinscrievent"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("descins"));
        tabinscri.setItems(data);
    }


    @FXML
    private void afficherEvenementsSelected(MouseEvent event) {
    }


    @FXML
    private void deleteeven(ActionEvent event) {
                if (tabinscri.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez sélectionner un evenements à supprimer");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet évenements ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de la réclamation sélectionnée dans la vue de la table
            int id = tabinscri.getSelectionModel().getSelectedItem().getId();

            // Supprimer la réclamation de la base de données
            rcd.supprimerInscriptions(id);
// Rafraîchir la liste de données
            data.clear();
            show();
            // Rafraîchir la vue de la table
            tabinscri.refresh();
        }
    }


    @FXML
    private void gererEvent(ActionEvent event) {
          try {
        Parent evenetparent = FXMLLoader.load(getClass().getResource("Evenements.fxml"));
        Scene eventscene = new Scene(evenetparent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(eventscene);
        window.show();
    } catch (IOException e) {
    }
    }

   

    @FXML
    private void retour(ActionEvent event) {
             try {
        Parent evenetparent = FXMLLoader.load(getClass().getResource("Evenements.fxml"));
        Scene eventscene = new Scene(evenetparent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(eventscene);
        window.show();
    } catch (IOException e) {
    }
    }

    @FXML
    private void gererInscri(ActionEvent event) {
             try {
        Parent evenetparent = FXMLLoader.load(getClass().getResource("Inscriptionsback.fxml"));
        Scene eventscene = new Scene(evenetparent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(eventscene);
        window.show();
    } catch (IOException e) {
    }
    }
    
}
