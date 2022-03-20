package commons;

public class QuestionTypeA extends Question {

    private final int questionType;
    private final Activity activity1;
    private final Activity activity2;
    private final Activity activity3;


    public QuestionTypeA(Activity activityA, Activity activityB, Activity activityC) {

        questionType = 0;
        activity1 = activityA;
        activity2 = activityB;
        activity3 = activityC;

    }

    public String giveText(){
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
}
