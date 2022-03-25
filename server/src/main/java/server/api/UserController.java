package server.api;

import commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.GameService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public Map<Object, Pair<Consumer<User>, String>> listeners = new HashMap<>();

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
                    .badRequest()
                    .body("No game found with this game code!");
        } else if (gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .badRequest()
                    .body("Username already in use in this game!");
        } else {
            gameService.joinGame(gameCode, username);
            listeners.forEach((k, l) -> {
                if(l.getSecond().equals(gameCode))
                    l.getFirst().accept(new User(username));
            });
            return ResponseEntity.ok()
                    .build();
        }
    }

    @GetMapping("/updates/{gameCode}")
    public DeferredResult<ResponseEntity<User>> getUserListUpdates(@PathVariable String gameCode) {
        System.out.println("Triggered");
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<User>> result = new DeferredResult<ResponseEntity<User>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, Pair.of(
                u -> {
                    result.setResult(ResponseEntity.ok(u));
                }, gameCode));
        result.onCompletion(() -> {
            listeners.remove(key);
        });

        return result;
    }

}
