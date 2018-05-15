package Ben_frontend;

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

    @FXML
    private Button buttonBack, buttonLocationChange, buttonLanguageChange;

    @FXML
    private Slider sliderTemperature, sliderWindspeed;

    @FXML
    private TextArea textAreaLocationInfo, textAreaTemperatureInfo, textAreaWindspeedInfo, textAreaLanguageInfo;


    /**
     * Handling the buttons
     */

    public void handleButtonBack(){
        /**
         * Here we go back to the previous screen, however it is that we do this
         */
        System.out.println("Back button pressed");
    }

    public void handleButtonLocationChange() throws IOException{
        /**
         * Here we go to location screen
         */
        System.out.println("Location change button pressed");
        switchScenes("LocationSettings.fxml", buttonLocationChange);


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

    public void handleSliderTemp(){

        //0 represents centigrade, 1 represents fahrenheit

        double value = sliderTemperature.getValue();

        //if(value)

    }
}
