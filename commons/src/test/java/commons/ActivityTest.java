package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActivityTest {
    public Activity a;

    @BeforeEach
    public void setup(){
        a = new Activity(1, "Test", 23, "source", 2);
    }

    @Test
    void getActivityIdTest() {
        assertEquals(1, a.getActivityId());
    }

    @Test
    void setActivityIdTest() {
        long toChange = 23;
        a.setActivityId(toChange);
        assertEquals(toChange, a.getActivityId());
    }

    @Test
    void getActivityTextTest() {
        assertEquals("Test", a.getActivityText());
    }

    @Test
    void setActivityTextTest() {
        String toChange = "New text";
        a.setActivityText(toChange);
        assertEquals(toChange, a.getActivityText());
    }

    @Test
    void getActivitySourceTest() {
        assertEquals("source", a.getSource());
    }

    @Test
    void setActivitySourceTest() {
        String toChange = "New source";
        a.setSource(toChange);
        assertEquals(toChange, a.getSource());
    }

    @Test
    void getActivityValueTest() {
        assertEquals(23, a.getValue());
    }

    @Test
    void setActivityValueTest() {
        long toChange = 46;
        a.setValue(toChange);
        assertEquals(toChange, a.getValue());
    }

    @Test
    void getActivityImageId() {
        assertEquals(2, a.getImageId());
    }

    @Test
    void setActivityImageId() {
        long toChange = 3;
        a.setImageId(toChange);
        assertEquals(toChange, a.getImageId());
    }

    @Test
    void equalsNullTest() {
        Activity b = null;
        assertNotEquals(a, b);
    }

    @Test
    void equalsIdDiffTest() {
        Activity b = new Activity(2, "Test", 23, "source", 2);
        assertNotEquals(a, b);
    }

    @Test
    void equalsValueDiffTest() {
        Activity b = new Activity(1, "Test", 22, "source", 2);
        assertNotEquals(a, b);
    }

    @Test
    void equalsTextDiffTest() {
        Activity b = new Activity(1, "Tet", 22, "source", 2);
        assertNotEquals(a, b);
    }

    @Test
    void equalsSourceDiffTest() {
        Activity b = new Activity(1, "Tet", 22, "soure", 2);
        assertNotEquals(a, b);
    }

    @Test
    void equalsImageIdDiffTest() {
        Activity b = new Activity(1, "Tet", 22, "source", 3);
        assertNotEquals(a, b);
    }

    @Test
    void equalsSameTest() {
        assertEquals(a, a);
    }

    @Test
    void equalsSameAttributesTest() {
        Activity b = new Activity(1, "Test", 23, "source", 2);
        assertEquals(a, b);
    }
}
