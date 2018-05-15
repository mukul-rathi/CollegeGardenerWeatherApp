package practice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        primaryStage.setTitle("Gardening Weather App");
        primaryStage.setScene(new Scene(root, 450, 800));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
