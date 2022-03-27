package client.scenes;

import client.MyFXML;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SettingsCtrl extends SceneController {
    @FXML
    GridPane background;
    @FXML
    Button colourModeButton;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private SettingsCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        scene.getStylesheets().removeAll();
        scene.getStylesheets().add(FileUtils.getTheme());
        showScene();

    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     */
    @FXML
    private void adminAction() {
        myFxml.showScene(AdminCtrl.class);
    }

    /**
     * Function called by Back to MainMenu button when clicked. Changes scene to SplashScreen scene.
     */
    @FXML
    private void splashAction() {
        myFxml.showScene(SplashCtrl.class);
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     */
    @FXML
    private void changeColourModeAction() {
        if (colourModeButton.getText().equals("Dark Mode")) {
            colourModeButton.setText("Light Mode");
            FileUtils.setTheme("file:/C:/OOPP/Project/client/build/resources/main/"
                    + "client/css/DarkTheme.css");
            myFxml.showScene(SplashCtrl.class);


        } else {
            FileUtils.setTheme("file:/C:/OOPP/Project/client/build/resources/main"
                    + "/client/css/LightTheme.css");
            colourModeButton.setText("Dark Mode");
            myFxml.showScene(SplashCtrl.class);
        }
    }

}
