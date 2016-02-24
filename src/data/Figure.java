package data;

public abstract class Figure {
    protected boolean alive;

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }
}
