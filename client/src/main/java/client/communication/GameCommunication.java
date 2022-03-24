package client.communication;

import commons.QuestionTypeA;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class GameCommunication {

    private static final String SERVER = "http://localhost:8080/";

    public static String startSinglePlayerGame(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(String.format("api/game/new"))
                .request(APPLICATION_JSON)
                .get(new GenericType<String>() {});
    }

    public static QuestionTypeA getQuestion(String gameCode, int qIndex) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(String.format("api/game/getq/%s/%d", gameCode, qIndex))
                .request(APPLICATION_JSON)
                .get(new GenericType<QuestionTypeA>() {});
    }

}
