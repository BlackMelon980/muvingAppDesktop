package Controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginController extends Component {

    @FXML public Button loginButton;
    public TextField idAdmin;
    public TextField passwordAdmin;
    public AnchorPane anchorPaneLogin;
    public BorderPane borderPaneRoot;
    private Stage mainStage;

    LoginDialogController loginDialogController;
    boolean isLogged;


    /*
      le classi di visualizzazione hanno bisogno di essere informate su eventuali modifiche apportate
      alla lista di recensioni. Per questo si usa una ObservableList.
    */

    //funzione richiamata alla pressione del bottone
    @FXML public void login() {
        if(idAdmin.getText().isEmpty() || passwordAdmin.getText().isEmpty()){
            if(idAdmin.getText().isEmpty()){
                idAdmin.setPromptText("Inserire codice");
            }
            if(passwordAdmin.getText().isEmpty()){
                passwordAdmin.setPromptText("Inserire password");
            }
        }
        else{
            loginDialogController = new LoginDialogController();
            String username = idAdmin.getText();
            String password = passwordAdmin.getText();
            int result = loginDialogController.loginAdmin(username,password);
            if (result == 1){
                isLogged = true;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/reviewsPage.fxml"));
                try {
                    Parent rootLayout = fxmlLoader.load();
                    Scene scene = new Scene(rootLayout);
                    scene.getStylesheets().add("/Layout/layout.css");
                    Scene rootScene = anchorPaneLogin.getScene();
                    rootLayout.translateYProperty().set(rootScene.getHeight());
                    mainStage.setScene(scene);

                    mainStage.setTitle("Applicazione");

                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                mainStage.show();
            }else if (result == 0){
                showAlert("Accesso negato","L'utente non possiede i permessi per accedere!");
                isLogged = false;
            }else{
                showAlert("Accesso negato","Le credenziali inserite sono errate. Riprovare");
                isLogged = false;
            }
        }

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("../Layout/layout.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.setResizable(false);
        alert.showAndWait();
    }
}
