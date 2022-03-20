package server.service;

import commons.Activity;
import commons.Question;
import commons.QuestionTypeA;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;
import commons.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private HashMap<String, Game> activeGames;
    private final Random random;

    private final ActivityRepository activityRepository;

    public GameService(ActivityRepository activityRepository, Random random) {
        this.activeGames = new HashMap<String, Game>();
        this.activityRepository = activityRepository;
        this.random = random;
    }


    public Game createGame() {

        Game game = new Game();

        // Generating a random six digit number.
        String code = String.format("%06d", random.nextInt(999999));

        // In case the code is already present in the database, get another.
        //TODO: implement time-out
        while (activeGames.containsKey(code))
            code = String.format("%06d", random.nextInt(999999));

        game.setGameCode(code);
        game.setActiveQuestionList(createQuestions());
        activeGames.put(game.getGameCode(), game);
        return game;

    }

    public List<Question> createQuestions() {
        // to be edited once new question types are added
        // todo replace with for loop for all 20 qs
        int questionType = random.nextInt(1);
        List<Question> questionList = new ArrayList<Question>();
        Question question;

        switch (questionType) {
            case 0:
                List<Activity> activityList = new ArrayList<>(activityRepository.getThreeRandom());
                question = new QuestionTypeA(activityList.get(0), activityList.get(1), activityList.get(2));
                questionList.add(question);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + questionType);
        }
        return questionList;
    }

    public boolean doesGameExist(String gameCode) {
        return activeGames.containsKey(gameCode);
    }

    public Question getQuestion(String gameCode, int qIndex) {
        return activeGames.get(gameCode).getActiveQuestionList().get(qIndex);
    }

    public Game removeGame(String gameCode) {
        return activeGames.remove(gameCode);
    }
}
