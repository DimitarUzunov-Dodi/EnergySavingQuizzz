package server.entities;

public class QuestionTypeA extends Question {

    private final String questionText;
    private final Activity activity1;
    private final Activity activity2;
    private final Activity activity3;

    public QuestionTypeA(Activity activityA, Activity activityB, Activity activityC) {

        questionText = new String("What requires more energy?");
        activity1 = activityA;
        activity2 = activityB;
        activity3 = activityC;

    }

}
