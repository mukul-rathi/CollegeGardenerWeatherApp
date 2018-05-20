package frontend;

import frontend.storage.ResourcesStorage;
import frontend.storage.StorageHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends ControllerMaster implements Initializable{

    /**
     * Use the @FXML label to link objects in code to fxml objects
     * By naming them the same way as their fx:id parameter
     */

    /*
    * All of the widgets needed are bellow, of three main types
     */

    @FXML
    private Button buttonBack, buttonLocationChange, buttonLanguageChange;

    @FXML
    private Slider sliderTemperature, sliderWindspeed;

    @FXML
    private Text textAreaTemperatureInfo, textAreaWindspeedInfo, textAreaLocationInfo, textAreaLanguageInfo;
    
    // Settings fields information
    private String userLocation;  // the location for the API
    private boolean tempScale; // true is centigrade, false is fahrenheit
    private boolean speedScale; // true is mph, false is kph

    private static final String defaultLocation = "Cambridge";

    @Override
    protected void init(SceneResource resource) {

        if (!(resource.getLocation() == null) && !resource.getLocation().equals("")){
            //System.out.println(resource.getLocation());
            //System.out.print(textAreaLocationInfo.getText());
            textAreaLocationInfo.setText("Current Location: " + resource.getLocation());
            userLocation = resource.getLocation();
        }
        else textAreaLocationInfo.setText("Current Location: "+ defaultLocation);

    }


    /**
     * Handling the buttons
     */


    public void handleButtonBack() throws IOException{

        //pass back to main page, need to pass in the location and temp and speed scales

        // At this point, the slider values need to be updated when they pass back
        storeSliderValues();


        //Switch scenes
        switchScenes("MainPage.fxml", buttonBack);

    }

    public void handleButtonLocationChange() throws IOException{

        // At this point, the slider values need to be updated
        storeSliderValues();

        //Switches to locations tab
        switchScenes("LocationSettings.fxml", buttonLocationChange);


    }


    public void handleButtonLanguageChange() throws IOException{
        /**
         * Here we go back to the language screen
         */

        // At this point, the slider values need to be updated when they pass back
        storeSliderValues();

        switchScenes("LanguageSettings.fxml", buttonLanguageChange);
    }


    /**
     * Handling the sliders
     */


    public void handleSliderTemperature(){

        //<0.5 represents centigrade, >0.5 represents fahrenheit
        //System.out.println("Here it is the slider has been changed");

        double value = sliderTemperature.getValue();
        //System.out.print(value);

        if(value >= 0.5){
            textAreaTemperatureInfo.setText("Scale: Celsius");
            tempScale = true;
        }
        else {
            textAreaTemperatureInfo.setText("Scale: Fahrenheit");
            tempScale = false;
        }

    }

    public void handleSliderWindspeed(){
        //<0.5 represents mph, >0.5 represents kph

        //System.out.println("Here it is the slider has been changed");

        double value = sliderWindspeed.getValue();
        //System.out.print(value);

        if(value >= 0.5){
            textAreaWindspeedInfo.setText("Scale: Mph");
            speedScale = true;
        }
        else {
            textAreaWindspeedInfo.setText("Scale: Kph");
            speedScale = false;
        }
    }

    public void storeSliderValues(){

        //loads the data form the file
        StorageHandler handler = new StorageHandler();
        ResourcesStorage resourcesStorage = handler.returnStorage();

        //updates the storage object
        resourcesStorage.setSpeedScale(speedScale);
        resourcesStorage.setTempScale(tempScale);

        //writes the storage to the file
        handler.writeToStorage(resourcesStorage);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StorageHandler handler = new StorageHandler();
        ResourcesStorage userResources = handler.returnStorage();

        //Setup location
        textAreaLocationInfo.setText(userResources.getUserLocation());

        //Setup temperature settings
        // true is celsius
        if(userResources.isTempScale()){
            textAreaTemperatureInfo.setText("Scale: Celsius");
            sliderTemperature.setValue(0);
        } else {
            textAreaTemperatureInfo.setText("Scale: Fahrenheit");
            sliderTemperature.setValue(1);
        }


        //Setup wind-speed settings
        // true is Mph
        if(userResources.isSpeedScale()){
            textAreaWindspeedInfo.setText("Scale: Mph");
            sliderWindspeed.setValue(0);
        } else {
            textAreaWindspeedInfo.setText("Scale: Kph");
            sliderWindspeed.setValue(1);
        }

        //Setup Language
        textAreaLanguageInfo.setText(userResources.getLanguage());

    }
}
