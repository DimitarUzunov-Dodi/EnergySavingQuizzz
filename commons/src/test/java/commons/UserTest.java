package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class UserTest {

    private User user1;


    /**
     * Setup for tests.
     */
    @BeforeEach
    public void setup() {
        user1 = new User("Dodi");
    }

    @Test
    public void constructorTest() {
        assertNotNull(user1);
    }

    @Test
    void getUsername() {
        assertEquals(user1.getUsername(),"Dodi");
    }

    @Test
    void setUsername() {
        user1.setUsername("Bob");
        assertEquals(user1.getUsername(),"Bob");
    }

    @Test
    void addScore() {
        user1.addScore(60);
        user1.addScore(300);
        user1.addScore(10);
        assertEquals(user1.getScore(),370);
    }

    @Test
    void getScore() {
        user1.addScore(40);
        assertEquals(user1.getScore(),40);
    }



    @Test
    void setScore() {
        user1.setScore(100);
        assertEquals(user1.getScore(),100);
    }
}