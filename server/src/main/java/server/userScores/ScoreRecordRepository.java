package server.userScores;

import commons.ScoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecord, String> {

}
