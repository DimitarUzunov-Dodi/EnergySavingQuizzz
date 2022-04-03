package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class QuestionTypeBTest {

    private transient QuestionTypeB question;
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

        question = new QuestionTypeB();
    }

    @Test
    public void constructorTest() {
        question = new QuestionTypeB(activity);

        assertNotNull(question);
    }

    @Test
    public void setActivityTest() {
        question.setActivity(activity);
        assertEquals(activity, question.getActivity());
    }

    @Test
    public void setAnswer2Test() {
        question.setAnswer2(answer2);
        assertEquals(answer2, question.getAnswer2());
    }

    @Test
    public void setAnswer3Test() {
        question.setAnswer3(answer3);
        assertEquals(answer3, question.getAnswer3());
    }

    @Test
    public void setQuestionTypeTest() {
        question.setQuestionType(1);
        assertEquals(1, question.getQuestionType());
    }

    @Test
    public void displayText() {
        assertEquals("How much energy does it take?",
                question.displayText());
    }

    @Test
    public void toStringTest() {
        question.setQuestionType(1);
        question.setActivity(activity);
        question.setAnswer2(answer2);
        question.setAnswer3(answer3);

        assertNotNull(question.toString());
    }

}
