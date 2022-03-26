package commons;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTypeD extends Question {

    private int questionType;
    private Activity activity;

    /**
     * Constructor.
     * @param activity activity
     */
    public QuestionTypeD(Activity activity) {
        this.questionType = 3;
        this.activity = activity;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getQuestionType() {
        return questionType;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public String displayText() {
        return "How much energy in KWh does the following activity take?";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("questionType", questionType)
                .append("activity", activity)
                .append("/n")
                .toString();
    }
}
