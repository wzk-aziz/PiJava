/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evenements.gui;

import evenements.entities.Evenements;
import evenements.services.EvenementsCRUD;
import evenements.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EvenementsController implements Initializable {

    @FXML
    private DatePicker tfdate;
   

    private EvenementsCRUD ecrud = new EvenementsCRUD();
   
    @FXML
    private TableColumn<?, ?> colId;
   
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colDescription;
    public ObservableList<Evenements> data = FXCollections.observableArrayList();
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnenvoyer;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnreclamation;

    @FXML
    private Button btnretour;
    @FXML
    private ImageView image1;
    @FXML
    private TableView<Evenements> tableEvenements;
    @FXML
    private TableColumn<?, ?> colTitre;
    @FXML
    private TableColumn<?, ?> colPhoto;
    private TextField tfimage;
    @FXML
    private TextField tftitre;
    @FXML
    private TextArea tfdescription;
    @FXML
    private TextField tfphoto;
    @FXML
    private Button btnchoisir;
    @FXML
    private Button btninscription;
    @FXML
    private Button btnInscri;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  btnchoisir.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image");
            File fileImageV = fileChooser.showOpenDialog(btnchoisir.getScene().getWindow());
            if (fileImageV != null) {
                tfphoto.setText(fileImageV.getPath());
            }
        });
        // Initialiser la date par défaut du DatePicker à aujourd'hui
        tfdate.setValue(LocalDate.now());
        show();
        
     
    }
    MyConnection cnx = null;
    Statement st = null;
    EvenementsCRUD rcd = new EvenementsCRUD();

    

    public void show() {
        try {
            String requete = "SELECT * FROM evenements";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Evenements r = new Evenements(rs.getInt("id"),rs.getString("titre"), rs.getString("description"), rs.getString("date"), rs.getString("photo"));
                data.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photo"));
        tableEvenements.setItems(data);
    }

   
    /**
     * Modifie les informations de la réclamation sélectionnée dans la table
     * view. Récupère les nouvelles valeurs des champs type, date et
     * description, puis exécute une requête de mise à jour dans la base de
     * données. Affiche un message de confirmation si la mise à jour a réussi.
     * Rafraîchit la table view pour afficher les nouvelles données.
     */
   
  

    @FXML
    private void gererEvenements(ActionEvent event) {
        try {
            Parent evenetparent = FXMLLoader.load(getClass().getResource("Evenements.fxml"));
            Scene eventscene = new Scene(evenetparent);
            Stage window = (Stage) (((Button) event.getSource()).getScene().getWindow());
            window.setScene(eventscene);
            window.show();
        } catch (IOException e) {
        }
    }

 

    
    

    @FXML
    private void retour(ActionEvent event) {
           try {
        Parent reclamationsParent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene reclamationsScene = new Scene(reclamationsParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(reclamationsScene);
        window.show();
    } catch (IOException e) {
    }
    }

   @FXML
private void addevn(ActionEvent event) {
     // Vérifier que tous les champs sont remplis
        if (tftitre.getText() == null || tfdate.getValue() == null || tfdescription.getText().isEmpty() || tfphoto.getText().isEmpty()) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Aucune de ces informations ne doit être vide. Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;

        }

        // Vérifier que la longueur de la description est supérieure à 5 caractères
        if (tfdescription.getText().length() < 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La description doit contenir au moins 6 caractères");
            alert.showAndWait();
            return;
        }
        // Vérifier que la date est comprise entre il y a deux jours et aujourd'hui
        LocalDate selectedDate = tfdate.getValue();
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        if (selectedDate.isBefore(twoDaysAgo) || selectedDate.isAfter(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La date doit être comprise entre il y a deux jours et aujourd'hui");
            alert.showAndWait();
            return;
        }
        // Récupérer les valeurs des champs
        String titre = tftitre.getText();
        String description = tfdescription.getText();

        String date = tfdate.getValue().toString();
        String photo = tfphoto.getText();

        rcd.ajouterEvenements(new Evenements(titre,description,date,photo));
        // Rafraîchir la liste de données
        data.clear();
        show();
        tableEvenements.refresh();
      
    }






    @FXML
    private void editeven(ActionEvent event) {
         Evenements evenements = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenements == null) {
            // Aucune réclamation sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de modification");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un évenement à modifier.");
            alert.showAndWait();
        } else {
            // Vérifier que tous les champs sont remplis
            if (tftitre.getText().isEmpty() || tfdescription.getText().isEmpty() || tfdate.getValue()== null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de modification");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs avant de modifier un évenement.");
                alert.showAndWait();
                return;
            }

            // Vérifier que la longueur de la description est supérieure à 5 caractères
            if (tfdescription.getText().length() < 6) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("La description doit contenir au moins 6 caractères");
                alert.showAndWait();
                return;
            }

            // Récupérer les nouvelles valeurs
            String titre = tftitre.getText();
        String description = tfdescription.getText();

        String date = tfdate.getValue().toString();
        String photo = tfphoto.getText();


            // Mettre à jour la réclamation dans la base de données
            rcd.modifierEvenements(new Evenements(evenements.getId(), titre,description,date,photo));

            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("votre évenement a été modifiée avec succès.");
            alert.showAndWait();

            // Rafraîchir la table view pour afficher les nouvelles données
            data.clear();
            show();
        }
    }

    @FXML
    private void deleteeven(ActionEvent event) {
        
         // Vérifier si une réclamation est sélectionnée
        if (tableEvenements.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez sélectionner un evenements à supprimer");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet évenement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de la réclamation sélectionnée dans la vue de la table
            int id = tableEvenements.getSelectionModel().getSelectedItem().getId();

            // Supprimer la réclamation de la base de données
            rcd.supprimerEvenements(id);
// Rafraîchir la liste de données
            data.clear();
            show();
            // Rafraîchir la vue de la table
            tableEvenements.refresh();
        }
    }


    @FXML
    private void afficherEvenementsSelected(MouseEvent event) {
         Evenements evenements = tableEvenements.getSelectionModel().getSelectedItem();
        if (evenements != null) {
            tftitre.setText(evenements.getTitre());
            tfdescription.setText(evenements.getDescription());
            tfdate.setValue(LocalDate.parse(evenements.getDate()));
            tfphoto.setText(evenements.getPhoto());


        } else {
            tftitre.setText("");
            tfdescription.setText("");

            tfdate.setValue(null);
            tfphoto.setText("");

        }
    }

    @FXML
    private void inscription(ActionEvent event) {
Evenements selectedEvent = tableEvenements.getSelectionModel().getSelectedItem();
    if (selectedEvent != null) {
        // Récupérer le nom et l'ID de l'événement sélectionné
        String nomTable = selectedEvent.getTitre();
        int idTable = selectedEvent.getId();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inscriptions.fxml"));
            Parent messageParent = loader.load();
            InscriptionsController messageController = loader.getController();
            
            // Passer le nom et l'ID de la table sélectionnée au contrôleur InscriptionsController
            messageController.setNomTable(nomTable);
            messageController.setIdTable(String.valueOf(idTable));
            
            Scene messageScene = new Scene(messageParent);
            Stage window = (Stage) (((Button) event.getSource()).getScene().getWindow());
            window.setScene(messageScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    @FXML
    private void gererinscription(ActionEvent event) {
          try {
        Parent inscrParent = FXMLLoader.load(getClass().getResource("inscriptionsback.fxml"));
        Scene inscrscene = new Scene(inscrParent);
        Stage window = (Stage)(((Button)event.getSource()).getScene().getWindow());
        window.setScene(inscrscene);
        window.show();
    } catch (IOException e) {
    }
    }

   



}