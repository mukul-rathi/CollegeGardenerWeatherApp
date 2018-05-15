package sample;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.RowConstraints;
import javax.management.ImmutableDescriptor;


public class Controllerlongview implements Initializable{

    public void pressBack(ActionEvent event){
        //TODO: link back to hompage
    }
    public void pressSettings(ActionEvent event){
        //TODO: link to settings
    }
    public void pressAlerts(ActionEvent event){
        //TODO: link back to alerts
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
