package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class ServerJoinCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @Inject
    public ServerJoinCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void onJoinPublic() {
        mainCtrl.showWaitingRoom("public game id");
    }
}
