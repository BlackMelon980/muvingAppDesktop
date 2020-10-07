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

    @FXML public StackPane parentContainer;
    @FXML public AnchorPane container;
    @FXML public Button loginButton;
    public TextField idAdmin;
    public TextField passwordAdmin;
    private Stage mainStage;

    LoginDialogController loginDialogController;
    boolean isLogged;

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
                    //cambio scena con animazione
                    Parent rootLayout = fxmlLoader.load();
                    Scene scene = loginButton.getScene();
                    rootLayout.translateXProperty().set(scene.getWidth());
                    parentContainer.getChildren().add(rootLayout);

                    Timeline timeline = new Timeline();
                    KeyValue kv = new KeyValue(rootLayout.translateXProperty(), 0 , Interpolator.EASE_IN);
                    KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
                    timeline.getKeyFrames().add(kf);
                    timeline.setOnFinished(event ->{
                        parentContainer.getChildren().remove(container);
                    });
                    timeline.play();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
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
        alert.setX( (container.getWidth()/2) - 170);
        alert.setY( (container.getHeight()/2) - 30 );

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("../Layout/layout.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.setResizable(false);
        alert.showAndWait();
    }
}
