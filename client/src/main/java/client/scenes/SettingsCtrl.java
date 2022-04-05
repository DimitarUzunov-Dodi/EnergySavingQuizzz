package client.scenes;

import static client.Main.primaryStage;

import client.MyFXML;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsCtrl extends SceneController {

    private final Stage stage; // the stage we show this scene on
    private Double posX; // saved window position
    private Double posY;

    @FXML
    private Button colourModeButton;

    /**
     * Sets up the secondary stage as well.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private SettingsCtrl(MyFXML myFxml) {
        super(myFxml);

        stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setMinHeight(250);
        stage.setMinWidth(300);
        stage.setTitle("Settings");
        stage.setOnCloseRequest(this::onBackButton);

        posX = primaryStage.getX();
        posY = primaryStage.getY();
    }

    /**
     * Show the scene and un-hide the secondary stage.
     */
    @Override
    public void show() {
        stage.setX(posX);
        stage.setY(posY);
        present(stage);
        stage.show();
        stage.requestFocus();
    }

    /**
     * Function called by Back button when clicked.
     * Hides the secondary stage.
     */
    @FXML
    private void onBackButton(Event event) {
        posX = stage.getX();
        posY = stage.getY();
        stage.hide();
        primaryStage.requestFocus();
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     */
    @FXML
    private void changeColourModeAction() {
        if (colourModeButton.getText().equals("Dark Mode")) {
            colourModeButton.setText("Light Mode");
            FileUtils.setTheme("DarkTheme");
            System.out.println(FileUtils.getTheme());
        } else {
            FileUtils.setTheme("LightTheme");
            System.out.println(FileUtils.getTheme());
            colourModeButton.setText("Dark Mode");
        }
        posX = stage.getX();
        posY = stage.getY();
        myFxml.showScene(SettingsCtrl.class);
    }

}
