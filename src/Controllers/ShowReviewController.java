package Controllers;

import Dao.DAOFactory;
import Dao.ReviewDAO;
import Models.Review;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class ShowReviewController {
    @FXML public Label nomeLuogo;
    @FXML public Label nomeStruttura;
    @FXML public Label dataRecensione;
    @FXML public Label titoloRecensione;
    @FXML public Label commento;
    @FXML public Label nomeAutore;
    @FXML public Button refuseButton;
    @FXML public Button acceptButton;
    private String reviewId;

    private ReviewDAO reviewDAO;

    public ShowReviewController(){
        reviewDAO = DAOFactory.getReviewDAO();
    }

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
        nomeAutore.setText(String.valueOf(review.getAutore()));
        commento.setText(review.getRecensione());
    }

    public void accettaRecensione(){

        String body = new StringBuilder()
                .append("{")
                .append("\"state\":")
                .append("{")
                .append("\"name\":")
                .append("\"ACCEPTED\"")
                .append("}")
                .append("}").toString();

        HttpResponse<String> response = reviewDAO.updateReviewState(Long.parseLong(review.getReviewId()),body);

    }

    public void rifiutaRecensione(){
        String body = new StringBuilder()
                .append("{")
                .append("\"state\":")
                .append("{")
                .append("\"name\":")
                .append("\"REFUSED\"")
                .append("}")
                .append("}").toString();
        HttpResponse<String> response = reviewDAO.updateReviewState(Long.parseLong(review.getReviewId()),body);
        System.out.println(review.getReviewId());
    }
}
