package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SettingsCtrl extends SceneController {

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    private SettingsCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    @Override
    public void show() {
        showScene();
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     * @param event passed by JavaFX by default
     */
    @FXML
    protected void adminAction(ActionEvent event) {
        myFXML.showScene(AdminCtrl.class);
    }
}
