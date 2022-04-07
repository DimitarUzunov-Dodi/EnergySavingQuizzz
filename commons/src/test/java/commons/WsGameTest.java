package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class WsGameTest {
    private Instant startTime;
    private Instant endTime;

    @Test
    void testEquals() {
        startTime = Instant.now();
        endTime = startTime.plusSeconds(1);

        WsGame webSocket1 = new WsGame(startTime,endTime);
        WsGame webSocket2 = new WsGame(startTime,endTime);

        assertEquals(webSocket1,webSocket2);

    }
}