package utils;

import ui.Gui;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class Utils {

    public static void drawInMiddle (Graphics2D g, String text, int dx, int dy) {
        FontMetrics fm = g.getFontMetrics();
        int x = (Gui.width - fm.stringWidth(text)) / 2;
        int y = ((Gui.height - fm.getHeight()) / 2) + fm.getHeight();
        g.drawString(text, x+dx, y+dy);
    }

    public static void drawInMiddle (Graphics2D g, String text, int dx, int dy, Color outline, float width) {

        Graphics2D g2d = (Graphics2D) g.create();

        Color orr = g2d.getColor();
        FontMetrics fm = g2d.getFontMetrics();
        int x = (Gui.width - fm.stringWidth(text) ) / 2;
        int y = ((Gui.height - fm.getHeight() - (int) width) / 2) + fm.getHeight();
        g2d.translate(x+dx, y+dy);
        g2d.setColor(outline);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(text, g2d.getFont().deriveFont(g2d.getFont().getSize()-width), frc);
        Shape shape = tl.getOutline(null);
        g2d.setStroke(new BasicStroke(width));
        g2d.draw(shape);
        g2d.setColor(orr);
        g2d.fill(shape);


    }

    public static int  getCentralX () {
        return (Gui.width) / 2;
    }
    public static int  getCentralY () {
        return (Gui.height) / 2;
    }

}
