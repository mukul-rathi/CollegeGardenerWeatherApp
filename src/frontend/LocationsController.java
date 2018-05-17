package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LocationsController extends ControllerMaster{

    /**
     * These are the three objects that require editing
     * the button should set the location to switch back to settings
     * and also update the current location being used.
     *
     */

    @FXML
    Button buttonSetLocation;

    @FXML
    TextArea textAreaCurrentLocation;

    @FXML
    TextField textFieldLocation;


    @FXML
    private void handleButtonSetLocation() throws IOException{

        /**
         * Here I need to set the location as given
         */

        switchScenes("Settings.fxml", buttonSetLocation);

    }

}
