package server.api;

import commons.User;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.GameService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public Map<Object, Pair<Consumer<Pair<User, String>>, String>> listeners = new HashMap<>();

    @Autowired
    private final GameService gameService;

    /**
     * Constructor for the UserController class.
     * @param gameService Game Service
     */
    public UserController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Adds an user to the user list of a game.
     *
     * @param gameCode gamecode
     * @param username username
     * @return ResponseEntity
     */
    @PutMapping("/join/{gameCode}/{username}")
    public ResponseEntity<String> joinGame(@PathVariable String gameCode,
                                       @PathVariable String username) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No game found with this game code!");
        } else if (gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already in use in this game!");
        } else if (gameService.isAllowedToJoin(gameCode)) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Room is now closed!");
        } else {
            gameService.joinGame(gameCode, username);
            listeners.forEach((k, l) -> {
                if (l.getSecond().equals(gameCode)) {
                    l.getFirst().accept(Pair.of(new User(username), "ADD"));
                }
            });
            return ResponseEntity.ok()
                    .build();
        }
    }

    /**
     * Removes a user from the user list of a game.
     *
     * @param gameCode gamecode
     * @param username username
     * @return ResponseEntity
     */
    @DeleteMapping("/leave/{gameCode}/{username}")
    public ResponseEntity<String> leaveGame(@PathVariable String gameCode,
                                           @PathVariable String username) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .badRequest()
                    .body("No game found with this game code!");
        } else if (!gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .badRequest()
                    .body("No such user in selected game!");
        } else {
            gameService.leaveGame(gameCode, username);
            listeners.forEach((k, l) -> {
                if (l.getSecond().equals(gameCode)) {
                    l.getFirst().accept(Pair.of(new User(username), "REMOVE"));
                }
            });
            return ResponseEntity.ok()
                    .build();
        }
    }

    /**
     * GET mapping that activates long-polling for client.
     * @param gameCode code is used to know from where to get data
     * @return DeferredResultResponseEntityUser
     */
    @GetMapping("/updates/{gameCode}")
    public DeferredResult<ResponseEntity<User>> getUserListUpdates(@PathVariable String gameCode) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<User>> result = new DeferredResult<ResponseEntity<User>>(
                5000L, noContent);

        var key = new Object();
        listeners.put(key, Pair.of(p -> {
            if (p.getSecond().equals("ADD")) {
                result.setResult(ResponseEntity.ok(p.getFirst()));
            } else if ((p.getSecond().equals("REMOVE"))) {
                result.setResult(ResponseEntity.status(HttpStatus.GONE).body(p.getFirst()));
            } else if ((p.getSecond().equals("START"))) {
                result.setResult(ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
            }
        }, gameCode));
        result.onCompletion(() -> {
            listeners.remove(key);
        });

        return result;
    }

    /**
     * POST mapping called when one of the users initiates start of the game.
     *
     * @param gameCode gamecode
     * @return ResponseEntity
     */
    @PostMapping("/start/{gameCode}")
    public ResponseEntity<String> startGame(@PathVariable String gameCode) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No game found with this game code!");
        } else {
            gameService.closeRoom(gameCode);
            listeners.forEach((k, l) -> {
                if (l.getSecond().equals(gameCode)) {
                    l.getFirst().accept(Pair.of(new User(""), "START"));
                }
            });
            return ResponseEntity.ok()
                    .build();
        }
    }
}
