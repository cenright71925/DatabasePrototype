import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     * public void start(Stage primaryStage) - Starts the JavaFX application
     * @param primaryStage the JavaFX primary stage
     * @throws IOException input output exception thrown by UI
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        primaryStage.setTitle("Prototype Database Application");
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * public static void main(String[] args) - program starts here
     * @param args passes down arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
