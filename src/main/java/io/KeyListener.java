package io;

import data.Collision;
import game.Game;
import game.GameMechanics;
import game.GameState;
import main.Main;
import utils.music.Music;
import utils.Direction;
import utils.Scheduler;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class KeyListener implements java.awt.event.KeyListener {

    public static boolean spaceIsHold = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (Game.gamestate == GameState.START) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Main.music = true;
                Music.playBackground();
                Game.gamestate = GameState.INGAME;
            }
        }

        if (Game.gamestate == GameState.MENU) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Game.startFirst();
            }
        }

        if (Game.gamestate == GameState.INGAME) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (spaceIsHold) return;
                spaceIsHold = true;
                GameMechanics.hardDrop();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                try {
                    if (!Collision.wouldCollideInRotation(Game.currentBlock, Direction.LEFT)) {
                        Game.currentBlock.rotate();
                        Music.playMoveSound();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                GameMechanics.holdBlock();
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                if (GameMechanics.tryToMove(Direction.DOWN, true)) {
                    Game.addScore(1);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                GameMechanics.tryToMove(Direction.RIGHT, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                GameMechanics.tryToMove(Direction.LEFT, true);
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Game.gamestate = GameState.PAUSE;
                Main.music = false;
                Music.updateMusic();
            }
        } else if (Game.gamestate == GameState.PAUSE) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Scheduler scheduler = new Scheduler();
                AtomicInteger i = new AtomicInteger(4);
                if (Game.countToStart != 0) return;
                Game.sf = scheduler.scheduleWithFixedDelay(() -> {
                    Game.countToStart++;
                    i.getAndDecrement();
                    if (i.get() == 0) {
                        Game.countToStart = 0;
                        scheduler.stop(Game.sf, true);
                        Main.music = true;
                        Music.updateMusic();
                        Game.gamestate = GameState.INGAME;
                    }
                }, 0, 1);
            }

        } else if (Game.gamestate == GameState.GAMEOVER) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Game.gamestate = GameState.INGAME;
                Game.start();
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spaceIsHold = false;
        }
    }
}
