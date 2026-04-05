package game;

import java.util.concurrent.ThreadLocalRandom;

public enum BlockType {

    GREEN, YELLOW, BLUE, CYAN, PURPLE, RED, ORANGE;

    static final boolean testMode = false;

    public static BlockType random () {
        if (testMode) return BlockType.CYAN;
        return values()[ThreadLocalRandom.current().nextInt(0, values().length)];
    }

}
