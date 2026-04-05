package ui;

import data.Conversion;
import game.Block;
import game.Game;
import game.GameState;
import utils.ImageLoader;
import utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawUtils extends JLabel{

    public Graphics2D g;
    public static final ArrayList<String> colors = new ArrayList<>(Arrays.asList("cyan", "blue", "purple", "red", "orange", "yellow", "green"));

    @Override
    protected void paintComponent(Graphics g1) {
        Graphics2D g = Gui.getG2D(g1);
        super.paintComponent(g);
        this.setBounds(0, 0, Gui.width, Gui.height);

        DrawPatterns.drawPatterns(g);

    }

    public static void paintBlock (Graphics2D g, Block b, int size, double posX, double posY) {
        for (int x = 0; x < b.getBounds()[b.getRotation()].length; x++) {
            for (int y = 0; y < b.getBounds()[b.getRotation()][x].length; y++) {
                if (b.getBounds()[b.getRotation()][x][y] == 1) {
                    BufferedImage bufferedImage = b.getImage(size, size);
                    if (Game.gamestate != GameState.INGAME && Game.gamestate != GameState.MENU) {
                        RescaleOp op = new RescaleOp(0.6f, 0, null);
                        bufferedImage = op.filter(bufferedImage, null);
                    }
                    g.drawImage(bufferedImage, Conversion.cellXToCoord((int) (posX + x)), Conversion.cellYToCoord((int) (posY + y)), null);
                }
            }
        }
    }

    public static void drawFPS (Graphics2D g) {
        g.setColor(Theme.text);
        g.setFont(Gui.getPixelfont(12f));
        g.drawString("FPS: " + Gui.currentFPS, 10, 75);
    }

    public static void drawCloseButtonImage (Graphics2D g) {
        g.setColor(new Color(255, 0, 0));
        g.fillRect(Gui.width-50, 0, 50, 25);
        BufferedImage image = ImageLoader.getImage("images/close x.png");
        g.drawImage(image, Gui.width-50+15, 2, 20, 20, null);
    }

}
