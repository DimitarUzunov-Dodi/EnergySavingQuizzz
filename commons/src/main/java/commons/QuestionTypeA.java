package commons;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTypeA extends Question {

    private final int questionType;
    private Activity activity1;
    private Activity activity2;
    private Activity activity3;

    public QuestionTypeA() {
        questionType = 0;
    }

    /**
     * Basic constructor for a question.
     * @param activityA first activity that will be used
     * @param activityB second activity that will be used
     * @param activityC third activity that will be used
     */
    public QuestionTypeA(Activity activityA, Activity activityB, Activity activityC) {
        questionType = 0;
        activity1 = activityA;
        activity2 = activityB;
        activity3 = activityC;
    }

    public String giveText() {
        String string = "Which one uses the most energy?";
        return  string;
    }

    public int getQuestionType() {
        return questionType;
    }

    public Activity getActivity1() {
        return activity1;
    }

    public Activity getActivity2() {
        return activity2;
    }

    public Activity getActivity3() {
        return activity3;
    }

    @Override
    public String displayText() {
        return "Which activity uses the most energy?";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("questionType", questionType)
                .append("activity1", activity1)
                .append("activity2", activity2)
                .append("activity3", activity3)
                .append("/n")
                .toString();
    }
}
