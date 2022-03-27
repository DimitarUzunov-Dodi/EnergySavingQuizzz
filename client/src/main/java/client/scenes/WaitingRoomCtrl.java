package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.username;

import client.MyFXML;
import client.communication.GameCommunication;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
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
        WaitingRoomCommunication.registerForUserListUpdates(username, currentGameID,
                u -> {
                    Platform.runLater(() -> {
                        playerList.add(u.getUsername());
                        playersLabel.setText(playerList.size() + " players");
                    });
                },
                u -> {
                    Platform.runLater(() -> {
                        playerList.remove(u.getUsername());
                        playersLabel.setText(playerList.size() + " players");
                    });
                },
                o -> {
                    System.out.println("STARTED GAME");

                    Platform.runLater(() -> {
                        myFxml.showScene(GameScreenCtrl.class);
                    });


                }
        );
        playerList = FXCollections.observableList(
                WaitingRoomCommunication.getAllUsers(currentGameID)
                        .stream()
                        .map(
                                u -> u.getUsername())
                        .collect(Collectors.toList()));
        listView.setItems(playerList);
        playersLabel.setText(playerList.size() + " players");
        showScene();
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
        myFxml.showScene(MultiplayerCtrl.class);
    }

    /**
     * Called on user pressing 'Start' button, sends user to GamePage.
     */
    @FXML
    private void onStartButton() {
        WaitingRoomCommunication.startGame(currentGameID);
        System.out.println(currentGameID);
      //  System.out.println("snet");


        myFxml.showScene(GameScreenCtrl.class);
     //   GameCommunication.connect();
        //GameCommunication.send("/app/time/" + currentGameID, "foo");
    }
    
}
