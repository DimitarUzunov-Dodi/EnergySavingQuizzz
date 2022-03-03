package server.userScores;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A class for using the Score of a given user
 */
@Entity
public class ScoreRecord {
    @Id
    public String nickname;
    public int score;


    /**
     * sets the user's score to the given value
     *
     * @param score int
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * gets the user's score
     *
     * @return
     */
    public int getScore() {
        return score;
    }
}
