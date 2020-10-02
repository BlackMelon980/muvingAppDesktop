package Controllers;

import Models.Review;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginController extends Component {

    @FXML public Button loginButton;
    public TextField idAdmin;
    public TextField passwordAdmin;
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
                    mainStage.setScene(scene);

                    ReviewsPageController reviewsPageController = fxmlLoader.getController();
                    reviewsPageController.setMainPage(this);

                    mainStage.setTitle("Applicazione");

                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                mainStage.show();
            }else if (result == 0){
                JOptionPane.showMessageDialog(this,"L'utente non possiede i permessi per accedere!","Accesso negato",JOptionPane.ERROR_MESSAGE);
                isLogged = false;
            }else{
                JOptionPane.showMessageDialog(this,"Le credenziali inserite sono errate. Riprovare","Accesso negato",JOptionPane.ERROR_MESSAGE);
                isLogged = false;
            }
        }

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
