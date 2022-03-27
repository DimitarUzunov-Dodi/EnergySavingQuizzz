package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.QuestionTypeB;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class QuestionTypeBComponentCtrl extends SceneController {

    private QuestionTypeB activeQuestion;
    private ArrayList<Button> buttonList = new ArrayList<>();

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

    }

}
