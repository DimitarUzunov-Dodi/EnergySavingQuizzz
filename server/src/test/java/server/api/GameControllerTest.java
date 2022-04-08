package server.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import commons.Question;
import commons.QuestionTypeB;
import commons.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import server.service.GameService;


@WebMvcTest(GameController.class)
public class GameControllerTest {

    @MockBean
    private transient GameService gameService;
    @Autowired
    private transient MockMvc mvc;
    private transient String gameCode;
    private transient Question question;

    /** Before each test.
     */
    @BeforeEach
    public void setup() {
        gameCode = "000000";

        question = new QuestionTypeB(new Activity());

    }

    /** Generic JSON parser.
     *
     * @param obj Object of any class
     * @return JSON String used for requests/response
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "-";
    }

    @Test
    public void createGameTest() throws Exception {
        when(gameService.createGame()).thenReturn(gameCode);

        mvc.perform(get("/api/game/new")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void endGameFailTest() throws Exception {

        when(gameService.doesGameExist(gameCode)).thenReturn(false);

        mvc.perform(delete(String.format("/api/game/end/%s", gameCode))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void endGameTest() throws Exception {

        when(gameService.doesGameExist(gameCode)).thenReturn(true);

        mvc.perform(delete(String.format("/api/game/end/%s", gameCode))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getQuestionFailTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(false);

        mvc.perform(get(String.format("/api/game/getq/%s/%s", gameCode, Integer.toString(0)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getQuestionTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(true);
        when(gameService.getQuestion(gameCode, 0)).thenReturn(question);

        mvc.perform(get(String.format("/api/game/getq/%s/%s", gameCode, Integer.toString(0)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getAnswerFailTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(false);

        mvc.perform(get(String.format("/api/game/getAnswer/%s/%s", gameCode, Integer.toString(0)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getAnswerTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(true);
        when(gameService.getCorrectAnswer(gameCode, 0)).thenReturn(1L);

        mvc.perform(get(String.format("/api/game/getAnswer/%s/%s", gameCode, Integer.toString(0)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void processAnswerFailTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(false);

        mvc.perform(get("/api/game/processAnswer")
                        .queryParam("gameCode", gameCode)
                        .queryParam("username", "alex")
                        .queryParam("questionIndex", "0")
                        .queryParam("answer","0")
                        .queryParam("time", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void processAnswerFail2Test() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(true);
        when(gameService.isUsernamePresent(gameCode, "alex")).thenReturn(false);

        mvc.perform(get("/api/game/processAnswer")
                        .queryParam("gameCode", gameCode)
                        .queryParam("username", "alex")
                        .queryParam("questionIndex", "0")
                        .queryParam("answer","0")
                        .queryParam("time", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void processAnswerTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(true);
        when(gameService.isUsernamePresent(gameCode, "alex")).thenReturn(true);
        when(gameService.processAnswer(gameCode, "alex", 0, 0L, 0L))
                .thenReturn(10);

        mvc.perform(get("/api/game/processAnswer")
                        .queryParam("gameCode", gameCode)
                        .queryParam("username", "alex")
                        .queryParam("questionIndex", "0")
                        .queryParam("answer","0")
                        .queryParam("time", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsersFailTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(false);

        mvc.perform(get(String.format("/api/game/getUsers/%s", gameCode))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getUsersTest() throws Exception {
        when(gameService.doesGameExist(gameCode)).thenReturn(true);
        when(gameService.getUsers(gameCode)).thenReturn(List.of(new User("alex")));

        mvc.perform(get(String.format("/api/game/getUsers/%s", gameCode))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPublicCodeTest1() throws Exception {
        when(gameService.getCurrentPublicGame()).thenReturn(gameCode);

        mvc.perform(get("/api/game/get/public")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPublicCodeTest2() throws Exception {
        when(gameService.getCurrentPublicGame()).thenReturn("");
        when(gameService.createGame()).thenReturn(gameCode);

        mvc.perform(get("/api/game/get/public")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
