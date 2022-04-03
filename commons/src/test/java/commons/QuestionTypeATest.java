package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class QuestionTypeATest {

    private transient QuestionTypeA question;
    private transient Activity activity1;
    private transient Activity activity2;
    private transient Activity activity3;
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
        activity3 = new Activity(3L, activityText, value, source, imageId);

        question = new QuestionTypeA();
    }

    @Test
    public void constructorTest() {
        question = new QuestionTypeA(activity1, activity2, activity3);

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
    public void setActivity3Test() {
        question.setActivity3(activity3);
        assertEquals(activity3, question.getActivity3());
    }

    @Test
    public void setQuestionTypeTest() {
        question.setQuestionType(0);
        assertEquals(0, question.getQuestionType());
    }

    @Test
    public void displayText() {
        assertEquals("Which activity uses the most energy?",
                question.displayText());
    }

    @Test
    public void toStringTest() {
        question.setActivity1(activity1);
        question.setActivity2(activity2);
        question.setActivity3(activity3);
        question.setQuestionType(0);

        assertNotNull(question.toString());
    }

}
