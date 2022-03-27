package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.QuestionTypeA;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class QuestionTypeAComponentCtrl extends SceneController {

    private QuestionTypeA activeQuestion;
    private ArrayList<Button> buttonList = new ArrayList<>();

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

        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);

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
        myFxml.get(GameScreenCtrl.class)
                .sendAnswer(activeQuestion.getActivity1().getValue());
    }

    /**
     * method to call when answer B is pressed.
     */
    public void answerBPressed() {
        myFxml.get(GameScreenCtrl.class)
                .sendAnswer(activeQuestion.getActivity2().getValue());
    }

    /**
     * method to call when answer C is pressed.
     */
    public void answerCPressed() {
        myFxml.get(GameScreenCtrl.class)
                .sendAnswer(activeQuestion.getActivity3().getValue());
    }
}
