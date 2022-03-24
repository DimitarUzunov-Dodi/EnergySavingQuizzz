package commons;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Game {

    @Id
    private String gameCode;
    @Transient
    private List<Question> activeQuestionList;

    public Game() {

    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }


    public List<Question> getActiveQuestionList() {
        return activeQuestionList;
    }

    public void setActiveQuestionList(List<Question> activeQuestionList) {
        this.activeQuestionList = activeQuestionList;
    }
}
