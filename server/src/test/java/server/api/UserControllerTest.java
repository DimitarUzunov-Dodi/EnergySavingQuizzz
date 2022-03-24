package server.api;

import commons.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private transient MockMvc mockMvc;

    private transient User user1;
    private transient User user2;
    private transient User user3;

    @BeforeEach
    void setup() {
        user1 = new User();
        user1.setUsername("Alex");
        user1.setScore(4);

        user2 = new User();
        user2.setUsername("Not Alex");
        user2.setScore(5);

        user3 = new User();
        user3.setUsername("Alex");
        user3.setScore(2);
    }

    @Test
    void successfulJoinTest() {

    }

}
