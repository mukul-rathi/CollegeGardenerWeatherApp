package frontend;
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

    @FXML
    public void switchToDays(ActionEvent actionEvent) {
        System.out.println("Days");
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
