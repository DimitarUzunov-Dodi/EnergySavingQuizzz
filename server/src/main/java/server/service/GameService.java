package server.service;

import commons.Activity;
import commons.Game;
import commons.Question;
import commons.QuestionTypeA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

@Service
public class GameService {

    private HashMap<String, Game> activeGames;
    private final Random random;

    private final ActivityRepository activityRepository;

    /**
     *  Game Service class which manages the list of active games.
     *
     * @param activityRepository The activity repo for constructing questions.
     * @param random Random generator for game codes.
     */
    public GameService(ActivityRepository activityRepository, Random random) {
        this.activeGames = new HashMap<String, Game>();
        this.activityRepository = activityRepository;
        this.random = random;
    }

    /**
     *  Create game method.
     *
     * @return Game code for the newly created game.
     */
    public String createGame() {

        Game game = new Game();

        // Generating a random six digit number.
        String code = String.format("%06d", random.nextInt(999999));

        // In case the code is already present in the database, get another.
        //TODO: implement time-out
        while (activeGames.containsKey(code)) {
            code = String.format("%06d", random.nextInt(999999));
        }

        game.setGameCode(code);
        game.setActiveQuestionList(createQuestions());
        activeGames.put(game.getGameCode(), game);
        return code;

    }

    /**
     * Method that creates 20 questions when a game is created.
     *
     * @return The List of 20 new questions.
     */
    public List<Question> createQuestions() {
        // to be edited once new question types are added
        // todo replace with for loop for all 20 qs
        int questionType = random.nextInt(1);
        List<Question> questionList = new ArrayList<Question>();
        Question question;

        switch (questionType) {
            case 0:
                List<Activity> activityList = new ArrayList<>(activityRepository.getThreeRandom());
                question = new QuestionTypeA(activityList.get(0),
                        activityList.get(1), activityList.get(2));
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

    public Question getQuestion(String gameCode, int questionIndex) {
        return activeGames.get(gameCode).getActiveQuestionList().get(questionIndex);
    }

    public Game getGame(String gameCode) {
        return activeGames.get(gameCode);
    }

    public Game removeGame(String gameCode) {
        return activeGames.remove(gameCode);
    }
}
