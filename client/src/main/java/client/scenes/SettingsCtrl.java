package client.scenes;

import static client.Main.primaryStage;

import client.MyFXML;
import client.communication.CommunicationUtils;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsCtrl extends SceneController {

    private String server;
    private final Stage stage; // the stage we show this scene on
    private Double posX; // saved window position
    private Double posY;

    @FXML
    private TextField serverText;
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
        initTextField();
        stage.setX(posX);
        stage.setY(posY);
        present(stage);
        stage.show();
        stage.requestFocus();
    }

    /**
     * Show the scene without the server field. For use during game.
     */
    @Override
    public void show(Object... args) {

        stage.setX(posX);
        stage.setY(posY);
        present(stage);
        stage.show();
        serverText.setVisible(false);
        stage.requestFocus();
    }

    /**
     * initialises the text field.
     */
    private void initTextField() {
        server = CommunicationUtils.serverAddress;
        serverText.setText(server);
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
        } else {
            FileUtils.setTheme("LightTheme");
            colourModeButton.setText("Dark Mode");
        }
        posX = stage.getX();
        posY = stage.getY();
        myFxml.showScene(SettingsCtrl.class);
    }

    /**
     * changes the current server the client is connected to.
     */
    @FXML
    private void changeServer() {
        server = serverText.getText().strip();
        CommunicationUtils.serverAddress = server;

    }


}
