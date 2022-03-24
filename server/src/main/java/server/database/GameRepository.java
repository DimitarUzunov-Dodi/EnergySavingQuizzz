package server.database;

import commons.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, String> {

    Optional<Game> findByGameCode(String gameCode);

}
