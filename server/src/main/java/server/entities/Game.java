package server.entities;

import javax.persistence.*;

@Entity
@Table(name = "game")
//@Data
public class Game {

    @Id
    @Column(name = "game_code")
    private String gameCode;

    public Game() {

    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }
}
