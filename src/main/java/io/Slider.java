package io;

import game.Game;
import main.Main;
import utils.music.Music;
import ui.Gui;
import utils.Theme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

public class Slider {

    public static JSlider slider = new JSlider() {
        private SliderPopupListener popupHandler;
        @Override public void updateUI() {
            setUI(new CustomSliderUI(this));

            removeMouseMotionListener(popupHandler);
            removeMouseListener(popupHandler);
            removeMouseWheelListener(popupHandler);
            popupHandler = new SliderPopupListener();
            addMouseMotionListener(popupHandler);
            addMouseListener(popupHandler);
            addMouseWheelListener(popupHandler);
        }
    };

    public static JSlider getMusicSlider (int x, int y, int height, int width) {

        slider.setBounds(x, y, width, height);
        slider.setMajorTickSpacing(25);
        slider.addChangeListener(e -> {
            JSlider src = (JSlider) e.getSource();
            Main.masterVolume = src.getValue();
            Music.updateMusic();
            DataHandler.saveSomething("data/volume.txt", src.getValue());
        });
        slider.setValue((int) Main.masterVolume);
        slider.setFont(Gui.getPixelfont(12f));
        slider.setPaintLabels(false);
        slider.setBackground(Color.BLUE);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0") );
        labelTable.put(50, new JLabel("50") );
        labelTable.put(100, new JLabel("100") );
        slider.setLabelTable(labelTable);

        slider.setOpaque(false);
        slider.setFocusable(false);
        slider.setVisible(false);
        return  slider;
    }

    public static class CustomSliderUI extends BasicSliderUI {

        private static final Dimension THUMB_SIZE = new Dimension(20, 20);

        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g1) {
            Graphics2D g = Gui.getG2D(g1);

            Rectangle trackBounds = trackRect;

            if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
                int cy = (trackBounds.height / 2) - 2;
                int cw = trackBounds.width;

                g.translate(trackBounds.x, trackBounds.y + cy);

                g.setColor(Theme.background);
                g.drawLine(0, 3, cw, 3);
                g.drawLine(cw, 0, cw, 3);
                g.setColor(Theme.text);
                g.drawLine(1, 1, cw-2, 1);

                g.translate(-trackBounds.x, -(trackBounds.y + cy));
            }

        }

        @Override
        public void paintThumb(final Graphics g1) {
            Graphics2D g = Gui.getG2D(g1);
            g.setColor(Game.currentBlock.getColor());
            g.fillOval(thumbRect.x+4, thumbRect.y+4, thumbRect.width-8, thumbRect.height-8);
            g.setColor(Theme.text);
            g.drawOval(thumbRect.x+4, thumbRect.y+4, thumbRect.width-8, thumbRect.height-8);
        }
    }

    public static class SliderPopupListener extends MouseAdapter {
        private final JWindow toolTip = new JWindow();
        private final JLabel label = new JLabel("", SwingConstants.CENTER);
        private final Dimension size = new Dimension(40, 20);
        private int prevValue = -1;

        public SliderPopupListener() {
            super();
            toolTip.add(label);
            toolTip.setSize(size);
        }
        protected void updateToolTip(MouseEvent me) {
            JSlider slider = (JSlider) me.getComponent();
            int intValue = slider.getValue();
            if (prevValue != intValue) {
                label.setText(slider.getValue() + "%");
                Point pt = me.getPoint();
                pt.y = -size.height;
                SwingUtilities.convertPointToScreen(pt, me.getComponent());
                pt.translate(-size.width / 2, 42);
                toolTip.setLocation(pt);
            }
            prevValue = intValue;
        }
        @Override public void mouseDragged(MouseEvent me) {
            toolTip.setVisible(true);
            updateToolTip(me);
        }
        @Override public void mouseReleased(MouseEvent me) {
            toolTip.setVisible(false);
        }
    }

}
