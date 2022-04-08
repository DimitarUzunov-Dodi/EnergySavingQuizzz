package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import commons.Activity;
import commons.Game;
import commons.Question;
import commons.QuestionTypeA;
import commons.User;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.database.ActivityRepository;


public class GameServiceTest {

    private transient ActivityRepository activityRepository =
            Mockito.mock(ActivityRepository.class);
    private transient Random random =
            Mockito.mock(Random.class);
    private final transient GameService gameService =
            new GameService(activityRepository, random);

    private transient Activity activity;
    private transient Question question;
    private transient String activityText = "Test";
    private transient long imageId = 2;
    private transient long value = 23;
    private transient String source = "source";
    private transient Game game;

    @BeforeEach
    public void setup() {
        activity = new Activity(1L, activityText, value, source, imageId);
        game = new Game();
    }

    @Test
    public void createGameTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));
        when(activityRepository.getThreeRandom()).thenReturn(List.of(activity, activity, activity));
        when(activityRepository.getOneRelated(activity.getValue()))
                .thenReturn(Optional.of(activity));


        assertEquals("111111", gameService.createGame());
    }

    @Test
    public void createQuestionTypeATest() {
        when(random.nextInt(4)).thenReturn(0);
        when(activityRepository.getThreeRandom()).thenReturn(List.of(activity, activity, activity));

        assertNotNull(gameService.createQuestions());
    }

    @Test
    public void createQuestionTypeBTest() {
        when(random.nextInt(4)).thenReturn(1);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        assertNotNull(gameService.createQuestions());
    }

    @Test
    public void createQuestionTypeCTest() {
        when(random.nextInt(4)).thenReturn(2);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));
        when(activityRepository.getOneRelated(activity.getValue()))
                .thenReturn(Optional.of(activity));

        assertNotNull(gameService.createQuestions());
    }

    @Test
    public void createQuestionTypeDTest() {
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        assertNotNull(gameService.createQuestions());
    }

    @Test
    public void createQuestionFailTest() {
        when(random.nextInt(4)).thenReturn(6);

        assertThrows(IllegalStateException.class, () ->
                gameService.createQuestions());
    }

    @Test
    public void doesGameExistTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        gameService.createGame();
        assertTrue(gameService.doesGameExist("111111"));
    }

    @Test
    public void getQuestionTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        gameService.createGame();
        assertNotNull(gameService.getQuestion("111111", 0));
    }

    @Test
    public void getGameTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        gameService.createGame();
        assertNotNull(gameService.getGame("111111"));
    }

    @Test
    public void isUsernamePresentTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        gameService.createGame();
        gameService.getGame("111111").addUser(new User("alex"));

        assertTrue(gameService.isUsernamePresent("111111", "alex"));
    }

    @Test
    public void leaveGameTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        gameService.createGame();

        gameService.getGame("111111").addUser(new User("alex"));

        gameService.leaveGame("111111", "alex");

        assertNull(gameService.getGame("111111"));
    }

    @Test
    public void getUsersTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        User user = new User("alex");
        gameService.createGame();
        gameService.getGame("111111").addUser(user);

        assertEquals(List.of(user), gameService.getUsers("111111"));
    }

    @Test
    public void getUserByUsername() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        User user = new User("alex");
        gameService.createGame();
        gameService.getGame("111111").addUser(user);

        assertEquals(user,
                gameService.getUserByUsername("111111", user.getUsername()));
    }

    @Test
    public void getUserByUsername2Test() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity));

        User user = new User("alex");
        gameService.createGame();
        gameService.getGame("111111").addUser(user);

        assertNull(gameService.getUserByUsername("111111",
                "not alex"));
    }


    @Test
    public void setCurrentPublicGameTest() {
        gameService.setCurrentPublicGame("111111");
        assertEquals("111111", gameService.getCurrentPublicGame());
    }

    @Test
    public void correctAnswerTypeATest() {
        Activity activity1 = new Activity(1L, activityText, value, source, imageId);
        Activity activity2 = new Activity(1L, activityText, value + 1, source, imageId);
        Activity activity3 = new Activity(1L, activityText, value + 2, source, imageId);

        QuestionTypeA q = new QuestionTypeA(activity1, activity2, activity3);
        assertEquals(activity3.getValue(), gameService.correctAnswerQuestionTypeA(q));
    }

    @Test
    public void processAnswerTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(0);
        Activity activity1 = new Activity(1L, activityText, value, source, imageId);
        Activity activity2 = new Activity(1L, activityText, value + 1, source, imageId);
        Activity activity3 = new Activity(1L, activityText, value + 2, source, imageId);
        when(activityRepository.getThreeRandom())
                .thenReturn(List.of(activity1, activity2, activity3));

        User user = new User("alex");
        gameService.createGame();
        gameService.getGame("111111").addUser(user);

        assertEquals(100, gameService.processAnswer("111111", "alex", 0, 25L, 100L));

    }

    @Test
    public void correctAnswerTest() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(0);
        Activity activity1 = new Activity(1L, activityText, value, source, imageId);
        Activity activity2 = new Activity(1L, activityText,
                value + 1, source, imageId);
        Activity activity3 = new Activity(1L, activityText,
                value + 2, source, imageId);
        when(activityRepository.getThreeRandom())
                .thenReturn(List.of(activity1, activity2, activity3));
        gameService.createGame();

        assertEquals(25L, gameService.getCorrectAnswer("111111", 0));
    }

    @Test
    public void processAnswerTest2() {
        when(random.nextInt(999999)).thenReturn(111111);
        when(random.nextInt(4)).thenReturn(3);
        Activity activity1 = new Activity(1L, activityText, value, source, imageId);
        when(activityRepository.getOneRandom()).thenReturn(Optional.of(activity1));

        User user = new User("alex");
        gameService.createGame();
        gameService.getGame("111111").addUser(user);

        assertEquals(100, gameService.processAnswer("111111", "alex", 0, 26L, 100L));

    }
}
