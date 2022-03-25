package commons;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTypeB extends Question {

    private final int questionType;
    private final Activity activity;
    private final long answer2;
    private final long answer3;

    /**
     * Constructor.
     * @param activity activity from DB
     */
    public QuestionTypeB(Activity activity) {
        questionType = 1;
        this.activity = activity;
        answer2 = (long) (activity.getValue() * 0.75);
        answer3 = (long) (activity.getValue() * 1.25);
    }

    public int getQuestionType() {
        return questionType;
    }

    public Activity getActivity() {
        return activity;
    }

    public long getAnswer2() {
        return answer2;
    }

    public long getAnswer3() {
        return answer3;
    }

    @Override
    public String displayText() {
        return "How much energy does it take?";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("questionType", questionType)
                .append("activity", activity)
                .append("answer2", answer2)
                .append("answer3", answer3)
                .toString();
    }
}
