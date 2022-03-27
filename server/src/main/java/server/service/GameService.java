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
import java.util.Arrays;
import java.util.Comparator;
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
            questionType = random.nextInt(1); // TODO change to 4 back

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
        activeGames.get(gameCode).getUserList().add(new User(username));
    }

    /**
     * Method handles leave from the game.
     * @param gameCode game to leave
     * @param username username of the user that leaves
     */
    public void leaveGame(String gameCode, String username) {
        activeGames.get(gameCode).removeUser(new User(username));
        if (getUsers(gameCode).size() == 0) {
            activeGames.remove(gameCode);
        }
    }

    public List<User> getUsers(String gameCode) {
        return activeGames.get(gameCode).getUserList();
    }

    public Game removeGame(String gameCode) {
        return activeGames.remove(gameCode);
    }

    public boolean isAllowedToJoin(String gameCode) {
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

    /**
     * Processes answer from the user and gives bonus points.
     * Decides how many points(if any) allocate to the user
     * Question Types A,B,C - either right or wrong
     * Question Type D - depends on how close the answer is
     * Time - from 0 to 100
     * rewardPoints are multiplied by time and divided by 10
     * so the score range is from 0 to 100
     *
     * @param gameCode - code of the game
     * @param username - name of the user requesting answer processing
     * @param questionIndex - index of question answered by user
     * @param answer - answer of the user(energy consume)
     * @param time - time left (0% - 100%)
     * @return reward points for user
     */
    public int processAnswer(String gameCode, String username,
                             int questionIndex, long answer, int time) {
        int rewardPoints = 0;

        Question question = getQuestion(gameCode, questionIndex);
        long correctAnswer = retrieveAnswer(question);

        if (correctAnswer == answer) {
            rewardPoints = 10;
        } else {
            if (question.getQuestionType() == 3) {
                if (answer >= 0.8 * correctAnswer && answer <= 1.2 * correctAnswer) {
                    rewardPoints = 10;
                }
            }
        }

        rewardPoints *= time;
        rewardPoints /= 10;

        User currentUser = getUserByUsername(gameCode, username);
        if (currentUser == null) {
            return 0;
        }

        currentUser.addScore(rewardPoints);
        return rewardPoints;
    }

    /**
     * Gets correct answer from the specified game from questionIndex.
     *
     * @param gameCode - code of the game
     * @param questionIndex - index of question requested by user
     * @return correct answer in energy consumption
     */
    public long getCorrectAnswer(String gameCode, int questionIndex) {
        Question question = getQuestion(gameCode, questionIndex);
        return retrieveAnswer(question);
    }

    private long retrieveAnswer(Question question) {
        long answer = -1;
        switch (question.getQuestionType()) {
            case 0:
                answer = correctAnswerQuestionTypeA((QuestionTypeA) question);
                break;
            case 1:
                answer = checkAnswerQuestionTypeB((QuestionTypeB) question);
                break;
            case 2:
                answer = checkAnswerQuestionTypeC((QuestionTypeC) question);
                break;
            case 3:
                answer = checkAnswerQuestionTypeD((QuestionTypeD) question);
                break;

            default:
                // TODO throw error
                break;
        }
        return answer;
    }

    public String getCurrentPublicGame() {
        return currentPublicGame;
    }

    private long correctAnswerQuestionTypeA(QuestionTypeA question) {
        List<Activity> activities = Arrays.asList(
                question.getActivity1(),
                question.getActivity2(),
                question.getActivity3());

        Activity maxConsumption = activities
                .stream()
                .max(Comparator.comparing(Activity::getValue))
                .get();

        return maxConsumption.getValue();
    }

    private long checkAnswerQuestionTypeB(QuestionTypeB question) {
        return question.getActivity().getValue();
    }

    private long checkAnswerQuestionTypeC(QuestionTypeC question) {
        return question.getActivityCorrect().getValue();
    }

    private long checkAnswerQuestionTypeD(QuestionTypeD question) {
        return question.getActivity().getValue();
    }

    private User getUserByUsername(String gameCode, String username) {
        for (User user : getUsers(gameCode)) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
