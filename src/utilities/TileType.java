package utilities;

public enum TileType {

    Dirt("dirt", true), Water("water", false), Null("null", false);

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
