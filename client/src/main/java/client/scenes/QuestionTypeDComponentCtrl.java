package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import client.utils.StyleUtils;
import client.utils.UserAlert;
import com.google.inject.Inject;
import commons.QuestionTypeD;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class QuestionTypeDComponentCtrl extends SceneController {

    private QuestionTypeD activeQuestion;

    @FXML
    private StackPane questionTypeDPane;
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
    private TextField textField;
    @FXML
    private Label correctAnswerLabel;
    @FXML
    private Button submitButton;

    private Long answerGiven;

    /**
     * Basic constructor.
     *
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected QuestionTypeDComponentCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        myFxml.get(GameScreenCtrl.class).showQuestion(questionTypeDPane);

        answerGiven = null;

        submitButton.setStyle(StyleUtils.DEFAULT_BUTTON_STYLE);
        correctAnswerLabel.setVisible(false);

        questionText.setText(activeQuestion.displayText());
        activityImage.setImage(ActivityImageCommunication.getImageFromId(
                activeQuestion.getActivity().getImageId()
        ));
        activityText.setText(activeQuestion.getActivity().getActivityText());

        textField.clear();
    }

    public void loadComponent(QuestionTypeD activeQuestion) {
        this.activeQuestion = activeQuestion;
        show();
    }

    /**
     * Method for submitting the answer to QuestionTypeD.
     */
    public void submit() {
        if (answerGiven == null) {
            try {
                answerGiven = Long.parseLong(textField.getText());
                myFxml.get(GameScreenCtrl.class).sendAnswer(answerGiven);

                submitButton.setStyle(StyleUtils.YELLOW_BUTTON_STYLE);
            } catch (NumberFormatException e) {
                UserAlert.userAlert("ERROR", "Wrong input", "Cannot parse the input");
            }
        }

    }

    /**
     * Shows correct answer.
     * @param correctAnswer - correct answer from the server
     */
    public void showCorrectAnswer(long correctAnswer) {
        submitButton.setStyle(StyleUtils.DEFAULT_BUTTON_STYLE);

        correctAnswerLabel.setVisible(true);
        correctAnswerLabel.setText("Correct answer: " + correctAnswer);
    }

}
