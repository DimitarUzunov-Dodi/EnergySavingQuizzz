package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.fxml.FXML;

public class SettingsCtrl extends SceneController {

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
}
