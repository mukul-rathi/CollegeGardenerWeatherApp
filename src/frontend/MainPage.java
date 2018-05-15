package frontend;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage implements Initializable {

    public TabPane tabs;
    public Button daysButton;
    public Button settingsButton;
    public Button alertButton;
    public ImageView currentWeatherImage;
    public Text currentTemperature;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/frontend/icons/weather-storm.png");
        Image image = new Image(file.toURI().toString());
        currentWeatherImage.setImage(image);

        for (int i = 0; i < 24; i++) {
            Tab t = new Tab();
            t.setGraphic(buildImage("src/frontend/icons/weather-clear.png"));
            if (i < 10) {
                t.setText("0" + i + ":00");
            } else {
                t.setText(i+":00");
            }
            VBox v = new VBox();

            t.setContent(v);
            tabs.getTabs().add(t);
        }
    }

    public void switchToDays() {
        System.out.println("Days Screen");
    }

    public void switchToSettings() {
        System.out.println("Settings Screen");
    }

    public void switchToAlerts() {
        System.out.println("Alert Screen");
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
