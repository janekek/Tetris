package data;

import game.Game;

public class Conversion {

    public static int cellXToCoord(int cell) {
        return Game.gameFieldXmin + cell * Game.cellSize;
    }

    public static int cellYToCoord(int cell) {
        return Game.gameFieldYmin + cell * Game.cellSize;
    }

}
