package ui;

import data.Conversion;
import game.Block;
import game.Game;
import io.DataHandler;
import io.MouseMotionListener;
import io.Slider;
import main.Main;
import utils.ImageLoader;
import utils.Scheduler;
import utils.Theme;
import utils.Utils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;
import java.util.Random;

public class DrawStartMenu extends Draw{

    public static Block b;
    public static RoundRectangle2D enterGameButton = new RoundRectangle2D.Float(Utils.getCentralX()-175, Utils.getCentralY()-25, 350, 50, 10, 10);
    public static Rectangle changeGameModeButton = new Rectangle(Gui.width/2+190, Gui.height/2+55, 20,40);
    public static Rectangle changeTheme = new Rectangle(Gui.width/2+190, Gui.height/2-95, 20,40);

    public static void drawMenu(Draw draw, Graphics2D g) {
        g.setFont(Gui.getPixelfont(18f));

        //Buttons
//        Button.changeGameModeButton.getButton().setVisible(true);

        //Random Block
        DrawUtils.paintBlock(g, b, Game.cellSize, b.getX(), b.getY());

        //Blured Tetris field
        g.setColor(Theme.text);
        g.drawRect(Conversion.cellXToCoord(0), Conversion.cellYToCoord(0), Game.columnSize*Game.cellSize, Game.rowSize*Game.cellSize);
        g.setColor(Theme.gitter);
        for (int x = 0; x < Game.columnSize; x++) {
            for (int y = 0; y < Game.rowSize; y++) {
                g.drawRect(Conversion.cellXToCoord(x), Conversion.cellYToCoord(y), Game.cellSize, Game.cellSize);
            }
        }

        //Tetris Logo
        g.drawImage( ImageLoader.getImage("images/TetrisLogo.png"), (Gui.width - Objects.requireNonNull(ImageLoader.getImage("images/TetrisLogo.png")).getWidth(null))/2,
                100, draw);

        //Press enter
        g.setColor(Theme.text);
        g.fill(enterGameButton);
        g.setColor(Theme.textBackground);
        Utils.drawInMiddle(g, "PRESS ENTER TO START", 0, 0);

        //GameModeSelection
        g.setColor(Theme.text);
        Utils.drawInMiddle(g, "Gamemode:", -100, 75);
        Utils.drawInMiddle(g, Game.gameMode.getName(),  100, 75);
        if (MouseMotionListener.hoverChangeGameModeButton) {
            g.drawImage(Objects.requireNonNull(ImageLoader.getRightArrow()), Gui.width/2+177, Gui.height/2+52, 46, 46,  null);
        } else g.drawImage(Objects.requireNonNull(ImageLoader.getRightArrow()), Gui.width/2+180, Gui.height/2+55, 40, 40,  null);

        //Slider
        Slider.slider.setBounds((Gui.width/2) - 100, Gui.height-120, 200, 100);
        Slider.slider.setVisible(true);

        //Credits
        Utils.drawInMiddle(g, "programmed by Janek Zeiger", 0, -130);

        //Date
        Utils.drawInMiddle(g, "May 2022", 250, 300);

        //White-DarkMode
        Utils.drawInMiddle(g, "Theme:", -100, -75);
        Utils.drawInMiddle(g, Main.theme.getName(),  100, -75);
        if (MouseMotionListener.hoverThemeButton) {
            g.drawImage(Objects.requireNonNull(ImageLoader.getRightArrow()), Gui.width/2+177, Gui.height/2-98, 46, 46,  null);
        } else g.drawImage(Objects.requireNonNull(ImageLoader.getRightArrow()), Gui.width/2+180, Gui.height/2-95, 40, 40,  null);

        //HighScore
        Utils.drawInMiddle(g, "Highscore:", -100, 130);
        Utils.drawInMiddle(g, String.valueOf(DataHandler.getHighScore(Game.gameMode)),  100, 130);

        //3, 2, 1
        if (Game.countToStart >= 1 && Game.countToStart <= 3) {
            g.setColor(Theme.text);
            g.setFont(Gui.getPixelfont(70f));
            Utils.drawInMiddle(g, "" + (4-Game.countToStart), 0, 200, Theme.background, 10f);
        }

    }

    public static void doScheduler () {
        Scheduler scheduler = new Scheduler();
        scheduler.scheduleWithFixedDelay(() -> {
                boolean repeat = true;
                while (repeat) {
                    repeat = false;
                    Random r = new Random();
                    b = new Block();
                    b.setX(r.nextInt(0, Game.columnSize-2));
                    b.setY(r.nextInt(Game.rowSize-4, Game.rowSize-1));
                    b.setRotation(r.nextInt(0, 3));
                    for (int x = 0; x < b.getBounds()[b.getRotation()].length; x++) {
                        for (int y = 0; y < b.getBounds()[b.getRotation()][x].length; y++) {
                            if (b.getBounds()[b.getRotation()][x][y] == 1) {
                                if (b.getY()+y >= Game.rowSize || b.getX()+x >= Game.columnSize) {
                                    repeat = true;
                                }
                            }
                        }
                    }
                }
        }, 0, 2);
    }

}