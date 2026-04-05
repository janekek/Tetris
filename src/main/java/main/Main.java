package main;

import java.awt.FontFormatException;
import java.io.IOException;

import game.Block;
import game.Game;
import game.Level;
import io.DataHandler;
import ui.Gui;
import utils.Theme;

public class Main {

    public static boolean music = false;
    public static Theme theme;
    public static float masterVolume = DataHandler.getSomething("data/volume.txt");

    public static void main (String[] args) throws IOException, FontFormatException {

        Thread.currentThread().setPriority((int)(Thread.MAX_PRIORITY*0.8));

        setTheme();
        Game.initializeGameFieldCoordinates();

        Game.currentBlock = new Block();
        Game.blocks.add(Game.currentBlock);
        Game.nextBlock = new Block();
        Game.nextBlock2 = new Block();
        Game.nextBlock3 = new Block();

        Gui g = new Gui();
        try {
            g.create();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        Level.runLevelChange();
        startLoop();

    }

    public static void startLoop() {
        GameLoop loop = new GameLoop();
        loop.start();
    }

    public static void setTheme() {
        int i = DataHandler.getSomething("data/theme.txt");
        if (i == 0) theme = Theme.LIGHT;
        if (i == 1) theme = Theme.DARK;
    }

}
