package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;

public class SettingsController extends ControllerMaster{

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

        SceneResource resource = new SceneResource();
        if(!(userLocation == null))
            resource.setLocation(userLocation);
        else
            resource.setLocation(defaultLocation);
        resource.setSpeedScale(speedScale);
        resource.setTempScale(tempScale);


        System.out.println(resource.getLocation());
        System.out.println(resource.isSpeedScale());
        System.out.println(resource.isTempScale());

        switchScenesPassData("MainPage.fxml", buttonBack, resource);

    }

    public void handleButtonLocationChange() throws IOException{

        //Passes current location to the locations pane
        SceneResource sceneResource = new SceneResource();
        if(userLocation!= null){
            sceneResource.setLocation(userLocation);
        } else {
            sceneResource.setLocation(defaultLocation);
        }


        switchScenesPassData("LocationSettings.fxml", buttonLocationChange, sceneResource);


    }


    public void handleButtonLanguageChange() throws IOException{
        /**
         * Here we go back to the language screen
         */
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
            textAreaTemperatureInfo.setText("Scale: Centigrade");
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

}
