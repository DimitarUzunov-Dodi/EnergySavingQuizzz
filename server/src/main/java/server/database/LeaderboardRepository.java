package server.database;

import org.springframework.data.jpa.repository.JpaRepository;

import commons.LeaderboardEntry;

public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {
    // TODO: get data from DB
}