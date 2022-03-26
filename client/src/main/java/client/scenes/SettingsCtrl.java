package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;

import java.util.EventListener;

public class SettingsCtrl extends SceneController {
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
     * Function called by Back to Mani menu button when clicked. Changes scene to SplashScreen scene.
     */
    @FXML
    private void SplashAction() {
        myFxml.showScene(SplashCtrl.class);
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     */
    @FXML
    private void ChangeColourModeAction() {
        if(colourModeButton.getText().equals("Dark Mode")){
            colourModeButton.setText("Light Mode");
        }else{
            colourModeButton.setText("Dark Mode");
        }




    }

}
