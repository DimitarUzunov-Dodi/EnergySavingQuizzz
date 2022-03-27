package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.QuestionTypeD;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class QuestionTypeDComponentCtrl extends SceneController {

    private QuestionTypeD activeQuestion;
    private ArrayList<Button> buttonList = new ArrayList<>();

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

        questionText.setText(activeQuestion.displayText());
        activityImage.setImage(ActivityImageCommunication.getImageFromId(
                activeQuestion.getActivity().getImageId()
        ));
        activityText.setText(activeQuestion.getActivity().getActivityText());

    }

    public void loadComponent(QuestionTypeD activeQuestion) {
        this.activeQuestion = activeQuestion;
        show();
    }

}
