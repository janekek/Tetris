package ui;

import game.Game;
import game.GameState;
import io.Button;

import javax.swing.*;
import java.awt.*;

public class Draw extends JLabel {

    @Override
    protected void paintComponent(Graphics g1) {
        Graphics2D g = Gui.getG2D(g1);
        super.paintComponent(g);
        this.setBounds(0, 0, Gui.width, Gui.height);

        DrawPatterns.drawPatterns(g);
        DrawUtils.drawCloseButtonImage(g);
        DrawUtils.drawFPS(g);

        if (Game.gamestate == GameState.INGAME) {
            DrawGame.drawGame(this, g);
            DrawInterface.drawInterface(g);
        }
        if (Game.gamestate == GameState.MENU) {
            DrawStartMenu.drawMenu(this, g);
        }
        if (Game.gamestate == GameState.GAMEOVER) {
            DrawGame.drawGame(this, g);
            DrawInterface.drawInterface(g);
            DrawMenu.drawGameover(g);
        }
        if (Game.gamestate == GameState.WON) {
            DrawGame.drawGame(this, g);
            DrawInterface.drawInterface(g);
            DrawMenu.drawWon(g);
        }
        if (Game.gamestate == GameState.PAUSE) {
            DrawGame.drawGame(this, g);
            DrawInterface.drawInterface(g);
            DrawMenu.drawPause(g);
        }
        if (!DrawAnimations.blocksToAnimate.isEmpty()) {
            DrawAnimations.drawAnimations(this, g);
        }
        if (!DrawAnimations.blocksToSkipDown.isEmpty()) {
            DrawAnimations.runOneSkipDownFrame(g);
        }

        if(Game.gamestate != GameState.PAUSE && Game.gamestate != GameState.GAMEOVER && Game.gamestate != GameState.WON) {
            Button.restartButton.getButton().setVisible(false);
            io.Button.backToMenu.getButton().setVisible(false);
            io.Button.continuePause.getButton().setVisible(false);
        } else {
            io.Button.restartButton.getButton().setVisible(true);
            io.Button.backToMenu.getButton().setVisible(true);
            io.Button.continuePause.getButton().setVisible(true);
        }

    }
}