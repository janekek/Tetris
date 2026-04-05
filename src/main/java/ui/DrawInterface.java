package ui;

import data.Conversion;
import game.*;
import io.DataHandler;
import utils.Theme;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Locale;

public class DrawInterface {

    public static void paintBlock (Graphics2D g, Block b, int size, double posX, double posY) {
        ArrayList<Dimension> list = new ArrayList<>();
        for (int x = 0; x < b.getBounds()[b.getRotation()].length; x++) {
            for (int y = 0; y < b.getBounds()[b.getRotation()][x].length; y++) {
                if (b.getBounds()[b.getRotation()][x][y] == 1) {
                    list.add(new Dimension(x, y));
                }
            }
        }
        int maxY = 0;
        for (Dimension d : list) {
            if (d.getWidth() > maxY) maxY = (int) d.getWidth();
        }
        BufferedImage bufferedImage = b.getImage(size, size);
        if (Game.gamestate != GameState.INGAME) {
            RescaleOp op = new RescaleOp(0.6f, 0, null);
            bufferedImage = op.filter(bufferedImage, null);
        }
        for (Dimension d : list) {
            double dx = 0;
            double dy = 0;
            switch (maxY) {
                case 3 -> dx = 0;
                case 2 -> dx = 0.5;
                case 1 -> dx = 1;
            }
            if (b.getType() == BlockType.CYAN) dy = -0.5;
            g.drawImage(bufferedImage, (int) (Conversion.cellXToCoord(10)+25 + (posX + d.getWidth() + dx)*(size+1)), (int) (Game.gameFieldYmin + (posY + d.getHeight() + dy)*(size+1)), null);
        }
    }

    public static void drawInterface(Graphics2D g) {

        g.setColor(Theme.text);
        g.setFont(Gui.getPixelfont(12f));

        g.drawString("NEXT BLOCKS: ", Conversion.cellXToCoord(11) , Game.gameFieldYmin + 50);
        paintBlock(g, Game.nextBlock, 24, 0.5, 3);
        paintBlock(g, Game.nextBlock2, 24, 0.5, 6);
        paintBlock(g, Game.nextBlock3, 24, 0.5, 9);
        g.drawString("SOUND:", Conversion.cellXToCoord(11), Game.gameFieldYmin + 360);
        g.drawString("HOLD:", Conversion.cellXToCoord(11), Game.gameFieldYmin + 430);
        if (Game.holdenBlock != null) {
            paintBlock(g, Game.holdenBlock, 15, 4, 25.5);
        }
        g.drawString("LEVEL: " + Game.level, Conversion.cellXToCoord(11), Game.gameFieldYmin + 470);
        g.drawString("SCORE: " + Game.score, Conversion.cellXToCoord(11), Game.gameFieldYmin + 510);
        g.drawString("BEST: " + DataHandler.getHighScore(Game.gameMode), Conversion.cellXToCoord(11), Game.gameFieldYmin + 550);
        g.drawString(Game.gameMode.getName().toUpperCase(Locale.ROOT), Conversion.cellXToCoord(11), Game.gameFieldYmin + 590);

        g.drawString("LAST SCORES: ", Conversion.cellXToCoord(-4)-Game.cellSize/2 , Game.gameFieldYmin + 50);

        FontMetrics fm = g.getFontMetrics();
        for (int i = 0; i < DataHandler.getScores(Game.gameMode).size(); i++) {
            int x = ((660 / 2 - ((Game.columnSize / 2) * Game.cellSize)) - fm.stringWidth(DataHandler.getScores(Game.gameMode).get(DataHandler.getScores(Game.gameMode).size()-i-1))) / 2;
            g.drawString(DataHandler.getScores(Game.gameMode).get(DataHandler.getScores(Game.gameMode).size()-i-1),  x + (Gui.width - 660) / 2 , Game.gameFieldYmin + 90 + 30*i);
        }
    }

}