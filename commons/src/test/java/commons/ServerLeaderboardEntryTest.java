package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerLeaderboardEntryTest {
    private ServerLeaderboardEntry entry1;
    private ServerLeaderboardEntry entry2;
    private ServerLeaderboardEntry entry3;

    @BeforeEach
    void setUp() {
        entry1 = new ServerLeaderboardEntry("Dodi",3,100);
        entry2 = new ServerLeaderboardEntry("Geca",5,175);
        entry3 = new ServerLeaderboardEntry("Dodi",3,100);
    }

    @Test
    void getGamesPlayed() {
        assertEquals(entry1.getGamesPlayed(),3);
    }

    @Test
    void setGamesPlayed() {
        entry1.setGamesPlayed(5);
        assertEquals(entry1.getGamesPlayed(),5);
    }

    @Test
    void getScore() {
        assertEquals(entry1.getScore(),100);
    }

    @Test
    void setScore() {
        entry1.setScore(200);
        assertEquals(entry1.getScore(),200);
    }

    @Test
    void testEquals() {
        assertEquals(entry1,entry3);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(entry1,entry2);
    }

    @Test
    void testHashCode() {
        assertEquals(entry1.hashCode(),entry3.hashCode());
    }
}