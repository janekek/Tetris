package ui;

import data.Collision;
import data.Conversion;
import game.AnimatedBlock;
import game.Block;
import game.Coordinate;
import game.Game;
import main.GameLoop;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawAnimations {

    public static ArrayList<AnimatedBlock> blocksToSkipDown = new ArrayList<>();
    public static HashMap<Block, Boolean> blocksToAnimate = new HashMap<>();

    public static void runOneSkipDownFrame(Graphics2D g) {
        ArrayList<AnimatedBlock> removements = new ArrayList<>();

        loop:
        for (AnimatedBlock b : blocksToSkipDown) {

            for (int i = 0; i < 5; i++) {
                b.getCoordinate().setY(b.getCoordinate().getY() + 1);

                System.out.println(b.getCoordinate().getY());
                System.out.println(Conversion.cellYToCoord(b.getBlock().getY()));

                if (b.getCoordinate().getY() >= Conversion.cellYToCoord(b.getBlock().getY())) {
                    removements.add(b);
                    b.getBlock().setInAnimation(false);
                    for (int x = 0; x < Game.map.length; x++) {
                        for (int y = 0; y < Game.map[x].length; y++) {
                            if (Game.map[x][y] >= 9) Game.map[x][y] = Game.map[x][y] % 8;
                        }
                    }
                    Gui.d.repaint();
                    GameLoop.spawnNewBlock();
                    continue loop;
                }
            }
            BufferedImage img = b.getBlock().getImage(Game.cellSize, Game.cellSize);
            for (int j = 0; j < b.getBlock().getBounds()[b.getBlock().getRotation()].length; j++) {
                for (int k = 0; k < b.getBlock().getBounds()[b.getBlock().getRotation()][j].length; k++) {

                    if (b.getBlock().getBounds()[b.getBlock().getRotation()][j][k] == 1) {
                        g.drawImage(img, Conversion.cellXToCoord(b.getBlock().getX() + j), b.getCoordinate().getY() + Conversion.cellYToCoord(k - 3), null);
                    }
                }
            }
        }
        blocksToSkipDown.removeAll(removements);
    }

    public static void addBlock (Block b) {
        b.setX(Conversion.cellXToCoord(b.getX()));
        b.setY(Conversion.cellYToCoord(b.getY()));
        blocksToAnimate.put(b, Math.random()<0.5);
    }
    public static void addBlock (int x, int y, int color) {
        Block b = new Block();
        b.setX(Conversion.cellXToCoord(x));
        b.setY(Conversion.cellYToCoord(y));
        b.setType(Block.getColorValue(color));
        boolean direction;
        direction = x >= 5;
        blocksToAnimate.put(b, direction);
    }

    public static void drawAnimations(Draw draw, Graphics2D g) {

        HashMap<Block, Boolean> remove = new HashMap<>(blocksToAnimate);

        for (Block b : blocksToAnimate.keySet()) {
            int x = b.getX();
            int y = b.getY();
            boolean direction = blocksToAnimate.get(b);
            if (direction) {
                x = x + 5;
                if (x >= Gui.width) {
                    remove.remove(b);
                } else {
                    remove.remove(b);
                    b.setX(x);
                    b.setY(y);
                    g.drawImage(b.getImage(Game.cellSize, Game.cellSize), b.getX(), b.getY(), null);
                    remove.put(b, true);
                }
            } else {
                x = x - 5;
                if (x <= -b.getImage(Game.cellSize, Game.cellSize).getWidth()) {
                    remove.remove(b);
                } else {
                    remove.remove(b);
                    b.setX(x);
                    b.setY(y);
                    g.drawImage(b.getImage(Game.cellSize, Game.cellSize), b.getX(), b.getY(), null);
                    remove.put(b, false);
                }
            }
        }

        blocksToAnimate = remove;

    }

}