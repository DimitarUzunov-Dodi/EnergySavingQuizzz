package server.database;

import commons.ServerLeaderboardEntry;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeaderboardRepository extends JpaRepository<ServerLeaderboardEntry, Long> {
    @Query(
        value = "SELECT * from server_leaderboard_entry ORDER BY score DESC",
        nativeQuery = true
    )
    Optional<ServerLeaderboardEntry> getAll();
}