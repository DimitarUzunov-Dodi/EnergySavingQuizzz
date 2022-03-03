package server.userScores;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecord, String> {

}
