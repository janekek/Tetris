package game;

import utils.FileLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Block implements Cloneable{

    BlockType type;
    int x, y, size, rotation = 0;

    int [] [] [] bounds;
    Color color;
    boolean movable = true;
    boolean inAnimation = false;

    public Block(BlockType type, int x, int y, int size, int rotation, int[][][] bounds, Color color, boolean movable) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.size = size;
        this.rotation = rotation;
        this.bounds = bounds;
        this.color = color;
        this.movable = movable;
    }

    public Block() {
        type = BlockType.random();
        switch (type) {
            case CYAN -> {
                color = Color.cyan;
                size = 4;
            }
            case YELLOW -> {
                color = Color.yellow;
                size = 2;
            }
            case BLUE -> {
                color = Color.BLUE;
                size = 3;
            }
            case ORANGE -> {
                color = Color.ORANGE;
                size = 3;
            }
            case GREEN -> {
                color = Color.GREEN;
                size = 3;
            }
            case PURPLE -> {
                color = Color.MAGENTA;
                size = 3;
            }
            case RED -> {
                color = Color.RED;
                size = 3;
            }
            default -> {
            }
        }
        x = 4;
        y = -2;

        bounds = blockBuilder(type);

    }

    public Block clone () {
        try {
            return (Block) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new Block();
        }
    }

    public static BufferedImage getImage (int height, int width, String colour) {
        BufferedImage bi;
        ImageIcon ii = new ImageIcon(Objects.requireNonNull(FileLoader.getFile("images/" + colour + " block.png")).getAbsolutePath());
        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(ii.getImage(), 0, 0, width, height, null);
        return bi;
    }
    public BufferedImage getImage (int height, int width) {
        ImageIcon ii = new ImageIcon(Objects.requireNonNull(FileLoader.getFile("images/" + type.name().toLowerCase(Locale.ROOT) + " block.png")).getAbsolutePath());
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(ii.getImage(), 0, 0, width, height, null);
        return bi;
    }

    public static BufferedImage getPattern (int height, int width, String colour) {
        ImageIcon ii = new ImageIcon(Objects.requireNonNull(FileLoader.getFile("images/" + colour + " pattern.png")).getAbsolutePath());
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(ii.getImage(), 0, 0, width, height, null);
        return bi;
    }

    public void rotate() {
        rotation++;
        if (rotation == 4) rotation = 0;
    }

    public int[][][] blockBuilder(BlockType type)  {

        int[][][] bounds = switch (type) {
            case CYAN -> new int[4][4][4];
            case YELLOW -> new int[4][2][2];
            default -> new int[4][3][3];
        };

        File file = FileLoader.getFile("blocks/" + type.name().toLowerCase() + ".txt");
        Scanner sc = null;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int variant = 0; variant < 4; variant++) {
            for (int i = 0; i < bounds[0].length; i++) {

                assert sc != null;
                if (sc.hasNext()) {
                    String[] srow = sc.next().split("");
                    int[] row = new int[bounds[0].length];

                    for (int j = 0; j < row.length; j++) {
                        row[j] = Integer.parseInt(srow[j]);
                        bounds[variant][j][i] = row[j];

                    }
                }
            }
        }
        return bounds;
    }

    public static int getTypeValue(String color) {
        return switch (color.toUpperCase()) {
            case "CYAN" -> 1;
            case "YELLOW" -> 2;
            case "PURPLE" -> 3;
            case "ORANGE" -> 4;
            case "BLUE" -> 5;
            case "RED" -> 6;
            case "GREEN" -> 7;
            default -> 0;
        };
    }

    public static BlockType getColorValue(int typeValue) {
        return switch (typeValue) {
            case 1 -> BlockType.CYAN;
            case 2 -> BlockType.YELLOW;
            case 3 -> BlockType.PURPLE;
            case 4 -> BlockType.ORANGE;
            case 5 -> BlockType.BLUE;
            case 6 -> BlockType.RED;
            case 7 -> BlockType.GREEN;
            default -> null;
        };
    }

    public int getTypeValue() {
        return switch (type) {
            case CYAN -> 1;
            case YELLOW -> 2;
            case PURPLE -> 3;
            case ORANGE -> 4;
            case BLUE -> 5;
            case RED -> 6;
            case GREEN -> 7;
        };
    }

    public boolean isInAnimation() {
        return inAnimation;
    }

    public void setInAnimation(boolean inAnimation) {
        this.inAnimation = inAnimation;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int[][][] getBounds() {
        return bounds;
    }

    public void setBounds(int[][][] bounds) {
        this.bounds = bounds;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }
}
