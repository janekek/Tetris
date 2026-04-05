package io;

import game.Block;
import game.Game;
import game.GameMechanics;
import game.GameState;
import ui.DrawStartMenu;
import utils.Direction;
import utils.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

    public static boolean hoverThemeButton = false;
    public static boolean hoverChangeGameModeButton = false;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (Game.gamestate == GameState.INGAME)
            moveBlock(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (Game.gamestate == GameState.INGAME)
            moveBlock(e);
        if (Game.gamestate == GameState.MENU) {
            RoundRectangle2D rr = DrawStartMenu.enterGameButton;
            if (rr.getX() <= e.getX() && e.getX() <= rr.getX() + rr.getWidth() && rr.getY() <= e.getY() && e.getY() <= rr.getY() + rr.getHeight()) {
                DrawStartMenu.enterGameButton = new RoundRectangle2D.Float(Utils.getCentralX()-178, Utils.getCentralY()-28, 356, 56, 10, 10);
            } else DrawStartMenu.enterGameButton = new RoundRectangle2D.Float(Utils.getCentralX()-175, Utils.getCentralY()-25, 350, 50, 10, 10);

            Rectangle r = DrawStartMenu.changeTheme;
            hoverThemeButton = r.getX() <= e.getX() && e.getX() <= r.getX() + r.getWidth() && r.getY() <= e.getY() && e.getY() <= r.getY() + r.getHeight();

            r = DrawStartMenu.changeGameModeButton;
            hoverChangeGameModeButton = r.getX() <= e.getX() && e.getX() <= r.getX() + r.getWidth() && r.getY() <= e.getY() && e.getY() <= r.getY() + r.getHeight();

        }
    }

    public void moveBlock (MouseEvent e) {
        if (Game.gamestate == GameState.INGAME) {
            int mouseX = e.getX()-7;
            int mouseY = e.getY()-30;
            Block b = Game.currentBlock;
            int dBlockX = 0;


            loop:
            for (int x = 0; x < b.getBounds()[b.getRotation()].length; x++) {
                for (int y = 0; y < b.getBounds()[b.getRotation()][x].length; y++) {
                    if (b.getBounds()[b.getRotation()][x][y] == 1) {
                        if (dBlockX < x) dBlockX = x;
                        else break loop;
                    }
                }
            }

            if (mouseX <= Game.gameFieldXmax && mouseX > Game.gameFieldXmin && mouseY <= Game.gameFieldYmax && mouseY > Game.gameFieldYmin) {
                mouseX = (mouseX-Game.gameFieldXmin)/32;

                if (b.getX() < mouseX+dBlockX) {
                    while (mouseX - dBlockX > b.getX() && GameMechanics.tryToMove(Direction.RIGHT, true)) ;
                }
                if (b.getX()+dBlockX > mouseX) {
                    while (mouseX - dBlockX < b.getX() && GameMechanics.tryToMove(Direction.LEFT, true));
                }

            }

        }
    }

}
