package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /**
         * Split the loading into a loader and a load function - because that way you
         * get access to the loader and can use it to load the controller.
         * This controller is needed in order to set scenes
         */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));

        Parent root = fxmlLoader.load();
        Scene settingsScene = primaryStage.getScene();
        // The code above is loading the root scene, in this case the settings menu that I have done.



        /**
         * Default code for initialising the first scene (not the root variable should be used
         * for the first scene that is loaded, hence in my local file I have used it here
         * this can be changed for a name identifying what scene it refers to
         */


        primaryStage.setTitle("Gardener Weather App");

        primaryStage.setScene(new Scene(root, 450, 800));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}
