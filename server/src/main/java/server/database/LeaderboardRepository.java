package server.database;

import org.springframework.data.jpa.repository.JpaRepository;

import commons.ServerLeaderboardEntry;

public interface LeaderboardRepository extends JpaRepository<ServerLeaderboardEntry, Long> {
    // TODO: get data from DB
}