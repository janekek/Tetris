package data;

import game.*;
import io.DataHandler;
import utils.music.Music;
import utils.Direction;

public class Collision {

    public static boolean wouldCollideWithBlock(Block b, Direction direction, boolean ghost) {

        switch (direction) {
            case LEFT:
                if (b.getY() >= 0) {
                    if (b.getX() > 0) {
                        for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                            for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                                if (b.getBounds()[b.getRotation()][i][j] == 1) {
                                    if (Game.map[b.getX() + i - 1][b.getY() + j] >= 1) {
                                        return true;
                                    }
                                }
                            }
                        }

                    }
                }
                break;
            case DOWN:
                if (b.getY() + b.getSize() > 1) {
                    if (b.getY() - b.getSize() < 17) {
                        try {
                            for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                                for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                                    if (b.getBounds()[b.getRotation()][i][j] == 1) {

                                        if (Game.map[b.getX() + i][b.getY() + j + 1] >= 1) {

                                            if (!ghost) {
                                                if (!b.isInAnimation())
                                                    Game.spawnNewBlock = true;
                                                fillBlock(b);
                                            }

                                            return true;
                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }

                break;
            case RIGHT:
                if (b.getY() > 0) {
                    if (b.getX() < 10) {
                        for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                            for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                                if (b.getBounds()[b.getRotation()][i][j] == 1) {
                                    if (Game.map[b.getX() + i + 1][b.getY() + j] >= 1) {

                                        return true;
                                    }
                                }
                            }
                        }

                    }
                }
                break;
        }

        return false;
    }

    public static boolean wouldCollideInRotation(Block b, Direction direction) {
        int rot;
        if (direction == Direction.LEFT) {
            rot = b.getRotation() + 1;
            if (rot == 4) rot = 0;
        } else {
            rot = b.getRotation() - 1;
            if (rot == -1) rot = 3;
        }

        Block block = new Block();
        block.setRotation(rot);
        block.setBounds(b.getBounds());
        block.setSize(b.getSize());
        block.setX(b.getX()-1);
        block.setY(b.getY());

        if(wouldCollideWithWall(block, Direction.RIGHT, false)) {
            return true;
        }
        block.setX(b.getX()+2);
        if(wouldCollideWithWall(block, Direction.LEFT, false)) {
            return true;
        }

        if (b.getY() > 0) {
            for (int i = 0; i < b.getBounds()[rot].length; i++) {
                for (int j = 0; j < b.getBounds()[rot][i].length; j++) {
                    if (b.getBounds()[rot][i][j] == 1) {
                        try {
                            if (Game.map[b.getX() + i][b.getY() + j] >= 1) {

                                return true;
                            }
                        } catch (Exception e) {
                            return true;
                        }
                    }
                }

            }
        }

        return false;
    }

    public static boolean wouldCollideWithWall(Block b, Direction direction, boolean ghost) {
        // direction: -1 = links, 0 = runter, 1 = rechts
        switch (direction) {
            case LEFT:
                for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                    for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                        if (b.getBounds()[b.getRotation()][i][j] == 1) {
                            if (b.getX() + i == 0) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case DOWN:
                for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                    for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                        if (b.getBounds()[b.getRotation()][i][j] == 1) {
                            if (b.getY() + j == 17) {
                                if (!ghost) {
                                    if (!b.isInAnimation())
                                        Game.spawnNewBlock = true;
                                    fillBlock(b);
                                }
                                return true;
                            }
                        }
                    }
                }
                break;
            case RIGHT:
                for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                    for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                        if (b.getBounds()[b.getRotation()][i][j] == 1) {
                            if (b.getX() + (i + 2) >= 11) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }

        return false;
    }

    public static void fillBlock(Block b) {
        try {
            for (int i = 0; i < b.getBounds()[b.getRotation()].length; i++) {
                for (int j = 0; j < b.getBounds()[b.getRotation()][i].length; j++) {
                    if (b.getBounds()[b.getRotation()][i][j] == 1) {
                        if (!b.isInAnimation())
                            Game.map[b.getX() + i][b.getY() + j] = b.getTypeValue();
                        else Game.map[b.getX() + i][b.getY() + j] = b.getTypeValue() + 8;
                    }

                }
            }
        } catch (Exception ignored) {

        }
        checkLoose();
    }

    public static void checkFullRow(int multiplier) {

        int blocksInRow = 0;

        for (int y = Game.map[0].length - 1; y > 0; y--) {
            for (int x = 0; x < Game.map.length; x++) {

                if (Game.map[x][y] > 0) {
                    blocksInRow++;
                }
            }
            if (blocksInRow == 10) {
                Music.playRow();

                double levelMultiplier = 1 + ((((double)Game.level-1)*3) / 100);
                Game.scoreToAdd += (100 * multiplier * levelMultiplier);

                delRow(y, multiplier);
                break;
            } else {
                blocksInRow = 0;
            }

        }

        Game.score += Game.scoreToAdd;
        Game.scoreToAdd = 0;

        if (Game.score > DataHandler.getHighScore(Game.gameMode)) {
            DataHandler.saveHighscore(Game.gameMode, Game.score);
        }
    }

    private static void delRow(int row, int multiplier) {

        for (int i = 0; i < Game.map.length; i++) {

            Game.map[i][row] = 0;
        }

        for (int y = row; y > 1; y--) {
            for (int x = 0; x < Game.map.length; x++) {
                Game.map[x][y] = Game.map[x][y - 1];
            }

        }

        boolean empty = true;

        if (Game.gameMode == GameMode.LINEBREAKER) {
            for (int x = 0; x < Game.map.length; x++) {

                if (Game.map[x][Game.rowSize - 1] > 0) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                //Win Game
                Game.gamestate = GameState.WON;
                Game.end();
            }
        }

        checkFullRow(multiplier + 1);
    }

    private static void checkLoose() {

        for (int x = 0; x < Game.map.length; x++) {
            if (Game.map[x][0] > 0) {
                Game.gamestate = GameState.GAMEOVER;
                Game.end();
                break;
            }
        }
    }

}
