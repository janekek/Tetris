package io;

import ui.Gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MoveWindow implements MouseListener, MouseMotionListener {

    private static Point initialClick;
    private static boolean windowIsAllowedToMove = false;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (e.getX() <= Gui.width - 50 && e.getY() <= 25) {
                initialClick = e.getPoint();
                windowIsAllowedToMove = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        windowIsAllowedToMove = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (windowIsAllowedToMove) {

            int moveX = e.getX() - initialClick.x;
            int moveY = e.getY() - initialClick.y;

            Gui.jf.setLocation(Gui.jf.getX()+moveX, Gui.jf.getY()+moveY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
