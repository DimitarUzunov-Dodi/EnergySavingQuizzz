package commons;

import static javax.persistence.GenerationType.*;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class ServerLeaderboardEntry {

    @Id
    @GeneratedValue(strategy = AUTO)
    public long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    public String username;
    public Integer gamesPlayed;
    public Integer score;

    @SuppressWarnings("unused")
    private ServerLeaderboardEntry() {
        // for object mappers
    }

    public ServerLeaderboardEntry(String username, int gamesPlayed, int score) {
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
