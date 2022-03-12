package client.scenes;

import com.google.inject.Inject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import static client.scenes.UserAlert.userAlert;
import static client.utils.FileUtils.readNickname;

public class LoadingController {
    private final MainCtrl mainCtrl;
    private static final int TIME_TO_NEXT_ROUND = 3;

    private ProgressBar progressBar;
    private Label username, score;

    @Inject
    public LoadingController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Initialize UI components, load username and score
     */
    public void init() {
        progressBar = (ProgressBar) mainCtrl.getCurrentScene().lookup("#progressBar");
        username = (Label) mainCtrl.getCurrentScene().lookup("#username");
        score = (Label) mainCtrl.getCurrentScene().lookup("#score");

        progressBar.setProgress(0);

        loadUsername();
        loadScore();
    }

    /**
     * Start a time for TIME_TO_NEXT_ROUND seconds and bind to progressbar
     */
    public void countDown() {
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
