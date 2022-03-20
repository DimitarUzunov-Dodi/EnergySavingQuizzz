package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import server.database.GameRepository;
import server.database.UserRepository;
import commons.User;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private transient UserRepository userRepository;
    @MockBean
    private transient GameRepository gameRepository;
    @Autowired
    private transient MockMvc mockMvc;

    private transient User user1;
    private transient User user2;
    private transient User user3;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setUsername("Alex");
        user1.setGameCode("1");
        user1.setScore(4);

        user2 = new User();
        user2.setUsername("Not Alex");
        user2.setGameCode("1");
        user2.setScore(5);

        user3 = new User();
        user3.setUsername("Alex");
        user3.setGameCode("2");
        user3.setScore(2);
    }

    @Test
    void successfulJoinTest() {

    }

}
