package commons;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTypeC extends Question {

    private int questionType;
    private Activity displayActivity;
    private Activity activityCorrect;
    private Activity activity1;
    private Activity activity2;

    public QuestionTypeC() {
    }

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

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public void setDisplayActivity(Activity displayActivity) {
        this.displayActivity = displayActivity;
    }

    public void setActivityCorrect(Activity activityCorrect) {
        this.activityCorrect = activityCorrect;
    }

    public void setActivity1(Activity activity1) {
        this.activity1 = activity1;
    }

    public void setActivity2(Activity activity2) {
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
        return "Instead of %s, you could be: ";
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
