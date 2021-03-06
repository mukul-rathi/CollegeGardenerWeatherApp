package frontend;

import backend.WeatherType;
import frontend.storage.ResourcesStorage;
import frontend.storage.StorageHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AlertSettingsGridController extends ControllerMaster implements Initializable{

    @FXML
    private RadioButton rbA1, rbA2, rbA3, rbA4, rbA5, rbp1, rbp2, rbp3, rbp4, rbp5;

    @FXML
    private Button buttonBack;


    @Override
    protected void init(SceneResource resource) {
        // will need alerts info from persistent storage.
        // for now, the back button sets up a default choice first,
        //(everything alterable, nothing priority)
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

        //get the state of all the alerts and save it to persistent data

        // Getting all of the data from the radio buttons
        HashMap<WeatherType, Boolean> alertableSelection = new HashMap<>();
        HashMap<WeatherType, Boolean> prioritySelection = new HashMap<>();

        final RadioButton[] allButtonsAlertable = new RadioButton[]{rbA1, rbA2, rbA3, rbA4, rbA5};
        final RadioButton[] allButtonsPriority = new RadioButton[]{rbp1, rbp2, rbp3, rbp4, rbp5};
        final WeatherType[] alertTypes = new WeatherType[]{WeatherType.RAIN, WeatherType.SLEET, WeatherType.SNOW,
                WeatherType.WIND, WeatherType.FOG};

        for(int i = 0; i < allButtonsAlertable.length; i++){
            alertableSelection.put(alertTypes[i], allButtonsAlertable[i].isSelected());
            prioritySelection.put(alertTypes[i], allButtonsPriority[i].isSelected());
        }

        AlertsContext alerts = new AlertsContext(alertableSelection, prioritySelection);

        //Writing that data to memory
        StorageHandler handler = new StorageHandler(); // setup handler
        ResourcesStorage storage = handler.returnStorage(); // read
        storage.setAlerts(alerts); // edit
        handler.writeToStorage(storage);  // write

        switchScenes("MainPage.fxml", buttonBack);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //reading the data from storage
        StorageHandler handler = new StorageHandler(); // setup Handler
        ResourcesStorage storage = handler.returnStorage(); // read
        boolean[] alertable = storage.getAlertable();
        boolean[] priority = storage.getPriority();

        //lists of the radio buttons
        final RadioButton[] allButtonsAlertable = new RadioButton[]{rbA1, rbA2, rbA3, rbA4, rbA5};
        final RadioButton[] allButtonsPriority = new RadioButton[]{rbp1, rbp2, rbp3, rbp4, rbp5};

        //setting up the radio buttons with the users preference
        for(int i = 0; i < allButtonsAlertable.length; i++){
            if(alertable[i] || priority[i])
                allButtonsAlertable[i].fire();
            if(priority[i])
                allButtonsPriority[i].fire();
        }

    }
}
