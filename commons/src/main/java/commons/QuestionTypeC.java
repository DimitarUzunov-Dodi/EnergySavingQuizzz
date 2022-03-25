package commons;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTypeC extends Question {

    private final int questionType;
    private final Activity displayActivity;
    private final Activity activityCorrect;
    private final Activity activity1;
    private final Activity activity2;

    /**
     * Constructor.
     * @param displayActivity displayActivity
     * @param activityCorrect activityCorrect
     * @param activity1 activity1
     * @param activity2 activity2
     */
    public QuestionTypeC(Activity displayActivity, Activity activityCorrect,
                         Activity activity1, Activity activity2) {
        this.questionType = 2;
        this.displayActivity = displayActivity;
        this.activityCorrect = activityCorrect;
        this.activity1 = activity1;
        this.activity2 = activity2;
    }

    public int getQuestionType() {
        return questionType;
    }

    public Activity getDisplayActivity() {
        return displayActivity;
    }

    public Activity getActivityCorrect() {
        return activityCorrect;
    }

    public Activity getActivity1() {
        return activity1;
    }

    public Activity getActivity2() {
        return activity2;
    }

    @Override
    public String displayText() {
        return "Instead of ..., you could: ";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("questionType", questionType)
                .append("displayActivity", displayActivity)
                .append("activityCorrect", activityCorrect)
                .append("activity1", activity1)
                .append("activity2", activity2)
                .append("/n")
                .toString();
    }
}
