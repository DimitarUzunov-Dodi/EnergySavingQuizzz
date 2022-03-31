package commons;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Wraps ScheduledThreadPoolExecutor for convenience.
 */
public class TaskScheduler extends ScheduledThreadPoolExecutor {
    public TaskScheduler(int corePoolSize) {
        super(corePoolSize);
    }

    /**
     * Submits a periodic action that becomes enabled right after submission,
     *      and subsequently with the given period;
     *      that is, executions will commence immediately, then {@code period}, then
     *      {@code 2 * period}, and so on.
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     * @throws IllegalArgumentException   {@inheritDoc}
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long period) {
        return scheduleAtFixedRate(command, 0L, period, MILLISECONDS);
    }

    /**
     * Submits the same task several times at fixed intervals apart.
     * Use this only for small numbers of repetitions.
     * @param command Task to be scheduled
     * @param times Number of times to be scheduled
     * @param period Interval between scheduled times.
     */
    public void scheduleFixedCount(Runnable command, long times, long period) {
        for (int i = 0; i < times; i++) {
            schedule(command, i * period, MILLISECONDS);
        }
    }

    /**
     * Wrapper for {@link ScheduledThreadPoolExecutor#schedule}.
     * @param command - the task to execute
     * @param delay - the time from now to delay execution
     * @return - a ScheduledFuture representing pending completion of the task
     *      and whose get() method will return null upon completion
     */
    public ScheduledFuture<?> schedule(Runnable command, long delay) {
        return schedule(command, delay, MILLISECONDS);
    }

    /**
     * Schedule a task to be executed at a specific point in time.
     * @param command - task to be executed
     * @param instant - moment of execution (expect up to a few microseconds of delay)
     * @return - a ScheduledFuture representing pending completion of the task
     *      and whose get() method will return null upon completion
     */
    public ScheduledFuture<?> scheduleAtInstant(Runnable command, Instant instant) {
        return schedule(command,
                Duration.between(Instant.now(), instant).toNanos(), NANOSECONDS);
    }

    /**
     * Print scheduler status.
     * @return Future that can be used to cancel the debugging task
     */
    public ScheduledFuture<?> startDebugPrinting() {
        return scheduleAtFixedRate(() -> {
            System.out.println("-- completed: " + getCompletedTaskCount());
            System.out.println("----- queued: " + getQueue().size());
            System.out.println("----- active: " + getActiveCount());
        }, 0, 500, MILLISECONDS);
    }
}
