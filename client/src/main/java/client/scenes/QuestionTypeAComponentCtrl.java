package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import client.utils.StyleUtils;
import com.google.inject.Inject;
import commons.QuestionTypeA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class QuestionTypeAComponentCtrl extends SceneController {

    private QuestionTypeA activeQuestion;

    private Long answerGiven;

    @FXML
    private StackPane questionTypeAPane;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private Text activityText1;
    @FXML
    private Text activityText2;
    @FXML
    private Text activityText3;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Text questionText;

    /**
     * Basic constructor.
     *
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected QuestionTypeAComponentCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        myFxml.get(GameScreenCtrl.class).showQuestion(questionTypeAPane);
        answerGiven = null;

        resetButtons();

        questionText.setText(activeQuestion.displayText());
        activityText1.setText(activeQuestion.getActivity1().getActivityText());
        activityText2.setText(activeQuestion.getActivity2().getActivityText());
        activityText3.setText(activeQuestion.getActivity3().getActivityText());

        image1.setImage(ActivityImageCommunication.getImageFromId(
                activeQuestion.getActivity1().getImageId()));
        image2.setImage(ActivityImageCommunication.getImageFromId(
                activeQuestion.getActivity2().getImageId()));
        image3.setImage(ActivityImageCommunication.getImageFromId(
                activeQuestion.getActivity3().getImageId()));

    }


    public void loadComponent(QuestionTypeA activeQuestion) {
        this.activeQuestion = activeQuestion;
        show();
    }

    /**
     * method to call when answer A is pressed.
     */
    public void answerAPressed() {
        if (answerGiven == null) {
            answerGiven = activeQuestion.getActivity1().getValue();
            myFxml.get(GameScreenCtrl.class).sendAnswer(answerGiven);

            button1.setStyle(StyleUtils.YELLOW_BUTTON_STYLE);
        }
    }

    /**
     * method to call when answer B is pressed.
     */
    public void answerBPressed() {
        if (answerGiven == null) {
            answerGiven = activeQuestion.getActivity2().getValue();
            myFxml.get(GameScreenCtrl.class).sendAnswer(answerGiven);

            button2.setStyle(StyleUtils.YELLOW_BUTTON_STYLE);
        }
    }

    /**
     * method to call when answer C is pressed.
     */
    public void answerCPressed() {
        if (answerGiven == null) {
            answerGiven = activeQuestion.getActivity3().getValue();
            myFxml.get(GameScreenCtrl.class).sendAnswer(answerGiven);

            button3.setStyle(StyleUtils.YELLOW_BUTTON_STYLE);
        }

    }

    /**
     * Shows correct answer.
     * @param correctAnswer - correct answer from the server
     */
    public void showCorrectAnswer(long correctAnswer) {
        Button correctButton = findButtonByAnswer(correctAnswer);
        if (correctButton != null) {
            correctButton.setStyle(StyleUtils.GREEN_BUTTON_STYLE);
        }

        if (answerGiven != null && answerGiven != correctAnswer) {
            Button selectedButton = findButtonByAnswer(answerGiven);
            if (selectedButton != null) {
                selectedButton.setStyle(StyleUtils.RED_BUTTON_STYLE);
            }
        }
    }

    /**
     * removeIncorrectAnswer for joker 50/50.
     * removes 1 incorrect answer out of 3 answers.
     * @param correctAnswer - correct answer from the server
     */
    public void removeIncorrectAnswer(long correctAnswer) {
        List<Button> answers = new ArrayList<>(Arrays.asList(
                button1, button2, button3
        ));
        Button correctButton = findButtonByAnswer(correctAnswer);
        answers.remove(correctButton);
        Collections.shuffle(answers);
        answers.get(0).setVisible(false);
        answers.get(0).setDisable(true);
    }

    private void resetButtons() {
        button1.setStyle(StyleUtils.DEFAULT_BUTTON_STYLE);
        button1.setVisible(true);
        button1.setDisable(false);
        button2.setStyle(StyleUtils.DEFAULT_BUTTON_STYLE);
        button2.setVisible(true);
        button2.setDisable(false);
        button3.setStyle(StyleUtils.DEFAULT_BUTTON_STYLE);
        button3.setVisible(true);
        button3.setDisable(false);
    }


    private Button findButtonByAnswer(long answer) {
        if (answer == activeQuestion.getActivity1().getValue()) {
            return button1;
        } else if (answer == activeQuestion.getActivity2().getValue()) {
            return button2;
        } else if (answer == activeQuestion.getActivity3().getValue()) {
            return button3;
        }

        return null;
    }

}
