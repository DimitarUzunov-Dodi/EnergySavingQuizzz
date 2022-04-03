package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GameTest {

    private transient Game game1;
    private transient User user1;
    private transient User user2;
    private transient List<User> userList;
    private transient QuestionTypeD question1;
    private transient QuestionTypeD question2;
    private transient List<Question> questionList;
    private transient Activity activity;
    private transient String gameCode;

    /**
     * Setup for tests.
     */
    @BeforeEach
    public void setup() {
        user1 = new User("Alex");
        user2 = new User("Not Alex");

        userList = new ArrayList<User>(List.of(user1, user2));

        question1 = new QuestionTypeD(activity);
        question2 = new QuestionTypeD(activity);

        questionList = new ArrayList<Question>(List.of(question1, question2));

        gameCode = "bruh";

        game1 = new Game();
    }

    @Test
    public void constructorTest() {
        assertNotNull(game1);
    }

    @Test
    public void setUserListTest() {
        game1.setUserList(userList);
        assertEquals(userList, game1.getUserList());
    }

    @Test
    public void addUserTest() {
        game1.addUser(user1);
        game1.addUser(user2);

        assertEquals(userList, game1.getUserList());
    }

    @Test
    public void removeUserTest() {
        game1.setUserList(userList);
        game1.removeUser(user1);
        game1.removeUser(user2);

        assertTrue(game1.getUserList().isEmpty());
    }

    @Test
    public void setGameCodeTest() {
        game1.setGameCode(gameCode);
        assertEquals(gameCode, game1.getGameCode());
    }

    @Test
    public void setActiveQuestionListTest() {
        game1.setActiveQuestionList(questionList);
        assertEquals(questionList, game1.getActiveQuestionList());
    }

    @Test
    public void setStartedTest() {
        game1.setStarted(true);
        assertTrue(game1.hasStarted());
    }
}
