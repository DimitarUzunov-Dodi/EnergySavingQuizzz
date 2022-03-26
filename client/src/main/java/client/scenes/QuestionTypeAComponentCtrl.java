package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Question;
import commons.QuestionTypeA;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class QuestionTypeAComponentCtrl extends SceneController {

    private int correctAnswer;
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
    private Button button4;

    @FXML
    private Text questionText;

    @Inject
    /**
     * Basic constructor.
     *
     * @param myFxml handled by INJECTOR
     */
    protected QuestionTypeAComponentCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        myFxml.get(GameScreenCtrl.class).showQuestion(questionTypeAPane);

        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
    }

    public void setActiveQuestion(QuestionTypeA activeQuestion) {
        this.activeQuestion = activeQuestion;
    }

    /**
     * method to call when answer A is pressed.
     */
    public void answerApressed() {
        if (correctAnswer == 0) {
            myFxml.get(GameScreenCtrl.class).awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to call when answer B is pressed.
     */
    public void answerBpressed() {
        if (correctAnswer == 1) {
            myFxml.get(GameScreenCtrl.class).awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to call when answer C is pressed.
     */
    public void answerCpressed() {
        if (correctAnswer == 2) {
            myFxml.get(GameScreenCtrl.class).awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }
}
