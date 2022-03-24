package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import static client.utils.FileUtils.readNickname;
import static client.utils.UserAlert.userAlert;

public class LoadingCtrl extends SceneController {
    private static final int TIME_TO_NEXT_ROUND = 3;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label username;

    @FXML
    private Label score;

    @Inject
    private LoadingCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    @Override
    public void show() {
        progressBar.setProgress(0);

        loadUsername();
        loadScore();

        countDown();
    }

    /**
     * Start a time for TIME_TO_NEXT_ROUND seconds and bind to progressbar
     */
    private void countDown() {
        final Service<Integer> countDownThread = new Service<>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() {
                        int i;
                        for(i = 0; i < TIME_TO_NEXT_ROUND * 100; i++) {
                            updateProgress(i, TIME_TO_NEXT_ROUND * 100);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return i;
                    }
                };
            }
        };

        // Next Round Transition
        countDownThread.setOnSucceeded(event -> {
            userAlert("INFO", "Next Round", "Next Round Start");
        });

        // bind thread to progress bar and start it
        progressBar.progressProperty().bind(countDownThread.progressProperty());
        countDownThread.start();

        // TODO "uses unchecked or unsafe operations"

    }

    /**
     * Load username to the label
     */
    private void loadUsername() {
        String nickname = readNickname();
        username.setText(nickname);
    }

    /**
     * Load score to the label
     */
    private void loadScore() {
        // TODO
    }
}
