package commons;

public class QuestionTypeA extends Question {

    private int questionType;
    private Activity activity1;
    private Activity activity2;
    private Activity activity3;

    public QuestionTypeA() { }


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

    @Override
    public String displayText() {
        return "What is more expensive?";
    }

}
