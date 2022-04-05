package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.username;

import client.MyFXML;
import client.communication.CommunicationUtils;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.User;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class WaitingRoomCtrl extends SceneController {

    private ObservableList<String> playerList;
    @FXML
    private ListView<String> listView;
    @FXML
    private Text gameCodeLabel;
    @FXML
    private Text playersLabel;
    public static Timer pollingThread;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private WaitingRoomCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    /**
     * Please use customShow(String gameCode) method to switch to this scene.
     */
    @Override
    public void show() {
        setGameCode(currentGameID);

        pollingThread = new Timer();
        pollingThread.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshUserList();
                if (WaitingRoomCommunication.isStarted(currentGameID).getStatus() == 418) {
                    System.out.println("START ACTIVATED");
                    pollingThread.cancel();
                    Platform.runLater(() -> {
                        myFxml.showScene(GameScreenCtrl.class, true);
                    });

                }
            }
        }, 0, 1000);
        present();
    }

    private void refreshUserList() {
        playerList = FXCollections.observableList(
                CommunicationUtils.getAllUsers(currentGameID)
                        .orElse(new ArrayList<>(0))
                        .stream()
                        .map(User::getUsername)
                        .collect(Collectors.toList()));
        Platform.runLater(() -> {
            listView.setItems(playerList);
            playersLabel.setText(playerList.size() + " players");
        });
    }



    /**
     * Method sets game code and changes label in the scene,
     * so players can see game code.
     * @param gameCode game code of te selected game
     */
    private void setGameCode(String gameCode) {
        gameCodeLabel.setText("Game code: " + gameCode);
    }

    /**
     * Called on user pressing 'Back' button, sends user to Multiplayer.
     */
    @FXML
    private void onBackButton() {
        WaitingRoomCommunication.leaveGame(currentGameID, username);
        pollingThread.cancel();
        currentGameID = "";
        myFxml.showScene(MultiplayerCtrl.class);
    }

    /**
     * Called on user pressing 'Start' button, sends user to GamePage.
     */
    @FXML
    private void onStartButton() {
        WaitingRoomCommunication.startGame(currentGameID);
        System.out.println("gameID: " + currentGameID);
    }
    
}
