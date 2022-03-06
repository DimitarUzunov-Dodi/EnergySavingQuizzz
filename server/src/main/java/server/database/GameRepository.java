package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.Game;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, String> {

    Optional<Game> findByGameCode(String gameCode);

}