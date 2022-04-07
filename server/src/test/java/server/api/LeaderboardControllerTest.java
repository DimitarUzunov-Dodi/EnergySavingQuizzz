package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.ServerLeaderboardEntry;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.LeaderboardRepository;


public class LeaderboardControllerTest {
    private LeaderboardController controller;
    private LeaderboardRepository repository;

    @BeforeEach
    public void setup() {
        repository = new TestLeaderboardRepository();
        controller = new LeaderboardController(repository);
    }

    @Test
    public void emptyRepoTest() {
        List<ServerLeaderboardEntry> r = controller.getServerLeaderboard();
        assertEquals(0, r.size());
    }

    @Test
    public void addOneEntryRepoTest() {
        List<ServerLeaderboardEntry> r = controller.getServerLeaderboard();
        assertEquals(0, r.size());
        repository.save(new ServerLeaderboardEntry("tester", 1, 1337));
        r = controller.getServerLeaderboard();
        assertEquals(1, r.size());
    }

    @Test
    public void getEntryByNullTest() {
        ResponseEntity<ServerLeaderboardEntry> r = controller.getEntryByUsername(null);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    public void getEntryByEmptyTest() {
        ResponseEntity<ServerLeaderboardEntry> r = controller.getEntryByUsername("");
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }

    @Test
    public void getEntryNotExistingTest() {
        ResponseEntity<ServerLeaderboardEntry> r = controller.getEntryByUsername("tester");
        assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    }

    @Test
    public void getEntryExistingTest() {
        repository.save(new ServerLeaderboardEntry("tester", 1, 1337));
        ResponseEntity<ServerLeaderboardEntry> r = controller.getEntryByUsername("tester");
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }

    @Test
    public void updateNotExistingEntryTest() {
        ResponseEntity<ServerLeaderboardEntry> r = controller.updateEntry("tester",
                new ServerLeaderboardEntry("tester", 2, 1337 * 2));
        assertEquals(HttpStatus.CREATED, r.getStatusCode());
    }

    @Test
    public void updateExistingEntryTest() {
        repository.save(new ServerLeaderboardEntry("tester", 1, 1337));
        ResponseEntity<ServerLeaderboardEntry> r = controller.updateEntry("tester",
                new ServerLeaderboardEntry("tester", 2, 1337 * 2));
        assertEquals(HttpStatus.ACCEPTED, r.getStatusCode());
    }

    @Test
    public void deltaNullUpdateTest() {
        repository.save(new ServerLeaderboardEntry("tester", 1, 1337));
        ResponseEntity<ServerLeaderboardEntry> r = controller.updateEntry("tester", null);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    }
}
