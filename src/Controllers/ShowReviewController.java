package Controllers;

import Dao.DAOFactory;
import Dao.ReviewDAO;
import Models.Review;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    @FXML public Label votoRecensione;

    private String reviewId;

    private ReviewDAO reviewDAO;

    public ShowReviewController(){
        reviewDAO = DAOFactory.getReviewDAO();
    }

    private Review review;
    private Stage stage;
    private ReviewsPageController mainPage;

    public void setReview(Review review) {
        this.review = review;
        addValues();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainPage(ReviewsPageController mainPage) {
        this.mainPage = mainPage;
    }

    public void addValues(){
        nomeLuogo.setText(review.getLuogo());
        nomeStruttura.setText(review.getStruttura());
        dataRecensione.setText(review.getData());
        titoloRecensione.setText(review.getTitolo());
        nomeAutore.setText(String.valueOf(review.getAutore()));
        commento.setText(review.getRecensione());
    }

    @FXML public void acceptOrRefuseReview(ActionEvent actionEvent) {
        String value = ((Button)actionEvent.getSource()).getText();
        String choice;
        if(value.equals("Accetta")){
            choice = "\"ACCEPTED\"";
        }else{
            choice = "\"REFUSED\"";
        }

        String body = new StringBuilder()
                .append("{")
                .append("\"state\":")
                .append("{")
                .append("\"name\":")
                .append(choice)
                .append("}")
                .append("}").toString();

        HttpResponse<String> response = reviewDAO.updateReviewState(Long.parseLong(review.getReviewId()),body);
        mainPage.setAcceptedOrRefused(true);
        stage.close();
    }
}
