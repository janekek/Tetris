package ui;

import game.Game;
import utils.Theme;
import utils.Utils;

import java.awt.*;

public class DrawMenu {

    public static void drawPause(Graphics2D g) {
        g.setColor(new Color(100, 100, 100, 90));
        g.fillRect(0, Gui.height / 2 - 50, Gui.width, 100);
        g.fillRect(Gui.width / 2 - 200, Gui.height / 2 + 50, 400, 15);
        g.setColor(Theme.text);
        g.setFont(Gui.getPixelfont(14f));
        Utils.drawInMiddle(g, "RESTART ", 100, 45);
        Utils.drawInMiddle(g, "BACK TO MENU ", -100, 45);
        g.setFont(Gui.getPixelfont(25f));
        Utils.drawInMiddle(g, "PRESS ESC TO CONTINUE", 0, 0);

        if (Game.countToStart >= 1 && Game.countToStart <= 3) {
            g.setColor(Theme.text);
            g.setFont(Gui.getPixelfont(70f));
//                Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 150);
            Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 150, Theme.background, 10f);
        }
    }

    public static void drawGameover(Graphics2D g) {
        g.setColor(new Color(175, 28, 28));
        g.fillRect(0, Gui.height / 2 - 50, Gui.width, 100);
        g.fillRect(Gui.width / 2 - 200, Gui.height / 2 + 49, 400, 15);
        g.setColor(Color.WHITE);
        g.setFont(Gui.getPixelfont(14f));
        Utils.drawInMiddle(g, "RESTART ", 100, 45);
        Utils.drawInMiddle(g, "BACK TO MENU ", -100, 45);
        g.setFont(Gui.getPixelfont(22f));
        Utils.drawInMiddle(g, "YOU LOST. PRESS ENTER TO START AGAIN", 0, 0);

        if (Game.countToStart >= 1 && Game.countToStart <= 3) {
            g.setColor(Theme.text);
            g.setFont(Gui.getPixelfont(70f));
            Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 150, Theme.background, 10f);
//                Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 150);
        }
    }

    public static void drawWon(Graphics2D g) {
        g.setColor(new Color(60, 175, 28));
        g.fillRect(0, Gui.height / 2 - 50, Gui.width, 100);
        g.fillRect(Gui.width / 2 - 200, Gui.height / 2 + 49, 400, 15);
        g.setColor(Theme.textBackground);
        g.setFont(Gui.getPixelfont(14f));
        Utils.drawInMiddle(g, "RESTART ", 100, 45);
        Utils.drawInMiddle(g, "BACK TO MENU ", -100, 45);
        g.setFont(Gui.getPixelfont(22f));
        Utils.drawInMiddle(g, "YOU WON! PRESS ENTER TO START AGAIN", 0, 0);

        if (Game.countToStart >= 1 && Game.countToStart <= 3) {
            g.setColor(Theme.text);
            g.setFont(Gui.getPixelfont(70f));
            Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 150, Theme.background, 10f);
//                Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 150);
        }
    }

}
