package ui;

import game.Game;
import io.*;
import io.Button;
import io.KeyListener;
import io.MouseListener;
import io.MouseMotionListener;
import io.MouseWheelListener;
import utils.ComponentResizer;
import utils.FileLoader;
import utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class Gui extends JPanel {

    private static final int FPS = 165;
    public static int currentFPS = 0;
    public static int frames = 0;
    public static int width = 660, height = 750;
    public static JFrame jf;
    public static Draw d;

    public void create() throws IOException, FontFormatException {
        jf = new JFrame("Tetris");

        Theme.updateTheme();

        jf.setLayout(null);
        jf.setUndecorated(true);
        jf.pack();
        jf.setSize(width, height);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(true);

        jf.add(Button.restartButton.getButton());
        jf.add(Button.backToMenu.getButton());
        jf.add(Button.continuePause.getButton());
        jf.add(Button.closeButton.getButton());
        jf.add(Slider.getMusicSlider(32*11, 340, 100, 140));
        jf.addMouseListener(new MoveWindow());
        jf.addMouseMotionListener(new MoveWindow());
        jf.addKeyListener (new KeyListener());
        jf.addMouseListener(new MouseListener());
        jf.addMouseWheelListener(new MouseWheelListener());
        jf.addMouseMotionListener(new MouseMotionListener());
        jf.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                Gui.width = c.getWidth();
                Gui.height = c.getHeight();
                Game.initializeGameFieldCoordinates();
            }
        });

        DrawStartMenu.doScheduler();

        d = new Draw();
        setupDraw(d, width, height);

        ComponentResizer cr = new ComponentResizer();
        cr.registerComponent(jf);
        cr.setSnapSize(new Dimension(10, 10));
        cr.setMinimumSize(new Dimension(width, height));

        jf.requestFocus();
        jf.setVisible(true);

        startFrameLoop(d);

    }

    private void setupDraw(JLabel draw, int width, int height) {
        draw.setBounds(0, 0, width, height);
        draw.setVisible(true);
        jf.add(draw);
    }

    public static Graphics2D getG2D (Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2;
    }

    public static Font getPixelfont (float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FileLoader.getFile("fonts/pixelfont.TTF"))).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startFrameLoop (Draw draw) {
        Thread thread = new Thread(() -> {
            double timePerFrame = 1000000000.0 / FPS;
            long lastFrame = System.nanoTime();
            long lastCheck = System.currentTimeMillis();
            while (true) {
                if (System.nanoTime() - lastFrame >= timePerFrame) {
                    draw.repaint();
                    lastFrame = System.nanoTime();
                    frames++;
                }
                if (System.currentTimeMillis() - lastCheck >= 1000) {
                    lastCheck = System.currentTimeMillis();
                    currentFPS = frames;
                    frames = 0;
                }
            }
        });
        thread.start();
    }

}