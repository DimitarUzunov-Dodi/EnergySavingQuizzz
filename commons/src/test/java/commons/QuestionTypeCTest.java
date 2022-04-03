package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class QuestionTypeCTest {

    private transient QuestionTypeC question;
    private transient Activity activity1;
    private transient Activity activity2;
    private transient Activity activityCorrect;
    private transient Activity displayActivity;
    private String activityText = "Test";
    private long imageId = 2;
    private long value = 23;
    private String source = "source";

    /**
     * Setup for tests.
     */
    @BeforeEach
    public void setup() {
        activity1 = new Activity(1L, activityText, value, source, imageId);
        activity2 = new Activity(2L, activityText, value, source, imageId);
        activityCorrect = new Activity(3L, activityText, value, source, imageId);
        displayActivity = new Activity(4L, activityText, value, source, imageId);

        question = new QuestionTypeC();
    }

    @Test
    public void constructorTest() {
        question = new QuestionTypeC(displayActivity, activityCorrect,
                activity1, activity2);

        assertNotNull(question);
    }

    @Test
    public void setActivity1Test() {
        question.setActivity1(activity1);
        assertEquals(activity1, question.getActivity1());
    }

    @Test
    public void setActivity2Test() {
        question.setActivity2(activity2);
        assertEquals(activity2, question.getActivity2());
    }

    @Test
    public void setDisplayActivityTest() {
        question.setDisplayActivity(displayActivity);
        assertEquals(displayActivity, question.getDisplayActivity());
    }

    @Test
    public void setActivityCorrectTest() {
        question.setActivityCorrect(activityCorrect);
        assertEquals(activityCorrect, question.getActivityCorrect());
    }

    @Test
    public void setQuestionTypeTest() {
        question.setQuestionType(2);
        assertEquals(2, question.getQuestionType());
    }

    @Test
    public void displayText() {
        assertEquals("Instead of %s, you could be: ",
                question.displayText());
    }

    @Test
    public void toStringTest() {
        question.setActivity1(activity1);
        question.setActivity2(activity2);
        question.setActivityCorrect(activityCorrect);
        question.setDisplayActivity(displayActivity);
        question.setQuestionType(2);

        assertNotNull(question.toString());
    }

}
