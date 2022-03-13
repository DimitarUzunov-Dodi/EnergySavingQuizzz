package client.scenes;

import com.google.inject.Inject;
import javafx.event.ActionEvent;

public class SettingsCtrl {
    private final MainCtrl mainCtrl;

    @Inject
    public SettingsCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     * @param event passed by JavaFX by default
     */
    public void adminAction(ActionEvent event){
        mainCtrl.showAdmin();
    }
}
