package utils;

import main.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {

    public static BufferedImage getImage (String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(FileLoader.getFile(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getRightArrow () {
        if (Main.theme == Theme.LIGHT) return getImage("images/arrow right.png");
        else return getImage("images/white arrow right.png");
    }

}
