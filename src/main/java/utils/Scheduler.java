package utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Scheduler {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    public ScheduledFuture scheduleWithFixedDelay (Runnable runnable, int initialDelay, int period) {
        return scheduledExecutorService.scheduleWithFixedDelay(runnable, initialDelay, period, TimeUnit.SECONDS);
    }

    public void stop (ScheduledFuture scheduledFuture, boolean mayInterruptIfRunning) {
        scheduledFuture.cancel(mayInterruptIfRunning);
    }

}
