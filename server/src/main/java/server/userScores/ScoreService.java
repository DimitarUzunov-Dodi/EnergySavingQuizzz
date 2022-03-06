package server.userScores;

import commons.ScoreRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreService {

    private final ScoreRecordRepository scoreRecordRepository;

    public ScoreService(server.userScores.ScoreRecordRepository scoreRecordRepository) {
        this.scoreRecordRepository = scoreRecordRepository;
    }

    /**
     * adds the score to the user in the database
     * @param nickname
     * @param score
     */
    void addScore(String nickname, int score) {

        var existingRecord = scoreRecordRepository.findById(nickname);
        if(!existingRecord.isPresent()){
           existingRecord = Optional.of(new ScoreRecord(nickname,0));
        }
        var finalScoreRecord =existingRecord.get();

        finalScoreRecord.setScore(finalScoreRecord.getScore() + score);
        scoreRecordRepository.save(finalScoreRecord);

    }


}
