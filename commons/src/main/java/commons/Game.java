package commons;

import java.util.ArrayList;
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
    @Transient
    private List<User> userList;

    public Game() {
        userList = new ArrayList<User>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user) {
        userList.add(user);
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
