package Ben_frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LanguagesController extends ControllerMaster{


    @FXML
    Button buttonBack;

    public void handleBackButton() throws IOException{
        // Code for switching scenes again

        switchScenes("Settings.fxml", buttonBack);

    }

    /*
    private void switchToSettingsScene() throws IOException{
        FXMLLoader SettingsLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent SettingsPane = SettingsLoader.load();
        Scene SettingsScene = new Scene(SettingsPane, 450, 800);


        //LanguagesController languagesController = (LanguagesController) this;
        //languagesController.setSettingsScene(SettingsScene);

        Stage primaryStage = (Stage)buttonBack.getScene().getWindow();
        primaryStage.setScene(SettingsScene);
    }
    */

}
