package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.io.IOException;

public class AlertSettingsGridController extends ControllerMaster{

    @FXML
    private RadioButton rbA1, rbA2, rbA3, rbA4, rbA5, rbp1, rbp2, rbp3, rbp4, rbp5;

    @FXML
    private Button buttonBack;

    private SceneResource sceneResource;


    @Override
    protected void init(SceneResource resource) {
        // will need alerts info from persistent storage.
        // for now, the back button sets up a default choice first,
        //(everything alterable, nothing priority)

        sceneResource = resource;
    }

    /**
     * Handlers for the different radio buttons, this will need
     * to include how they affect the wider program
     */

    /**
     * Weather warnings for the different radio sets
     * 1) Heavy Rain, 2), 3), 4), 5)
     */

    @FXML
    private void rba1Handler(){

    }

    @FXML
    private void rba2Handler(){

    }

    @FXML
    private void rba3Handler(){

    }

    @FXML
    private void rba4Handler(){

    }

    @FXML
    private void rba5Handler(){

    }

    @FXML
    private void rbp1Handler(){

    }

    @FXML
    private void rbp2Handler(){

    }

    @FXML
    private void rbp3Handler(){

    }

    @FXML
    private void rbp4Handler(){

    }

    @FXML
    private void rbp5Handler(){

    }

    @FXML
    private void handleButtonBack() throws IOException{

        if(sceneResource == null){
            sceneResource = new SceneResource();
        }



        switchScenesPassData("MainPage.fxml", buttonBack, sceneResource);
    }

}
