package commons;

import java.time.Instant;
import java.util.Objects;

public class WsGame {

    public Instant startTime;
    public Instant endTime;

    public  WsGame() {

    }

    public WsGame(Instant startTime, Instant endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WsGame wsGame = (WsGame) o;
        return Objects.equals(startTime, wsGame.startTime)
            && Objects.equals(endTime, wsGame.endTime);
    }

}
