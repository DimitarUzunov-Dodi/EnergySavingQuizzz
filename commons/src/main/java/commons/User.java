package commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_score")
public class User {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "game_code")
    private String gameCode;
    @Column(name = "score")
    private int score;

    public User() {

    }

    /**
     *  Constructor.
     *
     * @param gameCode unique game code
     * @param username unique username
     */
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
