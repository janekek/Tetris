package ui;

import game.Block;
import game.Game;
import game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class DrawPatterns {

    public static void drawPatterns (Graphics2D g) {

        //Tetris patterns
        int start = ((Gui.width % 100) / 2) - 100;
        for (int i = 0; i < (Gui.width/100)+2; i++) {
            BufferedImage img = Block.getPattern(50 , 100, DrawUtils.colors.get(i%7));
            if (Game.gamestate == GameState.PAUSE || Game.gamestate == GameState.GAMEOVER) {
                RescaleOp op = new RescaleOp(0.6f, 0, null);
                img = op.filter(img, null);
            }
            g.drawImage(img, 100*i + start, 0, null);
            g.drawImage(img, 100*i + start, Gui.height-50, null);
        }

    }

}
