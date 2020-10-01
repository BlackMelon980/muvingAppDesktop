package Controllers;

import Models.Review;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowReviewController {
    @FXML public Label nomeLuogo;
    @FXML public Label nomeStruttura;
    @FXML public Label dataRecensione;
    @FXML public Label titoloRecensione;
    @FXML public Label commento;
    @FXML public Label nomeAutore;

    private Review review;

    public void setReview(Review review) {
        this.review = review;
        addValues();
    }

    public void addValues(){
        nomeLuogo.setText(review.getLuogo());
        nomeStruttura.setText(review.getStruttura());
        dataRecensione.setText(review.getData());
        titoloRecensione.setText(review.getTitolo());
        nomeAutore.setText(review.getAutore());
        commento.setText(review.getRecensione());
    }
}
