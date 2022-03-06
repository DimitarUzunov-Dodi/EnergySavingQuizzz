package server.userScores;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreServiceTest {
    @Autowired
    private ScoreRecordRepository repository;


    @Test
    @Transactional
    void addScoreTest1() {
        var scoreService = new ScoreService(repository);
        scoreService.addScore("Bob", 20);
        var scoreRecord = repository.findById("Bob");
        assertTrue(scoreRecord.isPresent());
    }

    @Test
    @Transactional
    void addScoreTest2() {
        var scoreService = new ScoreService(repository);
        scoreService.addScore("Bob", 20);
        var scoreRecord = repository.findById("Bob");
        assertTrue(scoreRecord.get().getScore() == 20);
    }

}