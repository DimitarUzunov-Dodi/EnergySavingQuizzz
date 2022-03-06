package server.entities;

import javax.persistence.Id;

public class User {

    @Id
    private String username;
    private String gameCode;
    private int score;

    public User() {

    }

    public User(String gameCode, String username) {
        this.gameCode = gameCode;
        this.username = username;
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
