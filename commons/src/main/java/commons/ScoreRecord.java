package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A class for using the Score of a given user.
 */
@Entity
public class ScoreRecord {
    @Id
    public String nickname;

    public int score;

    public ScoreRecord(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public ScoreRecord() {
    }

    /**
     * sets the user's score to the given value.
     *
     * @param score int
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * gets the user's score.
     *
     * @return Returns the score.
     */
    public int getScore() {
        return score;
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
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
