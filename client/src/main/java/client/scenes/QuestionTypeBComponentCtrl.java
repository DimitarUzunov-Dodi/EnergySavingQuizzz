package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import client.utils.StyleUtils;
import com.google.inject.Inject;
import commons.QuestionTypeB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class QuestionTypeBComponentCtrl extends SceneController {

    private QuestionTypeB activeQuestion;
    private List<Text> answers;

    @FXML
    private StackPane questionTypeBPane;
    @FXML
    private Text questionText;
    @FXML
    private Text activityText;
    @FXML
    private Text answer1;
    @FXML
    private Text answer2;
    @FXML
    private Text answer3;
    @FXML
    private ImageView activityImage;

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;

    private Long answerGiven;

    /**
     * Basic constructor.
     *
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected QuestionTypeBComponentCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        myFxml.get(GameScreenCtrl.class).showQuestion(questionTypeBPane);

        answerGiven = null;

        resetButtons();

        questionText.setText(activeQuestion.displayText());
        activityImage.setImage(ActivityImageCommunication.getImageFromId(
                activeQuestion.getActivity().getImageId()
        ));
        activityText.setText(activeQuestion.getActivity().getActivityText());

        // Delegated functionality to set up answers to another method just for code readability.
        setAnswers();

    }

    public void loadComponent(QuestionTypeB activeQuestion) {
        this.activeQuestion = activeQuestion;
        show();
    }

    private void setAnswers() {
        answers = new ArrayList<>(
                Arrays.asList(answer1, answer2, answer3));

        Collections.shuffle(answers);
        answers.get(0).setText(
                String.valueOf(activeQuestion.getActivity().getValue())
        );
        answers.get(1).setText(
                String.valueOf(activeQuestion.getAnswer2())
        );
        answers.get(2).setText(
                String.valueOf(activeQuestion.getAnswer3())
        );
    }

    /**
     * method to call when answer A is pressed.
     */
    public void answerAPressed() {
        if (answerGiven == null) {
            answerGiven = Long.parseLong(answer1.getText());
            myFxml.get(GameScreenCtrl.class).sendAnswer(answerGiven);

            button1.setStyle(StyleUtils.YELLOW_BUTTON_STYLE);
        }
    }

    /**
     * method to call when answer B is pressed.
     */
    public void answerBPressed() {
        if (answerGiven == null) {
            answerGiven = Long.parseLong(answer2.getText());
            myFxml.get(GameScreenCtrl.class).sendAnswer(answerGiven);

            button2.setStyle(StyleUtils.YELLOW_BUTTON_STYLE);
        }
    }

    /**
     * method to call when answer C is pressed.
     */
    public void answerCPressed() {
        if (answerGiven == null) {
            answerGiven = Long.parseLong(answer3.getText());
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
        System.out.println(answers.size());
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
        if (answer == Long.parseLong(answer1.getText())) {
            return button1;
        } else if (answer == Long.parseLong(answer2.getText())) {
            return button2;
        } else if (answer == Long.parseLong(answer3.getText())) {
            return button3;
        }

        return null;
    }

}
