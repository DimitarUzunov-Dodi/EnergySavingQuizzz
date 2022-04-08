package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreRecordTest {
    private  ScoreRecord record1;


    /**
     * Setup for tests.
     */
    @BeforeEach
    public void setup() {
        record1 = new ScoreRecord("Dodi",100);
    }

    @Test
    void setScore() {
        record1.setScore(200);
        assertEquals(record1.getScore(),200);
    }

    @Test
    void getScore() {
        assertEquals(record1.getScore(),100);
    }

    @Test
    void testEquals() {
        ScoreRecord record2 = new ScoreRecord("Dodi",100);
        assertEquals(record2,record1);
    }

    @Test
    void testHashCode() {
        ScoreRecord record2 = new ScoreRecord("Dodi",100);
        var a = record1.hashCode();
        assertEquals(a,record2.hashCode());

    }

}