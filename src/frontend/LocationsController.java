package frontend;

import frontend.storage.ResourcesStorage;
import frontend.storage.StorageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    Text textAreaCurrentLocation;

    @FXML
    TextField textFieldLocation;


    @Override
    protected void init(SceneResource resource) {

        textAreaCurrentLocation.setText("Current Location: " + resource.getLocation());


    }

    @FXML
    private void handleButtonSetLocation() throws IOException{

        /**
         * Here I need to set the location as given
         */

        writeLocationToStorage();

        switchScenes("Settings.fxml", buttonSetLocation);

    }

    private void writeLocationToStorage(){

        //THis is the default way for writing to storage:
        // Make a handler to load the storage
        // Then load the storage
        // Set the parameter that you want
        // Write to storage, passing the storage object to the handler

        // Getting handler
        StorageHandler handler = new StorageHandler();
        ResourcesStorage storage = handler.returnStorage();

        // Validating user input
        if(textFieldLocation.getText()!="") {
            storage.setUserLocation(textFieldLocation.getText());
        } else {
            storage.setUserLocation("Cambridge");
        }

        // Writing to storage
        storage.setUserLocation(textFieldLocation.getText());
        handler.writeToStorage(storage);

    }

}
