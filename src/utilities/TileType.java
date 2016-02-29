package utilities;

public enum TileType {

    Dirt("dirt", true),
    Water("water", false),
    Null("null", false),
    DirtRed("dirt_red", true),
    DirtBlue("dire_blue", true);

    private String tileName;
    private boolean walkable;

    TileType(String tileName, boolean walkable) {
        this.tileName = tileName;
        this.walkable = walkable;
    };

    public String getTileName() {
        return tileName;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
