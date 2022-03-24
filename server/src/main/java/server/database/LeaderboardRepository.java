package server.database;

import commons.ServerLeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<ServerLeaderboardEntry, Long> {
}