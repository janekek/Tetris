package main;

import data.Collision;
import game.Block;
import game.Game;
import game.GameMechanics;
import game.GameState;
import utils.Direction;

public class GameLoop extends Thread{

    public static boolean running = true;

    @Override
    public void run() {

        while (running) {
            try {

                if (Game.gamestate == GameState.INGAME)
                    GameMechanics.tryToMove(Direction.DOWN, false);

                if (Game.spawnNewBlock) {
                    long l = (1000L -((Game.level-1)* 50L))*2;
                    if (l >= 1000) l = 1000;
                    sleep(l);
                } else sleep(1000L -((Game.level-1)* 50L));

                if (Game.spawnNewBlock) {
                    spawnNewBlock();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void runOneLoop () {
        GameMechanics.tryToMove(Direction.DOWN, false);
        if (Game.spawnNewBlock) {
            spawnNewBlock();
        }
    }

    public static void spawnNewBlock () {
        Collision.checkFullRow(1);
        Game.isBlockHold = false;
        Game.currentBlock.setMovable(false);
        Game.blocks.add(Game.nextBlock);
        Game.currentBlock = Game.nextBlock;
        Game.nextBlock = Game.nextBlock2;
        Game.nextBlock2 = Game.nextBlock3;
        Game.nextBlock3 = new Block();
        Game.spawnNewBlock = false;
    }

}
