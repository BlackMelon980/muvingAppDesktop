package Controllers;

import Auth.Token;
import DAO.ReviewDAO;
import DAO.DAOFactory;
import Models.Review;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


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
    @FXML public AnchorPane windowContainer;

    private String reviewId;

    private ReviewDAO reviewDAO;

    private Token token;

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
        votoRecensione.setText(review.getVoto());
        reviewId = review.getReviewId();
    }

    @FXML public void acceptOrRefuseReview(ActionEvent actionEvent) {
        String value = ((Button)actionEvent.getSource()).getText();
        System.out.println(((Button)actionEvent.getSource()).getText());
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
        if (response.body().contains("ACCEPTED")){
            //inserire review id
            System.out.println("VERO");
            String url = "http://ec2-18-130-144-5.eu-west-2.compute.amazonaws.com/?review_id="+reviewId;
            HttpClient client = HttpClient.newHttpClient();
            System.out.println(url);
            HttpRequest requestNotification = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            try {
                HttpResponse<String> response2 = client.send(requestNotification, HttpResponse.BodyHandlers.ofString());
            }catch (IOException | NumberFormatException | InterruptedException e){
                e.printStackTrace();
            }
        }
        mainPage.setAcceptedOrRefused(true);
        stage.close();
    }
}
