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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class FrontController implements Initializable {

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
    private TableView<Evenements> tableEvenements;
    @FXML
    private TableColumn<?, ?> colTitre;
    @FXML
    private TableColumn<?, ?> colPhoto;
    private TextField tfimage;
    private TextField tfphoto;
    private Button btnchoisir;
    @FXML
    private Button btninscription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
     
        show();
     
    }
    MyConnection cnx = null;
    Statement st = null;
    EvenementsCRUD rcd = new EvenementsCRUD();

    /**
     * Initializes the controller class.
     */
   
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


    
}
