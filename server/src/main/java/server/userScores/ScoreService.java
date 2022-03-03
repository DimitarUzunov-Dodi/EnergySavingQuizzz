package server.userScores;

import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    private final ScoreRecordRepository ScoreRecordRepository;

    public ScoreService(server.userScores.ScoreRecordRepository scoreRecordRepository) {
        ScoreRecordRepository = scoreRecordRepository;
    }

    /**
     * adds the score to the user in the database
     * @param nickname
     * @param score
     */
    void addScore(String nickname, int score) {

        var existingRecord = ScoreRecordRepository.findById(nickname).get();
        existingRecord.setScore(existingRecord.getScore() + score);
        ScoreRecordRepository.save(existingRecord);
    }


}
