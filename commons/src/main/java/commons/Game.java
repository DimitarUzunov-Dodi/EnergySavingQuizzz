package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    private boolean started;

    public Game() {
        userList = new ArrayList<User>();
        started = false;
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

    /**
     * Method removes from the game user with a given username.
     * @param user user object whose name will remove
     */
    public void removeUser(User user) {
        userList = userList.stream()
                .filter(u -> !u.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
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

    public boolean hasStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
