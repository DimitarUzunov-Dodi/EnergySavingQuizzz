package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class LeaderboardEntry {

    @Id
    public String username;
    public Integer gamesPlayed;
    public Integer score;

    @SuppressWarnings("unused")
    private LeaderboardEntry() {
        // for object mappers
    }

    public LeaderboardEntry(String username, int gamesPlayed, int score) {
        this.username = username;
        this.gamesPlayed = gamesPlayed;
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
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
