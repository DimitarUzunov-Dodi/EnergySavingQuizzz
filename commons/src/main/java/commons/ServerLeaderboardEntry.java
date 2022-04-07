package commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A class for storing a server leaderboard entry.
 */
@Entity
public class ServerLeaderboardEntry {

    @Id
    @Column(name = "username")
    public String username;
    @Column(name = "games_played")
    public Integer gamesPlayed;
    @Column(name = "score")
    public Integer score;

    private ServerLeaderboardEntry() {
        // for object mappers
    }

    /**
     * Constructor.
     * @param username username
     * @param gamesPlayed nr of games played
     * @param score highest score
     */
    public ServerLeaderboardEntry(String username, Integer gamesPlayed, Integer score) {
        this.username = username;
        this.gamesPlayed = gamesPlayed;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    /**
     * gets the player's number of played matches.
     *
     * @return nr of matches played
     */
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Set the number of matches played by a player.
     * @param gamesPlayed new number of played games
     */
    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Gets the player's score.
     *
     * @return player's highscore
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Set the highscore of a player.
     * @param score new player highscore
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "<" + username + ", " + gamesPlayed + ", " + score + ">";
    }
}
