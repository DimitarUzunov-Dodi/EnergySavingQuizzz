package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Activity;
import commons.User;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.ActivityRepository;
import server.service.GameService;

public class UserControllerTest {
    private UserController controller;
    private GameService gameService;
    private ActivityRepository testActivityRepository;
    private String username = "tester";
    private String gameCode = "000000";

    /**
     * Setup function for before test.
     */
    @BeforeEach
    public void setup() {
        testActivityRepository = new TestActivityRepository();
        for (int i = 0; i < 60; i++) {
            testActivityRepository.save(
                    new Activity(i + 1, "Text" + (i + 1), i + 1, "Source" + (i + 1), -1));
        }

        gameService = new GameService(testActivityRepository, new Random());
        controller = new UserController(gameService);
    }

    @Test
    public void noGameJoinTest() {
        ResponseEntity<String> r = controller.joinGame(gameCode, username);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    public void gameJoinWithSameUsernameTest() {
        gameCode = gameService.createGame();
        ResponseEntity<String> r = controller.joinGame(gameCode, username);
        r = controller.joinGame(gameCode, username);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    public void gameJoinClosedRoomTest() {
        gameCode = gameService.createGame();
        gameService.closeRoom(gameCode);
        ResponseEntity<String> r = controller.joinGame(gameCode, username);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, r.getStatusCode());
    }

    @Test
    public void gameJoinSuccessTest() {
        gameCode = gameService.createGame();
        ResponseEntity<String> r = controller.joinGame(gameCode, username);
        assertEquals(HttpStatus.OK, r.getStatusCode());
        List<User> userList = gameService.getUsers(gameCode);
        assertEquals(1, userList.size());
    }

    @Test
    public void noGameLeaveTest() {
        ResponseEntity<String> r = controller.leaveGame(gameCode, username);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    public void gameLeaveWithoutSuchNameTest() {
        gameCode = gameService.createGame();
        ResponseEntity<String> r = controller.leaveGame(gameCode, username);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    public void leaveGameSuccess() {
        gameCode = gameService.createGame();
        ResponseEntity<String> r = controller.joinGame(gameCode, username);
        assertEquals(HttpStatus.OK, r.getStatusCode());
        r = controller.leaveGame(gameCode, username);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

    @Test
    public void noGameStartTEst() {
        ResponseEntity<String> r = controller.startGame(gameCode);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    public void startGameSuccessTest() {
        gameCode = gameService.createGame();
        ResponseEntity<String> r = controller.startGame(gameCode);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

    @Test
    public void noGameTestIfStartedTest() {
        ResponseEntity<String> r = controller.testIfStarted(gameCode);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    public void gameStartedTestIfStartedTest() {
        gameCode = gameService.createGame();
        controller.startGame(gameCode);
        ResponseEntity<String> r = controller.testIfStarted(gameCode);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, r.getStatusCode());
    }

    @Test
    public void gameNotStartedTestIfStartedTest() {
        gameCode = gameService.createGame();
        ResponseEntity<String> r = controller.testIfStarted(gameCode);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }
}
