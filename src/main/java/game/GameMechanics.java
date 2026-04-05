package game;

import data.Collision;
import main.GameLoop;
import utils.music.Music;
import utils.Direction;

public class GameMechanics {

    public static boolean tryToRotate (Direction direction) {
        try {
            if (!Collision.wouldCollideInRotation(Game.currentBlock, direction)) {
                if (direction == Direction.LEFT)
                    Game.currentBlock.rotate();
                else Game.currentBlock.rotate();
                Music.playMoveSound();
                return true;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static void hardDrop () {
        Block b = Game.currentBlock;
//        int x = b.getX(), y = b.getY();
//        b.setInAnimation(true);
        Music.playSkipDownSound();
        int i = 0;

        while (!Collision.wouldCollideWithWall(b, Direction.DOWN, false) && !Collision.wouldCollideWithBlock(b, Direction.DOWN, false)) {
            b.setY(b.getY() + 1);
            i++;
        }
        Game.addScore(i);
        GameLoop.spawnNewBlock();

    }

    public static void holdBlock () {
        if (Game.isBlockHold) return;
        Game.isBlockHold = true;
        Block b = Game.holdenBlock;
        Game.holdenBlock = Game.currentBlock;
        if (b == null) {
            Game.currentBlock = Game.nextBlock;
            Game.nextBlock = Game.nextBlock2;
            Game.nextBlock2 = Game.nextBlock3;
            Game.nextBlock3 = new Block();
        } else {
            Game.currentBlock = b;
        }
        Game.blocks.add(Game.currentBlock);
        Game.blocks.remove(Game.holdenBlock);
        Game.holdenBlock.setX(4);
        Game.holdenBlock.setY(-2);
        Game.holdenBlock.setRotation(0);
    }

    public static boolean tryToMove(Direction direction, boolean sound) {
        if (!Collision.wouldCollideWithWall(Game.currentBlock, direction, false)
                && !Collision.wouldCollideWithBlock(Game.currentBlock, direction, false)) {
            switch (direction) {
                case LEFT -> Game.currentBlock.setX(Game.currentBlock.getX() - 1);
                case RIGHT -> Game.currentBlock.setX(Game.currentBlock.getX() + 1);
                case DOWN -> Game.currentBlock.setY(Game.currentBlock.getY() + 1);
            }
            if (sound)
                Music.playMoveSound();
            return true;
        }
        return false;
    }

}
