package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /**
         * Split the loading into a loader and a load function - because that way you
         * get access to the loader and can use it to load the controller.
         * This controller is needed in order to set scenes
         */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene settingsScene = primaryStage.getScene();
        // The code above is loading the root scene, in this case the settings menu that I have done.


        /**
         * The setup switches method will deal with switches scenes, see that method
         */
        setupSwitches(fxmlLoader, settingsScene);



        /**
         * Default code for initialising the first scene (not the root variable should be used
         * for the first scene that is loaded, hence in my local file I have used it here
         * this can be changed for a name identifying what scene it refers to
         */


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 450, 800));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


    /**
     *
     * @param fxmlLoader: the loader for the current scene (the settings scene in this case)
     * @param currentScene: the scene object for the current scnee, so that it can be injected into others scene's
     *                    controller classes.
     * @throws IOException
     */
    private void setupSwitches(FXMLLoader fxmlLoader, Scene currentScene) throws IOException{

        //TODO Properly comment this up in the relevant controller classes,
        // THIS CODE HAS MIGRATED TO RESIDE WITHIN EACH CONTROLLER CLASS, SO THAT EACH CONTROLLER
        // CLASS CAN SWITCH SCENES MANY TIMES INSTEAD OF JUST ONCE, SEE SETTINGS CONTROLLER CLASS FOR AN EXAMPLE
        // Template code is below.

        /**
         * Getting loader and a pane for the second scene - in this case the languages settings scene
         *
         * Here we need to get the resource
         * Then we need to load the second pane by callling the load method in the fxmlLoader (which is using the
         * fxml file of the scene you want to load
          */

        /*
        FXMLLoader LanguagesSettingsLoader = new FXMLLoader(getClass().getResource("LanguageSettings.fxml"));
        Parent LanguagesSettingsPane = LanguagesSettingsLoader.load();
        Scene LanguagesSettingsScene = new Scene(LanguagesSettingsPane, 450, 800);


        // injecting second scene  - languages scene into the controller of the first scene
        SettingsController settingsController = (SettingsController) fxmlLoader.getController();
        settingsController.setLanguagesScene(LanguagesSettingsScene);
        */

        /*
        FXMLLoader SettingsLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent SettingsPane = SettingsLoader.load();
        Scene SettingsScene = new Scene(SettingsPane, 450, 800);

        // injecting settings scene into the controller of the settings scene
        LanguagesController languagesController = (LanguagesController) LanguagesSettingsLoader.getController();
        languagesController.setSettingsScene(SettingsScene);
        */


    }


}
