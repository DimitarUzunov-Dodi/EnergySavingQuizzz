package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class QuestionTypeDTest {

    private transient QuestionTypeD question;
    private transient Activity activity;
    private String activityText = "Test";
    private long imageId = 2L;
    private long value = 100L;
    private String source = "source";
    private long answer2 = 75L;
    private long answer3 = 125L;

    /**
     * Setup for tests.
     */
    @BeforeEach
    public void setup() {
        activity = new Activity(1L, activityText, value, source, imageId);

        question = new QuestionTypeD();
    }

    @Test
    public void constructorTest() {
        question = new QuestionTypeD(activity);

        assertNotNull(question);
    }

    @Test
    public void setActivityTest() {
        question.setActivity(activity);
        assertEquals(activity, question.getActivity());
    }

    @Test
    public void setQuestionTypeTest() {
        question.setQuestionType(3);
        assertEquals(3, question.getQuestionType());
    }

    @Test
    public void displayText() {
        assertEquals("How much energy in KWh does the following activity take?",
                question.displayText());
    }

    @Test
    public void toStringTest() {
        question.setQuestionType(3);
        question.setActivity(activity);

        assertNotNull(question.toString());
    }

}
