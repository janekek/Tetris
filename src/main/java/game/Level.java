package game;

import utils.Scheduler;

import java.util.concurrent.ScheduledFuture;

public class Level extends Thread{

    public static ScheduledFuture sf;
    public static int seconds = 0;

    public static void runLevelChange () {

        Scheduler scheduler = new Scheduler();
        if (Game.countToStart != 0) return;
        sf = scheduler.scheduleWithFixedDelay(() -> {
            if (Game.gamestate.equals(GameState.INGAME)) {
                seconds++;
                if (seconds == 30) {
                    if (Game.level < 20)
                        Game.level += 1;
                    seconds = 0;
                }
            }
        }, 1, 1);

    }

}
