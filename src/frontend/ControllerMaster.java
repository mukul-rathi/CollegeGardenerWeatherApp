package frontend;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class ControllerMaster{

    /**
     *
     * @param location : This is a string denoting where the XML file is (put in a suitable package)
     * @param buttonBack : This is a button widget that has been pressed to switch to a different scene
     *                   This is used to gett he context of the scene.
     * @throws IOException
     */
    protected void switchScenes(String location, Button buttonBack) throws IOException {
        // Creates an FXML loader from the file given, then sets the scene
        FXMLLoader SceneLoader = new FXMLLoader(getClass().getResource(location));
        Parent nextPane = SceneLoader.load();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        height-=100;
        Scene nextScene = new Scene(nextPane, 450, height);
        height=Math.min(height, 800);

        Stage primaryStage = (Stage)buttonBack.getScene().getWindow();
        primaryStage.setScene(nextScene);
    }


    /**
     *
     * This is the same method as above, but modified to allow data transfer between scenes.
     */

    protected void switchScenesPassData(String location, Button buttonBack, SceneResource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        Parent nextPane = loader.load();

        ControllerMaster controller = loader.getController();
        controller.init(resource);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        height-=100;
        height=Math.min(height, 800);

        Scene nextScene = new Scene(nextPane, 450, height);


        Stage primaryStage = (Stage)buttonBack.getScene().getWindow();
        primaryStage.setScene(nextScene);

    }



    protected abstract void init(SceneResource resource);

}