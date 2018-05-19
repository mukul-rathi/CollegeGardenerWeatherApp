package frontend;
import backend.WeatherData;
import backend.WeatherType;
import backend.WindDirection;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Controllerlongview extends ControllerMaster implements Initializable{ //

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


        //all the data relevant that will be shown in the 7 days view
        WeatherData weatherData=new WeatherData();
        List<WeatherType> weatherType7days=weatherData.getDailyWeatherType();
        List<Integer> temperature7days=weatherData.getDailyTemperature();
        List<Double> chanceOfRain7days=weatherData.getDailyChanceOfRain();


        for (int i = 0; i < 7; i++) {
            //current weather
            WeatherType weather=weatherType7days.get(i);
            //create a new image icon based on real weather
            System.out.println(weather);
            ImageView imageView2=new ImageView(WeatherType.geticon(weather)) ;

            //set it to dimention 100x100(if possible to do so without deforming it)
            imageView2.setPreserveRatio(true);
            imageView2.setFitHeight(90);
            imageView2.setFitWidth(90);

            //create text box containing the temperature
            Text temperature=new Text(temperature7days.get(i).toString()+"°");
            //convert the chance of rain to percentuage
            Integer percentuageRain= (int)(chanceOfRain7days.get(i)*100);
            //create text box containing chance of raining
            Text chanceOfRain=new Text("Chance of rain: " + percentuageRain.toString()+"%");
            //set font, size... for the two text boxes
            chanceOfRain.setFont(Font.font("roboto",  FontPosture.REGULAR, 16));
            temperature.setFont(Font.font("roboto",  FontPosture.REGULAR, 20));
            //position the temperature
            temperature.setTranslateX(100);
            temperature.setTranslateY(30);
            //position the chance of Rain
            chanceOfRain.setTranslateX(100);
            chanceOfRain.setTranslateY(50);

            //create a pane to insert into the grid
            Pane pane=new Pane();
            pane.setLayoutY(120);
            //add to it the wanted fields
            pane.getChildren().add(imageView2);
            pane.getChildren().add(chanceOfRain);
            pane.getChildren().add(temperature);

            //add the pane to a grid
            pane10.addRow(i,pane);
        }

    }

}
