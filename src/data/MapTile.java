package data;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapTile {
    private int[][] matrix;
    private int xMax, yMax;

    public MapTile(final int WIDTH, final int HEIGHT) {
        xMax = WIDTH / MainGame.TILE_WIDTH;
        yMax = HEIGHT / MainGame.TILE_HEIGHT;
        matrix = new int[xMax][yMax];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                if (j == 4 && i == 2)
                    matrix[i][j] = TileType.Water.ordinal();
                else if ((j == 5 || j == 4) && (i == 2 || i == 3))
                    matrix[i][j] = TileType.Water.ordinal();
                else if ((j == 5 || j == 4) && (i == 6 || i == 7))
                    matrix[i][j] = TileType.Water.ordinal();
                else
                    matrix[i][j] = TileType.Dirt.ordinal();
            }
        }
    }

    private Image getImage(int tileIndex) throws SlickException {
        Image image;
        String path = "images/" + TileType.values()[tileIndex] + ".png";
        image = new Image(path);

        return image;
    }

    public void render(Graphics g) throws SlickException {
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                g.drawImage(getImage(matrix[i][j]), MainGame.TILE_WIDTH * i + i, MainGame.TILE_HEIGHT * j + j,
                        MainGame.TILE_WIDTH * i + i + MainGame.TILE_WIDTH,
                        MainGame.TILE_HEIGHT * j + j + MainGame.TILE_HEIGHT, 0, 0, MainGame.TILE_WIDTH,
                        MainGame.TILE_HEIGHT);
            }
        }
    }

    public void update() {

    }

}
