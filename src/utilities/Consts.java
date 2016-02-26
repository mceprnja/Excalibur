package utilities;

public final class Consts {

    public static final int TILE_HEIGHT = 50;
    public static final int TILE_WIDTH = 50;
    public static final int SCREEN_WIDTH = TILE_HEIGHT * 10 + 6 * TILE_WIDTH + 10; // padding + 6 columns for garrison 
    public static final int SCREEN_HEIGHT = TILE_WIDTH * 10 + 10; // padding 
    
    public Consts() {
        throw new AssertionError("Can't instantiate this object!");
    }
}
