package frontend;

import backend.WeatherType;

import backend.WeatherData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.HashMap;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

public class MainPage extends ControllerMaster implements Initializable {

    public TabPane tabs;
    public Button daysButton;
    public Button settingsButton;
    public Button alertButton;
    public ImageView currentWeatherImage;
    public Text currentTemperature;
    private HashMap<WeatherType, Boolean> altertable;
    private HashMap<WeatherType, Boolean> priority;

    // attributes from settings page
    private String userLocation;  // the location for the API
    private boolean tempScale; // true is centigrade, false is fahrenheit
    private boolean speedScale; // true is mph, false is kph

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/frontend/icons/weather-storm.png");
        Image image = new Image(file.toURI().toString());
        currentWeatherImage.setImage(image);

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        for (int i = 0; i < 24; i++) {

            int j = (hour + i) % 24;

            Tab t = new Tab();

            t.setGraphic(buildImage("src/frontend/icons/weather-clear.png"));
            if (j < 10) {
                t.setText("0" + j + ":00");

            } else {
                t.setText(j+":00");
            }
            VBox v = new VBox();

            t.setContent(v);
            tabs.getTabs().add(t);
        }

        //settings parameters setup
        userLocation = "Cambridge";
        tempScale = speedScale = true;

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

    @Override
    protected void init_alerts(SceneResource resource) {


    }

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
        System.out.println("FHEOFHNEOf");
        primary.show();
    }

    public static ImageView buildImage(String path) {
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();

        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        imageView.setImage(image);
        return imageView;
    }
}
