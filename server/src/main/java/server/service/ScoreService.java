package server.service;

import commons.ScoreRecord;
import java.util.Optional;
import org.springframework.stereotype.Service;
import server.database.ScoreRecordRepository;

@Service
public class ScoreService {

    private final ScoreRecordRepository scoreRecordRepository;

    public ScoreService(ScoreRecordRepository scoreRecordRepository) {
        this.scoreRecordRepository = scoreRecordRepository;
    }

    /**
     * adds the score to the user in the database.
     *
     * @param nickname The name of the user
     * @param score the score of the user
     */
    void addScore(String nickname, int score) {

        var existingRecord = scoreRecordRepository.findById(nickname);
        if (!existingRecord.isPresent()) {
            existingRecord = Optional.of(new ScoreRecord(nickname, 0));
        }
        var finalScoreRecord = existingRecord.get();

        finalScoreRecord.setScore(finalScoreRecord.getScore() + score);
        scoreRecordRepository.save(finalScoreRecord);

    }


}
