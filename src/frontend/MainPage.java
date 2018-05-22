package frontend;

import backend.WeatherType;
import backend.WeatherData;
import backend.WindDirection;
import frontend.storage.ResourcesStorage;
import frontend.storage.StorageHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

import java.util.*;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class MainPage extends ControllerMaster implements Initializable {

    public TabPane tabs;
    public ImageView alertImage;
    public Button daysButton;
    public Button settingsButton;
    public Button alertButton;
    public ImageView currentWeatherImage, raindropImage;
    public Text currentTemperature, rainChance;
    @FXML
    private GridPane allertsGrid;
    private HashMap<WeatherType, Boolean> altertable;
    private HashMap<WeatherType, Boolean> priority;

    // The handler for the persistent storage
    private StorageHandler storageHandler;
    private ResourcesStorage storageResources;

    // attributes from settings page
    private String userLocation;  // the location for the API
    private boolean tempScale; // true is centigrade, false is fahrenheit
    private boolean speedScale; // true is mph, false is kph
    private String sScale;  // the string that represents either mph or kph - setup in initialize
    private Double factor; // the value that represents the factor to convert mph to kph - setup in initialize
    private String tScale;  // the string that represents either celsius or fahrenheit - setup in initialize
    private Double tFactor; // the value that represents the factor to convert oC to oF - setup in initialize
    private Double tDifference; // the value that represents the scalar addition needed to convert oC to OF
    // setup in initialize

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //THIS is where user settings are loaded, see method below
        setupUserSettings();

        WeatherData w = new WeatherData();
        w.setLocation(userLocation);
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        // current hour
        int hour = calendar.get(Calendar.HOUR_OF_DAY);


        // get the weather info for the next 24 hours
        List<WeatherType> hourlyWeather = w.get24HourWeatherType();
        List<Integer> hourlyTemp = w.get24HourTemperature();
        List<Double> hourlyRain = w.get24HourChanceOfRain();
        List<Double> hourlyHumidity = w.get24HourHumidity();
        List<WindDirection> hourlyDirection = w.get24HourWindDirection();
        List<Double> hourlySpeed = w.get24HourWindSpeed();
        List<String> hourlySummary = w.get24HourSummary();



        //gridpane settings
        allertsGrid.setVgap(2);
        allertsGrid.setGridLinesVisible(true);
        //get the allerts from WeatherData
        String alert=w.getAlerts();
        //get if there is a priority
        boolean priority=w.getPriority();

        //if there is no alert for the location
        if(alert.equals("No alerts for this location.")){
            //get the green image of the danger signal
            File file = new File("src/frontend/icons/alert_green.png");
            Image image=new Image(file.toURI().toString());
            //put image signal on the screen
            alertImage.setImage(image);
            //write that there are no alert
            Label label=new Label(alert);
            label.setMaxWidth(100);
            //wrap test allows new line when line is full
            label.setWrapText(true);
            allertsGrid.addRow(0,label);
        }
        else{//if there at least one alert for the location
            //get the red image of the danger signal
            File file;
            //if there is a priority event put allert red, otherwise put alert yellow
            if(priority) {file = new File("src/frontend/icons/alert_orange.png");}
            else{file = new File("src/frontend/icons/alert_yellow.png");}
            Image image=new Image(file.toURI().toString());
            //put image signal on the screen
            alertImage.setImage(image);
            //split the alert on ',', according tho the getAlerts method in weatherData
            String[] alerts = alert.split(",");
            //put every alert on a box in a grid
            for(int i=0;i<alerts.length;i++){
                System.out.println(alerts[i]);
                Label label=new Label(alerts[i]);
                label.setWrapText(true);
                //wrap test allows new line when line is full
                label.setMaxWidth(100);
                allertsGrid.addRow(i,label);
            }

        }


        // create the tabs for each hour
        for (int i = 0; i < 24; i++) {
            int j = (hour + i) % 24; // find the next 24 hours

            Tab t = new Tab();

            // Set the tab header for each tab
            t.setGraphic(buildImage(WeatherType.geticon(hourlyWeather.get(i))));
            String j24 = Integer.toString(j);

            if (j < 10) {
                j24 = "0" + j;
                t.setText(j24 + ":00");

            } else {
                t.setText(j24+":00");
            }


            // create pane to go in the tab
            Pane p = new Pane();

            //Weather icon
            ImageView image = new ImageView();
            image.setImage(WeatherType.geticon(hourlyWeather.get(i)));
            image.setFitHeight(108);
            image.setFitWidth(108);
            image.setX(10);
            image.setY(10);

            // time and place of weather
            Text timePlace = new Text();
            timePlace.setFill(new Color(0.45, 0.45, 0.45, 1));
            timePlace.setText(j24 + ":00" + ", " + userLocation);
            timePlace.setFont(new Font(20));
            timePlace.setX(130);
            timePlace.setY(50);

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

            // temperature
            Text loc = new Text();
            loc.setFill(new Color(0.45, 0.45, 0.45, 1));
            loc.setText(Math.round(100.0*(hourlyTemp.get(i)*tFactor+tDifference))/100.0 + tScale);
            loc.setFont(new Font(48));
            loc.setX(130);
            loc.setY(105);

            // chance of rain
            Text rainChance = new Text();
            rainChance.setFill(new Color(0.45, 0.45, 0.45, 1));
            rainChance.setText("Chance of Rain: " + Double.toString(hourlyRain.get(i)) + "%");
            rainChance.setFont(new Font(20));
            rainChance.setX(25);
            rainChance.setY(140);

            //humidity
            Text humidity = new Text();
            humidity.setFill(new Color(0.45, 0.45, 0.45, 1));
            humidity.setText("Humidity: " + Double.toString(hourlyHumidity.get(i)) + "%");
            humidity.setFont(new Font(20));
            humidity.setX(25);
            humidity.setY(170);

            //depending on users choice, set speed scale
            if(speedScale) {
                sScale = "mph ";
                factor = 1.0;
            }
            else {
                sScale = "kph ";
                factor = 1.60934;
            }

            // wind speed and direction
            Text wind = new Text();
            wind.setFill(new Color(0.45, 0.45, 0.45, 1));
            wind.setText("Wind: " + Math.round(100.0*hourlySpeed.get(i)*factor)/100.0 +
                    sScale + hourlyDirection.get(i).toString());
            wind.setFont(new Font(20));
            wind.setX(25);
            wind.setY(200);

            // summary of the weather for this time slot
            Text summary = new Text();
            summary.setFill(new Color(0.45, 0.45, 0.45, 1));
            summary.setText("Summary: " + hourlySummary.get(i).replaceAll("\"", ""));
            summary.setFont(new Font(20));
            summary.setX(25);
            summary.setY(230);

            // add all information to the pane
            p.getChildren().addAll(image, loc, rainChance, humidity, wind, summary, timePlace);
            t.setContent(p);
            tabs.getTabs().add(t);
        }

        // current chance of rain
        File file = new File("src/frontend/icons/raindrop.png");
        Image i  = new Image(file.toURI().toString());
        raindropImage.setY(10);
        raindropImage.setImage(i);
        rainChance.setText(Integer.toString(intValue(w.getCurrentChanceOfRain())) + "%");

        // current weather
        currentWeatherImage.setImage(WeatherType.geticon(w.getCurrentWeatherType()));
        currentTemperature.setText(Math.round(w.getCurrentTemperature()*tFactor+tDifference) + tScale);
    }


    private void setupUserSettings(){

        /**
         * loading in the persistent storage from memory
         *
         */

        storageHandler = new StorageHandler();
        storageResources = storageHandler.returnStorage();

        try{

            userLocation = storageResources.getUserLocation();
            tempScale = storageResources.isTempScale();
            speedScale = storageResources.isSpeedScale();

        } catch (NullPointerException e){
            //in this case, defaults are set

            //
            // settings parameters setup
            userLocation = "Cambridge";
            tempScale = speedScale = true;
        }


    }


    // From settings pane
    @Override
    protected void init(SceneResource resource) {

        /*
        System.out.println("init");

        if (!resource.isAlertsTab()){
            userLocation = resource.getLocation();
            tempScale = resource.isTempScale();
            tempScale = resource.isSpeedScale();
        } else {
            altertable = resource.getAlertable();
            priority = resource.getPriority();
        }
        */



    }

    protected void init_alerts(SceneResource resource) {


    }

    // functions to switch to the separate scenes
    @FXML
    public void switchToDays(ActionEvent actionEvent) {
        switchScene(actionEvent, "longview.fxml");
    }

    public void switchToSettings(ActionEvent actionEvent) {
        switchScene(actionEvent, "Settings.fxml");
    }

    public void switchToAlerts(ActionEvent actionEvent) {
        switchScene(actionEvent, "AlertSettings.fxml");
    }

    public void switchScene(ActionEvent a, String scene) {
        Parent days;
        try {
            days = FXMLLoader.load(getClass().getResource(scene));
        } catch (IOException e) {
            days = null;
        }
        Stage primary = (Stage) ((Node) a.getSource()).getScene().getWindow();
        primary.setScene(new Scene(days, 450, 700));
        primary.show();
    }

    // put an image in to an image view and put it into the tab
    public static ImageView buildImage(Image i) {
        ImageView imageView = new ImageView();

        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        imageView.setImage(i);
        return imageView;
    }
}
