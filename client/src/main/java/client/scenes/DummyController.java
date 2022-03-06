package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DummyController implements Initializable{
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Inject
    public DummyController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    public void showDummy(){
        mainCtrl.showDummy();
    }
}
