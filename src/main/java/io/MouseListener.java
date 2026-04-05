package io;

import game.Game;
import game.GameMechanics;
import game.GameState;
import main.Main;
import ui.DrawStartMenu;
import utils.Theme;

import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MouseListener implements java.awt.event.MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Game.gamestate == GameState.MENU) {
            RoundRectangle2D rr = DrawStartMenu.enterGameButton;
            if (rr.getX() <= e.getX() && e.getX() <= rr.getX() + rr.getWidth() && rr.getY() <= e.getY() && e.getY() <= rr.getY() + rr.getHeight())
                Game.startFirst();
            if (MouseMotionListener.hoverChangeGameModeButton) Game.gameMode = Game.gameMode.next();
            if (MouseMotionListener.hoverThemeButton) {
                Main.theme = Main.theme.next();
                Theme.updateTheme();
            }
        }
        if (Game.gamestate == GameState.INGAME) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                GameMechanics.hardDrop();
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                GameMechanics.holdBlock();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
