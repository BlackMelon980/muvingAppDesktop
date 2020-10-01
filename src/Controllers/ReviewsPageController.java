package Controllers;

import Models.Review;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReviewsPageController implements Initializable {

    @FXML public TableColumn<Review,String> colonnaLuogo;
    @FXML public TableColumn<Review,String> colonnaStruttura;
    @FXML public TableColumn<Review,String> colonnaAutore;
    @FXML public TableColumn<Review,String> colonnaRecensione;
    @FXML public TableColumn<Review,String> colonnaData;
    @FXML public TableView<Review> table;
    @FXML public Button showButton;

    private LoginController mainPage;

    public Review selectedReview = null;
    //selezione riga
    TableView.TableViewSelectionModel<Review> selectionModel = null;

    public void setMainPage(LoginController mainPage) {
        this.mainPage = mainPage;
        //aggiungo la lista di recensioni alla tabella (la funzione prende appunto una ObservableList)
        table.setItems(mainPage.getReviewList());

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

    //funzione che crea la finestra che mostra i dati della recensione scelta
    public void openReview(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/showReview.fxml"));
        try {
            Parent rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            scene.getStylesheets().add("layout.css");
            stage.setTitle("Recensione");
            ShowReviewController controller = fxmlLoader.getController();
            controller.setReview(selectedReview);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        stage.showAndWait();

    }
}