package client.scenes;

import com.google.inject.Inject;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import static client.utils.FileUtils.readNickname;

public class LoadingController {
    private final MainCtrl mainCtrl;
    private static int TIME_TO_NEXT_ROUND = 3;

    private ProgressBar progressBar;
    private Label username, score;

    @Inject
    public LoadingController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void init() {
        progressBar = (ProgressBar) mainCtrl.getCurrentScene().lookup("#progressBar");
        username = (Label) mainCtrl.getCurrentScene().lookup("#username");
        score = (Label) mainCtrl.getCurrentScene().lookup("#score");

        progressBar.setProgress(0);

        loadUsername();
        loadScore();
    }

    public void countDown() {
        final Service<Integer> countDownThread = new Service<>() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Integer call() throws InterruptedException {
                        int i;
                        for(i = 0; i < TIME_TO_NEXT_ROUND * 100; i++) {
                            updateProgress(i, TIME_TO_NEXT_ROUND * 100);
                            Thread.sleep(10);
                        }
                        return i;
                    }
                };
            }
        };

        progressBar.progressProperty().bind(countDownThread.progressProperty());
        countDownThread.start();


    }

    private void loadUsername() {
        String nickname = readNickname();
        username.setText(nickname);
    }

    private void loadScore() {
        // TODO
    }
}
