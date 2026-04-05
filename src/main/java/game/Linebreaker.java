package game;

import ui.DrawUtils;

import java.util.Random;

public class Linebreaker {

    public static void createGameField () {
        Random r = new Random();
        for (int x = 0; x < Game.columnSize; x++) {
            for (int y = Game.rowSize-10; y < Game.rowSize; y++) {
                if (r.nextInt(0,2) == 1) {
                    int typeValue = Block.getTypeValue(DrawUtils.colors.get(r.nextInt(0, DrawUtils.colors.size())));
                    Game.map[x][y] = typeValue;
                }
            }
        }
    }
}
