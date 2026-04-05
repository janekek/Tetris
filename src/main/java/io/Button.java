package io;

import game.Game;
import game.GameState;
import utils.music.Music;
import ui.Gui;
import utils.Utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Button {

    private final JButton button;
    private final PlasticCoord x, y;
    private final int width, height;

    public interface PlasticCoord {
        int coord ();
    }

    public Button (PlasticCoord x, PlasticCoord y, int width, int height, ActionListener actionListener) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        button = new JButton();
        button.addActionListener(actionListener);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setVisible(true);
        update();
        Gui.jf.add(button);
    }

    public static Button restartButton = new Button(() -> Utils.getCentralX()+30, () -> Utils.getCentralY()+35, 130, 25, e -> {
        Game.end();
        Game.start();
        Music.restartAll();
    });
    public static Button closeButton = new Button(() -> Gui.width-50, () -> 0, 50, 25, e -> System.exit(0));
    public static Button continuePause = new Button(() -> Utils.getCentralX() - 200, () -> Utils.getCentralY() - 22, 400, 44, e -> {
        if (Game.gamestate == GameState.PAUSE) {
            Game.continueGame();
        }
        if (Game.gamestate == GameState.WON || Game.gamestate == GameState.GAMEOVER) {
            Game.restart();
        }
    });
    public static Button backToMenu = new Button(() -> Utils.getCentralX() - 180, () -> Utils.getCentralY() + 35, 160, 25, e -> {
        if (Game.gamestate == GameState.GAMEOVER) {
            Game.clear();
        }
        Game.gamestate = GameState.MENU;
    });

    public void update () {
        button.setBounds(x.coord(), y.coord(), width, height);
    }

    public JButton getButton () {
        return this.button;
    }

    public static void updateButtonsBounds () {
        restartButton.update();
        closeButton.update();
        backToMenu.update();
        continuePause.update();
    }

}
