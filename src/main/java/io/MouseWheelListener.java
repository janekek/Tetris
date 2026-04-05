package io;

import game.Game;
import game.GameMechanics;
import game.GameState;
import utils.Direction;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (Game.gamestate == GameState.INGAME) {
            if (e.getWheelRotation() > 0) {
                GameMechanics.tryToRotate(Direction.LEFT);
            } else {
                GameMechanics.tryToRotate(Direction.RIGHT);
            }
        }
    }
}