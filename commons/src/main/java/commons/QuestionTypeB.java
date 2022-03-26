package commons;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTypeB extends Question {

    private int questionType;
    private Activity activity;
    private long answer2;
    private long answer3;

    public QuestionTypeB() {
    }

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

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAnswer2(long answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(long answer3) {
        this.answer3 = answer3;
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
