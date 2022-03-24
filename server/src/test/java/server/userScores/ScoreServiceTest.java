package server.userScores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.database.ScoreRecordRepository;

@SpringBootTest
class ScoreServiceTest {
    @Autowired
    private ScoreRecordRepository repository;

/*
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
*/
}