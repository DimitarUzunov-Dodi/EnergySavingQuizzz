package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Activity;
import commons.QuestionTypeC;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionTypeCComponentCtrl extends SceneController {

    private Activity correctActivity;
    private int correctAnswer;
    private QuestionTypeC activeQuestion;
    private ArrayList<Button> buttonList = new ArrayList<>();

    @FXML
    private StackPane questionTypeCPane;
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

    @Inject
    /**
     * Basic constructor.
     *
     * @param myFxml handled by INJECTOR
     */
    protected QuestionTypeCComponentCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        myFxml.get(GameScreenCtrl.class).showQuestion(questionTypeCPane);

        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);

        questionText.setText(String.format(activeQuestion.displayText(),
                Character.toLowerCase(activeQuestion.getDisplayActivity()
                        .getActivityText().charAt(0))
                        + activeQuestion.getDisplayActivity()
                                .getActivityText().substring(1)));

        correctActivity = activeQuestion.getActivityCorrect();

        List<Activity> activityList = new ArrayList<Activity>();
        activityList.add(correctActivity);
        activityList.add(activeQuestion.getActivity1());
        activityList.add(activeQuestion.getActivity2());

        Collections.shuffle(activityList);

        for (correctAnswer = 0; correctAnswer < activityList.size(); correctAnswer++) {
            if (activityList.get(correctAnswer).equals(correctActivity)) {
                break;
            }
        }

        activityText1.setText(activityList.get(0).getActivityText());
        activityText2.setText(activityList.get(1).getActivityText());
        activityText3.setText(activityList.get(2).getActivityText());
        image1.setImage(ActivityImageCommunication.getImageFromId(
                activityList.get(0).getImageId()));
        image2.setImage(ActivityImageCommunication.getImageFromId(
                activityList.get(1).getImageId()));
        image3.setImage(ActivityImageCommunication.getImageFromId(
                activityList.get(2).getImageId()));
        System.out.println(correctAnswer);
    }

    public void loadComponent(QuestionTypeC activeQuestion) {
        this.activeQuestion = activeQuestion;
        show();
    }

    /**
     * method to call when answer A is pressed.
     */
    public void answerAPressed() {
        if (correctAnswer == 0) {
            myFxml.get(GameScreenCtrl.class).awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to call when answer B is pressed.
     */
    public void answerBPressed() {
        if (correctAnswer == 1) {
            myFxml.get(GameScreenCtrl.class).awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to call when answer C is pressed.
     */
    public void answerCPressed() {
        if (correctAnswer == 2) {
            myFxml.get(GameScreenCtrl.class).awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

}
