package frontend;

import backend.WeatherType;
import backend.WeatherData;
import backend.WindDirection;
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

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

import java.util.*;

import static oracle.jrockit.jfr.events.Bits.intValue;

public class MainPage extends ControllerMaster implements Initializable {

    public TabPane tabs;
    public Button daysButton;
    public Button settingsButton;
    public Button alertButton;
    public ImageView currentWeatherImage, raindropImage;
    public Text currentTemperature, rainChance;
    private HashMap<WeatherType, Boolean> altertable;
    private HashMap<WeatherType, Boolean> priority;

    // attributes from settings page
    private String userLocation;  // the location for the API
    private boolean tempScale; // true is centigrade, false is fahrenheit
    private boolean speedScale; // true is mph, false is kph

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WeatherData w = new WeatherData();
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        // current hour
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // settings parameters setup
        userLocation = "Cambridge";
        tempScale = speedScale = true;

        // get the weather info for the next 24 hours
        List<WeatherType> hourlyWeather = w.get24HourWeatherType();
        List<Integer> hourlyTemp = w.get24HourTemperature();
        List<Double> hourlyRain = w.get24HourChanceOfRain();
        List<Double> hourlyHumidity = w.get24HourHumidity();
        List<WindDirection> hourlyDirection = w.get24HourWindDirection();
        List<Double> hourlySpeed = w.get24HourWindSpeed();
        List<String> hourlySummary = w.get24HourSummary();

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

            // temperature
            Text loc = new Text();
            loc.setFill(new Color(0.45, 0.45, 0.45, 1));
            loc.setText(Integer.toString(hourlyTemp.get(i)) + "°");
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

            // wind speed and direction
            Text wind = new Text();
            wind.setFill(new Color(0.45, 0.45, 0.45, 1));
            wind.setText("Wind: " + Double.toString(hourlySpeed.get(i)) + "mph " + hourlyDirection.get(i).toString());
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
        currentTemperature.setText(Integer.toString(w.getCurrentTemperature()) + "°");
    }

    // From settings pane
    @Override
    protected void init(SceneResource resource) {

        if (!resource.isAlertsTab()){
            userLocation = resource.getLocation();
            tempScale = resource.isTempScale();
            tempScale = resource.isSpeedScale();
        } else {
            altertable = resource.getAlertable();
            priority = resource.getPriority();
        }



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
        primary.setScene(new Scene(days, 450, 800));
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
