import Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class muvingApplication extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/login.fxml"));
        try {
            Parent rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            scene.getStylesheets().add("/Layout/layout.css");
            stage.setTitle("Login");
            LoginController controller = fxmlLoader.getController();
            controller.setMainStage(stage);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
