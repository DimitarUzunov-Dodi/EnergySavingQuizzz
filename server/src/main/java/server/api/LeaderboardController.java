package server.api;

import commons.ServerLeaderboardEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.LeaderboardRepository;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardRepository repo;

    /**
     * Constructor for leaderboard controller.
     * @param repo Server Leaderboard Repository
     */
    public LeaderboardController(LeaderboardRepository repo) {
        this.repo = repo;
    }

    /**
     * Get all leaderboard entries from the server.
     * @return list of all entries
     */
    @GetMapping("/")
    public List<ServerLeaderboardEntry> getServerLeaderboard() {
        return repo.findAll(Sort.by("score"));
    }

    /**
     * Get a specific entry from the server leaderboard.
     * @param username entry username
     * @return HTTP 200 with a leaderboard entry if ok, error code otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<ServerLeaderboardEntry>
        getEntryByUsername(@PathVariable("username") String username) {
        if (username == null || username.equals("")) {
            return ResponseEntity.badRequest().build();
        }

        var example = Example.of(new ServerLeaderboardEntry(username, null, null));
        Optional<ServerLeaderboardEntry> entry = repo.findOne(example);

        if (entry.isPresent()) {
            return ResponseEntity.ok(entry.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add/Update an entry.
     * @param username entry username
     * @param delta object containing the delta values to be applied to the respective db record
     * @return HTTP 201 if all updated existing, 202 if created new entry, error code otherwise
     */
    @PutMapping("/{username}")
    public ResponseEntity<ServerLeaderboardEntry> updateEntry(@PathVariable String username,
                    @RequestBody ServerLeaderboardEntry delta) {
        if (delta == null) {
            return ResponseEntity.badRequest().build();
        }

        var example = Example.of(new ServerLeaderboardEntry(username, null, null));
        var found = repo.findOne(example);
        if (found.isPresent()) {
            var entry = found.get();
            entry.score += delta.score;
            entry.gamesPlayed += delta.gamesPlayed;
            return ResponseEntity.accepted().body(repo.save(found.get()));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(delta));
        }
    }
}
