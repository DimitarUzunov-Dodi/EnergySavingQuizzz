package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByGameCodeAndUsername(String gameCode, String username);

}