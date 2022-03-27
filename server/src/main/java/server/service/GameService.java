package server.service;

import commons.Activity;
import commons.Game;
import commons.Question;
import commons.QuestionTypeA;
import commons.QuestionTypeB;
import commons.QuestionTypeC;
import commons.QuestionTypeD;
import commons.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

@Service
public class GameService {

    private HashMap<String, Game> activeGames;
    private String currentPublicGame;
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
        this.currentPublicGame = createGame();
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

        int questionType;
        List<Question> questionList = new ArrayList<Question>();
        Question question;


        for (int i = 0; i < 20; i++) {
            questionType = random.nextInt(4);

            switch (questionType) {
                case 0:
                    List<Activity> activityList =
                            new ArrayList<>(activityRepository.getThreeRandom());
                    question = new QuestionTypeA(activityList.get(0),
                            activityList.get(1), activityList.get(2));
                    questionList.add(question);

                    break;
                case 1:
                    question = new QuestionTypeB(activityRepository.getOneRandom().get());
                    questionList.add(question);

                    break;

                case 2:
                    Activity displayActivity = activityRepository.getOneRandom().get();
                    Activity correctActivity = activityRepository
                            .getOneRelated(displayActivity.getValue()).get();
                    question = new QuestionTypeC(displayActivity, correctActivity,
                            activityRepository.getOneRandom().get(),
                            activityRepository.getOneRandom().get());
                    questionList.add(question);

                    break;
                case 3:
                    question = new QuestionTypeD(activityRepository.getOneRandom().get());
                    questionList.add(question);

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + questionType);
            }
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

    /**
     * Checks if an username with the given name is already in that game.
     *
     * @param gameCode The game
     * @param username The name
     * @return boolean
     */
    public boolean isUsernamePresent(String gameCode, String username) {
        return activeGames.get(gameCode).getUserList().stream()
                .map(x -> x.getUsername())
                .collect(Collectors.toList())
                .contains(username);
    }

    public void joinGame(String gameCode, String username) {
        activeGames.get(gameCode).addUser(new User(username));
    }

    /**
     * Method handles leave from the game.
     * @param gameCode game to leave
     * @param username username of the user that leaves
     */
    public void leaveGame(String gameCode, String username) {
        activeGames.get(gameCode).removeUser(new User(username));
        if (getUsers(gameCode).size() == 0 && !currentPublicGame.equals(gameCode)) {
            activeGames.remove(gameCode);
        }
    }

    public List<User> getUsers(String gameCode) {
        return activeGames.get(gameCode).getUserList();
    }

    public Game removeGame(String gameCode) {
        return activeGames.remove(gameCode);
    }

    public boolean isNotAllowedToJoin(String gameCode) {
        return activeGames.get(gameCode).hasStarted();
    }

    /**
     * Method closes the room, so it's become impossible to join it.
     * In case of room being public a new public room is created.
     * @param gameCode code of the room to close
     */
    public void closeRoom(String gameCode) {
        activeGames.get(gameCode).setStarted(true);
        if (this.currentPublicGame.equals(gameCode)) {
            this.currentPublicGame = createGame();
        }
    }

    public String getCurrentPublicGame() {
        return currentPublicGame;
    }
}
