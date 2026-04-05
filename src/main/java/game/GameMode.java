package game;

public enum GameMode {

    CLASSIC, LINEBREAKER;

    public String getName () {
        String s = null;
        switch (this) {
            case CLASSIC -> s = "Classic";
            case LINEBREAKER -> s = "Linebreaker";
        }
        return s;
    }

    public GameMode next() {
        return GameMode.values()[(ordinal() + 1) % GameMode.values().length];
    }

}

