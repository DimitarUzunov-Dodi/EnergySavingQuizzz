package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActivityTest {
    public Activity activityA;
    public long id = 1;
    public String activityText = "Test";
    public long imageId = 2;
    public long value = 23;
    public String source = "source";

    @BeforeEach
    public void setup() {
        activityA = new Activity(id, activityText, value, source, imageId);
    }

    @Test
    public void constructorTest() {
        Activity toBeTested = new Activity(id, activityText, value, source, imageId);
        assertNotNull(toBeTested);
    }

    @Test
    public void constructorWithoutIdTest() {
        Activity toBeTested = new Activity(activityText, value, source, imageId);
        assertNotNull(toBeTested);
    }

    @Test
    void getActivityIdTest() {
        assertEquals(id, activityA.getActivityId());
    }

    @Test
    void setActivityIdTest() {
        long toChange = 23;
        activityA.setActivityId(toChange);
        assertEquals(toChange, activityA.getActivityId());
    }

    @Test
    void getActivityTextTest() {
        assertEquals(activityText, activityA.getActivityText());
    }

    @Test
    void setActivityTextTest() {
        String toChange = "New text";
        activityA.setActivityText(toChange);
        assertEquals(toChange, activityA.getActivityText());
    }

    @Test
    void getActivitySourceTest() {
        assertEquals(source, activityA.getSource());
    }

    @Test
    void setActivitySourceTest() {
        String toChange = "New source";
        activityA.setSource(toChange);
        assertEquals(toChange, activityA.getSource());
    }

    @Test
    void getActivityValueTest() {
        assertEquals(value, activityA.getValue());
    }

    @Test
    void setActivityValueTest() {
        long toChange = 46;
        activityA.setValue(toChange);
        assertEquals(toChange, activityA.getValue());
    }

    @Test
    void getActivityImageId() {
        assertEquals(imageId, activityA.getImageId());
    }

    @Test
    void setActivityImageId() {
        long toChange = 3;
        activityA.setImageId(toChange);
        assertEquals(toChange, activityA.getImageId());
    }

    @Test
    void equalsNullTest() {
        Activity b = null;
        assertNotEquals(activityA, b);
    }

    @Test
    void equalsIdDiffTest() {
        Activity b = new Activity(2, "Test", 23, "source", 2);
        assertNotEquals(activityA, b);
    }

    @Test
    void equalsValueDiffTest() {
        Activity b = new Activity(1, "Test", 22, "source", 2);
        assertNotEquals(activityA, b);
    }

    @Test
    void equalsTextDiffTest() {
        Activity b = new Activity(1, "Tet", 22, "source", 2);
        assertNotEquals(activityA, b);
    }

    @Test
    void equalsSourceDiffTest() {
        Activity b = new Activity(1, "Tet", 22, "soure", 2);
        assertNotEquals(activityA, b);
    }

    @Test
    void equalsImageIdDiffTest() {
        Activity b = new Activity(1, "Tet", 22, "source", 3);
        assertNotEquals(activityA, b);
    }

    @Test
    void equalsSameTest() {
        assertEquals(activityA, activityA);
    }

    @Test
    void equalsSameAttributesTest() {
        Activity b = new Activity(1, "Test", 23, "source", 2);
        assertEquals(activityA, b);
    }

    @Test
    void hashCodeTest() {
        Activity b = new Activity(1, "Test", 23, "source", 2);
        assertEquals(activityA.hashCode(), b.hashCode());
    }

    @Test
    void toStringSameAttributesTest() {
        Activity b = new Activity(1, "Test", 23, "source", 2);
        assertEquals(activityA.toString(), b.toString());
    }
}
