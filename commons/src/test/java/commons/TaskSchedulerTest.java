package commons;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TaskSchedulerTest {
    private TaskScheduler scheduler;
    private int sillyGoose = 0;



    @BeforeEach
    public void setup() {
        scheduler = new TaskScheduler(5);
    }

    @Test
    void scheduleAtFixedRate() {

        var b = scheduler.scheduleAtFixedRate(() -> sillyGoose++,3000);
        b.cancel(false);

    }


    @Test
    void schedule() {
        var b = scheduler.schedule(() -> sillyGoose++,3000);
        b.cancel(false);

    }

    @Test
    void scheduleAtInstant() {
        var b = scheduler.scheduleAtInstant(() -> sillyGoose++, Instant.now());
        try {
            b.get(4000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    @Test
    void startDebugPrinting() {
        scheduler.scheduleAtInstant(() -> sillyGoose++, Instant.now());
        scheduler.startDebugPrinting();

    }
}