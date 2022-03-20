package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerJoinCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private TextField gameCodeField;

    @Inject
    public ServerJoinCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void onJoinPublic() {
        mainCtrl.showWaitingRoom("public game id");
    }

    public void onJoinPrivate() {
        mainCtrl.showWaitingRoom(gameCodeField.getText());
    }

    public void onCreatePrivate() {
        String id = "gen new id";
        mainCtrl.showWaitingRoom(id);
        UserAlert.userAlert("INFO", "Game code: " + id, "Share this with the people you want to play with.");
    }

    public void onBackButton() {
        mainCtrl.showSplashScreen();
    }
}
