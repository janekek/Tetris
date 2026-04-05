package game;

public class AnimatedBlock {

    private Block block;
    private Coordinate coordinate;
    private int count;

    public AnimatedBlock(Block block, Coordinate coordinate, int count) {
        this.block = block;
        this.coordinate = coordinate;
        this.count = count;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block b) {
        this.block = b;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
