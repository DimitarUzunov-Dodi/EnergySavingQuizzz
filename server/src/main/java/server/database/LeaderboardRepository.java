package server.database;

import commons.ServerLeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<ServerLeaderboardEntry, Long> {
    @Query(
        value = "SELECT * from server_leaderboard_entry ORDER BY score DESC",
        nativeQuery = true
    )
    Optional<ServerLeaderboardEntry> getAll();
}