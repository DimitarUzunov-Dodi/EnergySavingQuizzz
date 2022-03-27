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
            FileUtils.setTheme("DarkTheme");
            System.out.println(FileUtils.getTheme());
            myFxml.showScene(SplashCtrl.class);


        } else {
            FileUtils.setTheme("LightTheme");
            System.out.println(FileUtils.getTheme());
            colourModeButton.setText("Dark Mode");
            myFxml.showScene(SplashCtrl.class);
        }
    }

}
