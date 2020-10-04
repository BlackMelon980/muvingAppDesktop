package Controllers;

import Dao.DAOFactory;
import Dao.ReviewDAO;
import Models.Review;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ReviewsPageController implements Initializable {

    @FXML public TableColumn<Review,String> colonnaLuogo;
    @FXML public TableColumn<Review,String> colonnaStruttura;
    @FXML public TableColumn<Review,String> colonnaAutore;
    @FXML public TableColumn<Review,String> colonnaRecensione;
    @FXML public TableColumn<Review,String> colonnaData;
    @FXML public TableView<Review> table;
    @FXML public TextField struttura;
    @FXML public TextField luogo;
    @FXML public Button showButton;
    @FXML public Button searchButton;

    private LoginController mainPage;
    private ObservableList<Review> reviewList = FXCollections.observableArrayList();
    public Review selectedReview = null;
    //selezione riga
    TableView.TableViewSelectionModel<Review> selectionModel = null;

    public void setMainPage(LoginController mainPage) {
        this.mainPage = mainPage;
    //prendo le reviews
        ReviewDAO reviewDAO = DAOFactory.getReviewDAO();
        List<Object[]>reviews = reviewDAO.getReviewByState("AWAITING");

        if(reviews != null){
            //reviewList.removeAll(reviewList);
            for(int i=0; i<reviews.size();i++) {
                Object[] o = reviews.get(i);
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(0);
                String userId = nf.format(o[2]);
                String reviewId = nf.format(o[7]);
                reviewList.add(new Review(o[0].toString(),o[1].toString(),userId,o[3].toString(),o[4].toString(),o[5].toString(),o[6].toString(), reviewId));
            }
        }
        table.setItems(reviewList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //qui verrá inizializzata la tabella con tutte le recensioni
        showButton.setVisible(false);
        colonnaLuogo.setCellValueFactory(cellData -> cellData.getValue().luogoProperty());
        colonnaStruttura.setCellValueFactory(cellData -> cellData.getValue().strutturaProperty());
        colonnaAutore.setCellValueFactory(cellData -> cellData.getValue().autoreProperty());
        colonnaRecensione.setCellValueFactory(cellData -> cellData.getValue().recensioneProperty());
        colonnaData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());

        //selezione riga
        TableView.TableViewSelectionModel<Review> selectionModel = table.getSelectionModel();
        if(selectionModel != null){
            //permetto di selezionare 1 riga alla volta
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            ObservableList<Review>selectedItems = selectionModel.getSelectedItems();

            selectedItems.addListener(new ListChangeListener<Review>() {
                @Override
                public void onChanged(Change<? extends Review> change) {
                    showButton.setVisible(true);
                    selectedReview = selectedItems.get(0);
                }
            });
        }

    }

    @FXML public void showReview(ActionEvent actionEvent) {
        openReview();
    }

    @FXML public void search(){
        ReviewDAO reviewDAO = DAOFactory.getReviewDAO();
        if (struttura.getText().isEmpty() && luogo.getText().isEmpty()){
            struttura.setPromptText("Inserire nome struttura");
            luogo.setPromptText("Inserire nome luogo");
        }else if (!struttura.getText().isEmpty() && luogo.getText().isEmpty()){
            List<Object[]> reviews = reviewDAO.getReviewByStructure(struttura.getText());
            if(reviews != null){
                reviewList.removeAll(reviewList);
                for(int i=0; i<reviews.size();i++) {
                    Object[] o = reviews.get(i);
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(0);
                    String userId = nf.format(o[2]);
                    String reviewId = nf.format(o[7]);
                    reviewList.add(new Review(o[0].toString(),o[1].toString(),userId,o[3].toString(),o[4].toString(),o[5].toString(),o[6].toString(), reviewId));
                }
            }

        }else if(struttura.getText().isEmpty() && !luogo.getText().isEmpty()){
            List<Object[]> reviews = reviewDAO.getReviewByPlace(luogo.getText());
            if(reviews != null){
                reviewList.removeAll(reviewList);
                for(int i=0; i<reviews.size();i++) {
                    Object[] o = reviews.get(i);
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(0);
                    String userId = nf.format(o[2]);
                    String reviewId = nf.format(o[7]);
                    reviewList.add(new Review(o[0].toString(),o[1].toString(),userId,o[3].toString(),o[4].toString(),o[5].toString(),o[6].toString(), reviewId));
                }
            }
        }else{
            List<Object[]> reviews = reviewDAO.getReviewByStructureAndPlace(struttura.getText(),luogo.getText());
            if(reviews != null){
                reviewList.removeAll(reviewList);
                for(int i=0; i<reviews.size();i++) {
                    Object[] o = reviews.get(i);
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(0);
                    String userId = nf.format(o[2]);
                    String reviewId = nf.format(o[7]);
                    reviewList.add(new Review(o[0].toString(),o[1].toString(),userId,o[3].toString(),o[4].toString(),o[5].toString(),o[6].toString(), reviewId));
                }
            }
        }
        table.setItems(reviewList);
    }

    //funzione che crea la finestra che mostra i dati della recensione scelta
    public void openReview(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/showReview.fxml"));
        try {
            Parent rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Recensione");
            ShowReviewController controller = fxmlLoader.getController();
            controller.setReview(selectedReview);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        stage.showAndWait();
    }
}