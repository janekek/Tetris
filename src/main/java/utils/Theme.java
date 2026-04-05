package utils;

import io.DataHandler;
import main.Main;
import ui.Gui;

import java.awt.*;

public enum Theme {

    LIGHT, DARK;

    public static Color background, text, textBackground, gitter;

    public String getName () {
        String s = null;
        switch (this) {
            case LIGHT -> s = "Light";
            case DARK -> s = "Dark";
        }
        return s;
    }

    public int getValue () {
        int i = -1;
        switch (this) {
            case LIGHT -> i = 0;
            case DARK -> i = 1;
        }
        return i;
    }

    public Theme next() {
        return Theme.values()[(ordinal() + 1) % Theme.values().length];
    }

    public static void updateTheme () {
        DataHandler.saveSomething("data/theme.txt", Main.theme.getValue());
        if (Main.theme == LIGHT) {
            background = new Color(247, 247, 247);
            text = Color.BLACK;
            textBackground = Color.WHITE;
            gitter = Color.lightGray;
        } else if (Main.theme == DARK) {
            background = new Color(30, 30, 30);
            text = Color.WHITE;
            textBackground = Color.BLACK;
            gitter = new Color(52, 52, 52);
        }
        Gui.jf.getContentPane().setBackground(background);
    }
}
