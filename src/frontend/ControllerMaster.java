package frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMaster {

    protected void switchScenes(String location, Button buttonBack) throws IOException {
        FXMLLoader SettingsLoader = new FXMLLoader(getClass().getResource(location));
        Parent SettingsPane = SettingsLoader.load();
        Scene SettingsScene = new Scene(SettingsPane, 450, 800);


        //LanguagesController languagesController = (LanguagesController) this;
        //languagesController.setSettingsScene(SettingsScene);

        Stage primaryStage = (Stage)buttonBack.getScene().getWindow();
        primaryStage.setScene(SettingsScene);
    }

}
