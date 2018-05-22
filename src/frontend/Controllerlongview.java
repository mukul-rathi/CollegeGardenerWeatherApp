package frontend;
import backend.WeatherData;
import backend.WeatherType;
import frontend.storage.ResourcesStorage;
import frontend.storage.StorageHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import java.text.SimpleDateFormat;

public class Controllerlongview extends ControllerMaster implements Initializable{ //

    // user location setting, so that the 7 day view represents the correct location
    private String userLocation;
    String tScale;  // the string that represents either celsius or fahrenheit - setup in initialize
    Double tFactor; // the value that represents the factor to convert oC to oF - setup in initialize
    Double tDifference; // the value that represents the scalar addition needed to convert oC to OF
    // setup in initialize

    @Override
    protected void init(SceneResource resource) {

    }

    @FXML
    private Button back_button,Setting_Button, Alerts_Button;

    public void pressBack(ActionEvent event) throws IOException {
        //link back to hompage
        switchScenes("MainPage.fxml", back_button);

    }
    public void pressSettings(ActionEvent event)throws IOException{
        //link to settings
        switchScenes("Settings.fxml", Setting_Button);
    }
    public void pressAlerts(ActionEvent event)throws IOException{
        //link back to alerts
        switchScenes("AlertSettings.fxml", Alerts_Button);
    }

    @FXML
    private GridPane pane10;



    //on window open initialize seting weather panes
    public void initialize(URL location, ResourceBundle resources) {
        //gridepane settings
        pane10.setVgap(4);
        pane10.setGridLinesVisible(true);
        pane10.setLayoutY(130*7);

        //getting user data loaded
        setupUserData();

        //all the data relevant that will be shown in the 7 days view
        WeatherData weatherData=new WeatherData();
        weatherData.setLocation(userLocation);
        List<WeatherType> weatherType7days=weatherData.getDailyWeatherType();
        List<Integer> temperature7days=weatherData.getDailyTemperature();
        List<Double> chanceOfRain7days=weatherData.getDailyChanceOfRain();


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        date.add(Calendar.DATE  , 1);




        for (int i = 0; i < 7; i++) {
            //current weather
            WeatherType weather=weatherType7days.get(i);
            //create a new image icon based on real weather
            System.out.println(weather);
            ImageView imageView2=new ImageView(WeatherType.geticon(weather)) ;

            //current data
            String currentData=format.format(date.getTime()).toString();
            //add 1 so that you can use it for the next day
            date.add(Calendar.DATE  , 1);

            //set it to dimention 100x100(if possible to do so without deforming it)
            imageView2.setPreserveRatio(true);
            imageView2.setFitHeight(90);
            imageView2.setFitWidth(90);

            //create text box containing the temperature
            // loc.setText(Double.toString(temperature7days.get(i)*tFactor+tDifference) + tScale);
            Text temperature=new Text(Math.round(100.0*(temperature7days.get(i)*tFactor+tDifference))/100.0 + tScale);
            //convert the chance of rain to percentuage
            Integer percentuageRain= (int)(chanceOfRain7days.get(i)*100);
            //create text box containing chance of raining
            Text chanceOfRain=new Text("Chance of rain: " + percentuageRain.toString()+"%");
            //create text box containing chance of raining
            Text dayOfWeek=new Text(currentData);

            //set font, size... for the two text boxes
            chanceOfRain.setFont(Font.font("roboto",  FontPosture.REGULAR, 16));
            temperature.setFont(Font.font("roboto",  FontPosture.REGULAR, 20));
            dayOfWeek.setFont(Font.font("roboto",  FontPosture.REGULAR, 20));
            //position the temperature
            temperature.setTranslateX(100);
            temperature.setTranslateY(30);
            //position the chance of Rain
            chanceOfRain.setTranslateX(100);
            chanceOfRain.setTranslateY(50);

            //position the date
            dayOfWeek.setTranslateX(300);
            dayOfWeek.setTranslateY(30);

            //create a pane to insert into the grid
            Pane pane=new Pane();
            pane.setLayoutY(120);
            //add to it the wanted fields
            pane.getChildren().add(imageView2);
            pane.getChildren().add(chanceOfRain);
            pane.getChildren().add(temperature);
            pane.getChildren().add(dayOfWeek);

            //add the pane to a grid
            pane10.addRow(i,pane);
        }

    }

    private void setupUserData(){
        StorageHandler handler = new StorageHandler();
        ResourcesStorage storage = handler.returnStorage();
        userLocation = storage.getUserLocation();

        boolean tempScale = storage.isTempScale();

        //depending on users choice, set temperature scale
        if(tempScale) {
            tScale = "°C";
            tFactor = 1.0;
            tDifference = 0.0;
        }
        else {
            tScale = "°F";
            tFactor = 1.8;
            tDifference = 32.0;
        }

    }

}
