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

import java.io.IOException;

public class LoginController {

    @FXML public Button loginButton;
    public TextField idAdmin;
    public TextField passwordAdmin;
    private Stage mainStage;

    /*
      le classi di visualizzazione hanno bisogno di essere informate su eventuali modifiche apportate
      alla lista di recensioni. Per questo si usa una ObservableList.
    */
    private ObservableList<Review> reviewList = FXCollections.observableArrayList();


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
            //controllo se le credenziali sono giuste e in caso positivo cambio la scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/reviewsPage.fxml"));

            reviewList.add(new Review("Napoli","400 gradi Di Ciro & Salvio Rapuano","Peppe98","Ottima pizza ai quartieri spagnoli","Spinto dalle ottime recensioni decidiamo di andare in questa piccola pizzeria dei quartieri spagnoli e non c’è stata scelta migliore ottima pizza dal impasto super leggero e personale molto attento...consiglio di andarci...grazie mille a Salvio per la sua ospitalità...ci rivedremo presto...","10/06/2020"));
            reviewList.add(new Review("Torre Del Greco","MM Lounge Restauran","Giovix2","Cena sublime","Cortesia, eleganza, ottima qualità del cibo. Sono questi i principali elementi che contraddistinguono questo ristorante. Ogni piatto ben presentato, perfettamente curato, un piacere per occhi e palato, dall'antipasto al dolce, con un ampia possibilità di scelta dei vini. Servizio perfetto, cena sublime.\n" +
                    "Un ringraziamento ai camerieri in servizio ieri sera per il servizio perfetto!(20 settembre 2020)\n" +
                    "Complimenti allo Chef!","23/08/2020"));
            reviewList.add(new Review("Torre Del Greco","MM Lounge Restauran","Giovix2","Cena sublime","Cortesia, eleganza, ottima qualità del cibo. Sono questi i principali elementi che contraddistinguono questo ristorante. Ogni piatto ben presentato, perfettamente curato, un piacere per occhi e palato, dall'antipasto al dolce, con un ampia possibilità di scelta dei vini. Servizio perfetto, cena sublime.\n" +
                    "Un ringraziamento ai camerieri in servizio ieri sera per il servizio perfetto!(20 settembre 2020)\n" +
                    "Complimenti allo Chef!","23/08/2020"));


            try {
                Parent rootLayout = fxmlLoader.load();
                Scene scene = new Scene(rootLayout);
                scene.getStylesheets().add("layout.css");
                mainStage.setScene(scene);

                ReviewsPageController reviewsPageController = fxmlLoader.getController();
                reviewsPageController.setMainPage(this);

                mainStage.setTitle("Applicazione");

            }catch (IOException e){
                throw new RuntimeException(e);
            }
            mainStage.show();
        }

    }

    public ObservableList<Review> getReviewList(){
        return reviewList;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
