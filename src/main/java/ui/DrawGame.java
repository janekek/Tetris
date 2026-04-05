package ui;

import data.Collision;
import data.Conversion;
import game.Block;
import game.Game;
import io.Slider;
import utils.Direction;
import utils.Theme;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawGame {

    public static void drawGame(Draw draw, Graphics2D g) {

        for (int x = 0; x < Game.map.length; x++) {
            for (int y = 0; y < Game.map[x].length; y++) {
                if (Game.map[x][y] > 0) {
                    String colour = null;
                    switch (Game.map[x][y]) {
                        case 1 -> colour = "cyan";
                        case 2 -> colour = "yellow";
                        case 3 -> colour = "purple";
                        case 4 -> colour = "orange";
                        case 5 -> colour = "blue";
                        case 6 -> colour = "red";
                        case 7 -> colour = "green";
                    }
                    if (colour == null) continue;
                    BufferedImage bufferedImage = Block.getImage(Game.cellSize, Game.cellSize, colour);
                    g.drawImage(bufferedImage, Conversion.cellXToCoord(x), Conversion.cellYToCoord(y), draw);
                }
            }
        }

        DrawUtils.paintBlock(g, Game.currentBlock, Game.cellSize, Game.currentBlock.getX(), Game.currentBlock.getY());

        g.setColor(Theme.gitter);
        for (int x = 0; x < Game.columnSize; x++) {
            for (int y = 0; y < Game.rowSize; y++) {
                g.drawRect(Conversion.cellXToCoord(x), Conversion.cellYToCoord(y), Game.cellSize, Game.cellSize);
            }
        }

        if (!Game.currentBlock.isInAnimation()) {
            Block imaginaryBlock = new Block(Game.currentBlock.getType(),Game.currentBlock.getX(), Game.currentBlock.getY(),Game.currentBlock.getSize(),
                    Game.currentBlock.getRotation(),Game.currentBlock.getBounds(),Game.currentBlock.getColor(),Game.currentBlock.isMovable());
            imaginaryBlock.setColor(Color.black);
            while (!Collision.wouldCollideWithWall(imaginaryBlock, Direction.DOWN, true) && !Collision.wouldCollideWithBlock(imaginaryBlock, Direction.DOWN, true)) {
                imaginaryBlock.setY(imaginaryBlock.getY() + 1);
            }
            g.setColor(Theme.text);
            for (int j = 0; j < imaginaryBlock.getBounds()[imaginaryBlock.getRotation()].length; j++) {
                for (int k = 0; k < imaginaryBlock.getBounds()[imaginaryBlock.getRotation()][j].length; k++) {

                    if (imaginaryBlock.getBounds()[imaginaryBlock.getRotation()][j][k] == 1) {
                        g.drawRect(Conversion.cellXToCoord(imaginaryBlock.getX() + j),
                                Conversion.cellYToCoord(imaginaryBlock.getY() + k), Game.cellSize, Game.cellSize);
                    }
                }
            }
        }

        Slider.slider.setBounds(Conversion.cellXToCoord(11)-10, Game.gameFieldYmin + 340, 130, 100);
        Slider.slider.setVisible(true);

        g.setColor(Theme.text);
        g.drawRect(Conversion.cellXToCoord(0), Conversion.cellYToCoord(0), Game.columnSize*Game.cellSize, Game.rowSize*Game.cellSize);
    }

}
