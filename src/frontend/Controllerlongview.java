package frontend;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Controllerlongview extends ControllerMaster implements Initializable{

    @FXML
    private Button back_button, Alerts_Button, Setting_Button;
    

    public void pressBack(ActionEvent event) throws IOException{
        //TODO: link back to hompage
        switchScenes("MainPage.fxml", back_button);
    }
    public void pressSettings(ActionEvent event) throws IOException{
        //TODO: link to settings
        switchScenes("Settings.fxml", Setting_Button);
    }
    public void pressAlerts(ActionEvent event)throws IOException {
        //TODO: link back to alerts
        switchScenes("AlertSettings.fxml", Alerts_Button);
    }

    @FXML
    private GridPane pane10;



    //on window open initialize seting weather panes
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: put image depending on the weather
        File file = new File("sun.jpeg");
        Image image = new Image(file.toURI().toString());

        //gridepane settings
        pane10.setVgap(4);
        pane10.setGridLinesVisible(true);
        pane10.setLayoutY(130*7);

        for (int i = 0; i < 7; i++) {
            //TODO: select image based on real weather

            //create a new image icon
            ImageView imageView2=new ImageView(image) ;

            //set it to dimention 100x100(if possible to do so without deforming it)
            imageView2.setPreserveRatio(true);
            imageView2.setFitHeight(100);
            imageView2.setFitWidth(100);

            //insert two (or more) texts fields, containitg temperature and weather
            Text temperature=new Text("20°");
            Text other=new Text("sun");

            //insert all the fields for a single day in a pane
            Pane pane=new Pane();
            pane.setLayoutY(120);
            pane.getChildren().add(imageView2);

            //add the pane to a grid
            pane10.addRow(i,pane);
        }

    }

}
