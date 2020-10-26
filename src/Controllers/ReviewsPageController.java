package Controllers;

import DAO.DAOFactory;
import DAO.ReviewDAO;
import DAO.UserDAO;
import Models.Review;
import javafx.animation.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
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
    @FXML public Button logoutButton;

    @FXML public StackPane parentContainer;
    @FXML public Button deleteButton;
    @FXML AnchorPane container;


    private ObservableList<Review> reviewList = FXCollections.observableArrayList();
    public Review selectedReview = null;
    private Boolean acceptedOrRefused = false;



    //funzione richiamata per inizializzare la tabella
    public void setTable() {
    //prendo le reviews
        ReviewDAO reviewDAO = DAOFactory.getReviewDAO();
        List<Object[]>reviews = reviewDAO.getReviewByState("AWAITING");
        fillTable(reviews);
        table.setItems(reviewList);
    }



    @FXML public void showReview(ActionEvent actionEvent) {
        openReview();
    }

    //funzione richiamata alla pressione del bottone "Cerca"
    @FXML public void search(){
        ReviewDAO reviewDAO = DAOFactory.getReviewDAO();
        List<Object[]> reviews = null;

        if (struttura.getText().isEmpty() && luogo.getText().isEmpty()){

            struttura.setPromptText("Inserire nome struttura");
            struttura.setStyle("-fx-prompt-text-fill: #b33950");
            luogo.setPromptText("Inserire nome luogo");
            luogo.setStyle("-fx-prompt-text-fill: #b33950");

        }else if (!struttura.getText().isEmpty() && luogo.getText().isEmpty()){

            reviews = reviewDAO.getReviewByStructure(struttura.getText());

        }else if(struttura.getText().isEmpty() && !luogo.getText().isEmpty()){

            reviews = reviewDAO.getReviewByPlace(luogo.getText());

        }else{
            reviews = reviewDAO.getReviewByStructureAndPlace(struttura.getText(),luogo.getText());
        }
        fillTable(reviews);
        table.setItems(reviewList);
        deleteButton.setDisable(false);
    }

    private void fillTable(List<Object[]> reviews) {

        reviewList.removeAll(reviewList);

        if(reviews != null){
            for (Object[] o : reviews) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(0);
                String userId = nf.format(o[3]);
                String reviewId = nf.format(o[8]);
                if (o[9].toString().equals("AWAITING")) {
                    reviewList.add(new Review(o[0].toString(), o[2].toString(), userId, o[4].toString(), o[5].toString(), o[6].toString(), o[7].toString(), reviewId));
                }
            }
        }
    }

    //funzione che crea la finestra che mostra i dati della recensione scelta
    public void openReview(){
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/showReview.fxml"));
        try {
            Parent rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Recensione");
            ShowReviewController controller = fxmlLoader.getController();
            if(selectedReview!= null){
                controller.setReview(selectedReview);
                controller.setStage(stage);
                controller.setMainPage(this);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        stage.showAndWait();
        //controllo se ho chiuso la finestra premendo uno dei due bottoni

        if(acceptedOrRefused){
            table.getItems().remove(selectedReview);
            table.refresh();
            //aggiorno la variabile
            setAcceptedOrRefused(false);
        }
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

        setTable();
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
                    if(selectedItems.size()>=1){
                        selectedReview = selectedItems.get(0);
                    }
                }
            });
        }

    }
    @FXML
    public void logout(ActionEvent actionEvent) {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.logout();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/login.fxml"));
        try {

            Parent rootLayout = fxmlLoader.load();
            Scene scene = showButton.getScene();
            rootLayout.translateXProperty().set(-1 * scene.getWidth());
            parentContainer.getChildren().add(rootLayout);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(rootLayout.translateXProperty(), 0 , Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
            timeline.getKeyFrames().add(kf);

            FadeTransition ft = new FadeTransition(Duration.seconds(0.4),container);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);

            timeline.setDelay(Duration.seconds(0.4));
            timeline.setOnFinished(event ->{
                parentContainer.getChildren().remove(container);
            });

            ft.play();
            timeline.play();

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void setAcceptedOrRefused(Boolean acceptedOrRefused) {
        this.acceptedOrRefused = acceptedOrRefused;
    }

    public void deleteSearch(ActionEvent actionEvent) {
        setTable();
        deleteButton.setDisable(true);
    }
}