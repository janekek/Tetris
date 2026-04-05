package game;

import data.Conversion;
import io.DataHandler;
import main.Main;
import utils.music.Music;
import ui.DrawAnimations;
import ui.Gui;
import utils.Scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    public static int countToStart = 0;
    public static ScheduledFuture sf;

    public static int score = 0, scoreToAdd = 0, level = 1, cellSize = 32, rowSize = 18, columnSize = 10;
    public static int gameFieldXmax, gameFieldXmin, gameFieldYmax, gameFieldYmin;
    public static boolean spawnNewBlock = false, isBlockHold = false;

    public static ArrayList<Block> blocks = new ArrayList<>();
    public static Block currentBlock, nextBlock, nextBlock2, nextBlock3, holdenBlock;

    public static int[][] map = new int[columnSize][rowSize];

    public static GameState gamestate = GameState.MENU;
    public static GameMode gameMode = GameMode.CLASSIC;

    public static void initializeGameFieldCoordinates() {
        gameFieldXmax = Gui.width / 2 + ((columnSize / 2) * cellSize);
        gameFieldXmin = Gui.width / 2 - ((columnSize / 2) * cellSize);
        gameFieldYmax = Gui.height / 2 + ((rowSize / 2) * cellSize);
        gameFieldYmin = Gui.height / 2 - ((rowSize / 2) * cellSize);
    }

    public static void addScore (int amount) {
        Game.score += amount;
        if (Game.score > DataHandler.getHighScore(Game.gameMode)) {
            DataHandler.saveHighscore(Game.gameMode, score);
        }
    }

    public static void end() {
        DataHandler.saveScore(Game.gameMode, score);
        Level.seconds = 0;
    }

    public static void clear () {
        HashMap<Block, Boolean> blocks = new HashMap<>();
        for (int x = 0; x < Game.map.length; x++) {
            for (int y = 0; y < Game.map[x].length; y++) {
                if (Game.map[x][y] > 0) {
                    Block b = new Block();
                    b.setX(Conversion.cellXToCoord(x));
                    b.setY(Conversion.cellYToCoord(y));
                    b.setType(Block.getColorValue(Game.map[x][y]));
                    boolean direction;
                    direction = x >= 5;
                    blocks.put(b, direction);
                }
            }
        }
        DrawAnimations.blocksToAnimate.putAll(blocks);
        for (int[] ints : map) {
            Arrays.fill(ints, 0);
        }
        level = 1;
        score = 0;
    }

    public static void restart() {
        Scheduler scheduler = new Scheduler();
        AtomicInteger i = new AtomicInteger(4);
        if (countToStart != 0) return;
        sf = scheduler.scheduleWithFixedDelay(() -> {
            countToStart++;
            i.getAndDecrement();
            if (i.get() == 0) {
                countToStart = 0;
                scheduler.stop(sf, true);
                start();
            }
        }, 0, 1);
    }

    public static void start () {
        clear();
        if (Game.gameMode == GameMode.LINEBREAKER) {
            Linebreaker.createGameField();
        }
        Game.gamestate = GameState.INGAME;
    }

    public static void startFirst() {
        Scheduler scheduler = new Scheduler();
        AtomicInteger i = new AtomicInteger(4);
        if (countToStart != 0) return;
        sf = scheduler.scheduleWithFixedDelay(() -> {
            countToStart++;
            i.getAndDecrement();
            if (i.get() == 0) {
                countToStart = 0;
                scheduler.stop(sf, true);
                Music.stopAll();
                Music.clearAll();
                Main.music = true;
                Music.playBackground();
                start();
            }
        }, 0, 1);
    }

    public static void continueGame () {
        Scheduler scheduler = new Scheduler();
        AtomicInteger i = new AtomicInteger(4);
        if (countToStart != 0) return;
        sf = scheduler.scheduleWithFixedDelay(() -> {
            countToStart++;
            i.getAndDecrement();
            if (i.get() == 0) {
                countToStart = 0;
                scheduler.stop(sf, true);
                Main.music = true;
                Music.updateMusic();
                Game.gamestate = GameState.INGAME;
            }
        }, 0, 1);
    }

}