package client.communication;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.User;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.glassfish.jersey.client.ClientConfig;

public class Utils {
    public static String serverAddress;

    /**
     * Send GET request to the server to get a list of all users in an ongoing game.
     * Prints the exception stacktrace if the request fails. (HTTP error)
     * @return Optional list of Users
     */
    public static Optional<List<User>> getAllUsers(String gameCode) {

        var dodi = new User("Dodi");
         dodi.addScore(30);
        var geca = new User("Geca1");
        geca.addScore(20);
        var geca1 = new User("Geca13");
        geca1.addScore(40);
        var geca3 = new User("Geca2");
        geca3.addScore(50);
        var geca2 = new User("Geca4");
        geca2.addScore(40);


        return Optional.of(Arrays.asList(dodi,geca,geca1,geca2,geca3));

//        try {
//            if (gameCode == null) {
//                throw new IllegalArgumentException("'gameCode' may not be null!");
//            }
//            return Optional.ofNullable(ClientBuilder.newClient(new ClientConfig())
//                    .target(serverAddress).path("/api/game/getUsers/" + gameCode)
//                    .request(APPLICATION_JSON)
//                    .get(new GenericType<List<User>>() { // do not remove the explicit type!
//                    }));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Optional.empty();
//        }
    }
}
