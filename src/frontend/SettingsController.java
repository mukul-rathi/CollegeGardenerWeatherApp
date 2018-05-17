package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

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
    private TextArea textAreaLocationInfo, textAreaTemperatureInfo, textAreaWindspeedInfo, textAreaLanguageInfo;

    // The string used to set the location of the object


    @Override
    protected void init(SceneResource resource) {

        //TODO include a check to see if the location has been updated before referencing it
        if (!resource.getLocation().equals(null)){
            //System.out.println(resource.getLocation());
            //System.out.print(textAreaLocationInfo.getText());
            textAreaLocationInfo.setText(resource.getLocation());
        }

    }


    /**
     * Handling the buttons
     */

    //Todo the back button needs to go back to the homescreen

    public void handleButtonBack(){
        /**
         * Here we go back to the previous screen, however it is that we do this
         */
        //System.out.println("Back button pressed");
    }

    //Todo the location change now needs to go to the API section

    public void handleButtonLocationChange() throws IOException{
        /**
         * Here we go to location screen
         */
        //System.out.println("Location change button pressed");

        //initialises the location of the static location to cambridge for now
        //should load from a file that stores the data


        switchScenes("LocationSettings.fxml", buttonLocationChange);


    }

    //TODO we will not bother implementing language, but the interface is there none the less

    public void handleButtonLanguageChange() throws IOException{
        /**
         * Here we go back to the language screen
         */
        switchScenes("LanguageSettings.fxml", buttonLanguageChange);
    }


    /**
     * Handling the sliders
     */


    //TODO actually change the scale being used for the temperature in the API
    public void handleSliderTemperature(){

        //<0.5 represents centigrade, >0.5 represents fahrenheit
        //System.out.println("Here it is the slider has been changed");

        double value = sliderTemperature.getValue();
        //System.out.print(value);

        if(value >= 0.5){
            textAreaTemperatureInfo.setText("Scale: Centigrade");
        }
        else {
            textAreaTemperatureInfo.setText("Scale: Fahrenheit");
        }

    }

    public void handleSliderWindspeed(){
        //<0.5 represents mph, >0.5 represents kph

        //System.out.println("Here it is the slider has been changed");

        double value = sliderWindspeed.getValue();
        //System.out.print(value);

        if(value >= 0.5){
            textAreaWindspeedInfo.setText("Scale: Mph");
        }
        else {
            textAreaWindspeedInfo.setText("Scale: Kph");
        }
    }

}
