package utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapTile {
    private int[][][] matrix;
    
    private final int xMax = 10,  yMax = 10;
    private Image image;

    public MapTile() {
    	
        matrix = new int[xMax][yMax][3];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                if (j == 4 && i == 2)
                    matrix[i][j][0] = TileType.Water.ordinal();
                else if ((j == 5 || j == 4) && (i == 2 || i == 3))
                    matrix[i][j][0] = TileType.Water.ordinal();
                else if ((j == 5 || j == 4) && (i == 6 || i == 7))
                    matrix[i][j][0] = TileType.Water.ordinal();
                else
                    matrix[i][j][0] = TileType.Dirt.ordinal();
                matrix[i][j][1] = -1;
                matrix[i][j][2] = -1; //-1 empty, 0 red, 1 blue
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
                int xPos = Consts.TILE_WIDTH * i + i;
                int yPos = Consts.TILE_HEIGHT * j + j;
                int x2 = xPos + Consts.TILE_WIDTH;
                int y2 = yPos + Consts.TILE_HEIGHT;
                g.drawImage(getImage(matrix[i][j][0]), xPos, yPos, x2, y2, 0, 0, Consts.TILE_WIDTH, Consts.TILE_HEIGHT);
                
                if (matrix[i][j][1] != -1) {
                    image = new Image(FigureType.values()[matrix[i][j][1]].getPath());
                    
                    g.drawImage(image, xPos, yPos, xPos + Consts.TILE_WIDTH, yPos + Consts.TILE_HEIGHT, 0, 0,
                            image.getWidth(), image.getHeight());
                }
            }
        }

    }
    
    private void changeTileBackground(int tileX, int tileY, boolean isSetting, boolean isRed) {
    	if(isSetting) {
    		if(isRed) {
    			matrix[tileX][tileY][0] = TileType.DirtRed.ordinal();
    			matrix[tileX][tileY][2] = 0;
    		} else {
    			matrix[tileX][tileY][0] = TileType.DirtBlue.ordinal();
    			matrix[tileX][tileY][2] = 1;
    		}
    	} else {
    		matrix[tileX][tileY][0] = TileType.Dirt.ordinal();
    		matrix[tileX][tileY][2] = -1;
    	}
    }
    
    public int getTileColorBeforeMoving(int mouseX, int mouseY){
    	int tileX = getTileX(mouseX);
    	int tileY = getTileY(mouseY);
    	
//    	System.out.println(matrix[tileX][tileY][0]);
    	return matrix[tileX][tileY][0];
    }
    
    public int getTileColor(int mouseX, int mouseY){
    	int xTile = getTileX(mouseX);
    	int yTile = getTileY(mouseY);
    	int tileColor = matrix[xTile][yTile][2];
    	System.out.println("Tile color: " + tileColor);
    	
    	return tileColor;
//    	if(tileColor == 3){
//    		return 0;
//    	} else if (tileColor == 4) {
//    		return 1;
//    	} else {
//    		return -1;
//    	}
    }
    
    public int getTileX(int x) {
        int xTile = (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        if (xTile >= xMax)
            xTile = 0;
        return xTile;
    }

    public int getTileY(int y) {
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        if (yTile >= xMax)
            yTile = 0;
        return yTile;
    }

    public TileType getTileType(int x, int y) {
        return TileType.values()[matrix[getTileX(x)][getTileY(y)][0]];
    }

    public void setFigureAt(int x, int y, int figure) {
        int xTile =  (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        if (xTile >= xMax || yTile >= yMax) {
            return;
        }
        
        matrix[xTile][yTile][1] = figure;
    }
    
    public void setFigureAt(int x, int y, int figure, int figureColor) {
    	boolean isRed = figureColor == 0 ? true : false;
        int xTile =  (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        if (xTile >= xMax || yTile >= yMax) {
        	System.out.println("OUT");
            return;
        }
        
        System.out.println("xTile: " + xTile + " yTile " + yTile);
        
        this.changeTileBackground(xTile, yTile, true, isRed);
        matrix[xTile][yTile][1] = figure;
    }
    
    public void setFigureAt(int x, int y, int oldX, int oldY, int oldTileColor, FigureType currentFigure) {
    	boolean isRed = oldTileColor == 0 ? true : false;
        int xTile =  (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        int xOldTile = getTileX(oldX);
        int yOldTile = getTileY(oldY);
        int futureTileColor = getTileColor(oldX, oldY);
//        System.out.println(futureTileColor);
//        System.out.println(oldTileColor);
//        
        if (xTile >= xMax || yTile >= yMax || futureTileColor == oldTileColor) { // 
        	System.out.println("OUT");
            return;
        }
        
        
        
//        System.out.println("Figure je: "  + figure);
//        System.out.println("Staro polje je crveno" + exTileColor);
//        THIS PART SHOULD RESOLVE KILLING
//        if(newTileColor != exTileColor && figure != -1 && matrix[xTile][yTile][1] != -1){
////        	System.out.println("IDE POKOLJ");
//        	int figure1 = figure;
//        	int figure2 = matrix[xTile][yTile][1];
////        	System.out.println("Figure1 je: "  + figure1);
////        	System.out.println("Figure2 je: "  + figure2);
////        	
//        	if(figure1 > figure2) {
//        		isRed = true;
//        		figure = figure1;
//        		System.out.println("TU SAM");
//        	} else {
//        		isRed = false;
//        		xTile = xOldTile;
//        		yTile = yOldTile;
//        		figure = figure2;
//        		System.out.println("Evo me SAM");
//        	}
//        }
//        System.out.println("Figure je: "  + figure);
        this.changeTileBackground(xTile, yTile, true, isRed);
        matrix[xTile][yTile][1] = currentFigure.getIndex();
    }
    
    public void clearTile(int x, int y) {
        int xTile =  (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        if (xTile >= xMax || yTile >= yMax) {
            return;
        }
        this.changeTileBackground(xTile, yTile, false, false);
        matrix[xTile][yTile][1] = -1;
    }

    public void clearAll() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j][1] = -1;

                if(matrix[i][j][0] != TileType.Water.ordinal()){
                	matrix[i][j][0] = TileType.Dirt.ordinal();
                }
            }
        }
    }
    
    public boolean canPlaceAt(int x, int y, int currentTileColor, int exTileColor) {
        int xTile =  (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        
        if (xTile >= xMax || yTile >= yMax) {
            return false ;
        }
        
        System.out.println("Current tile color" + currentTileColor);
        System.out.println("Ex tile color" + exTileColor);
        
        return (!(matrix[xTile][yTile][0] == TileType.Water.ordinal()) && (matrix[xTile][yTile][1] == -1 || currentTileColor != exTileColor)); //&&  == 
    }
    
    public boolean canPlaceAt(int x, int y, boolean isSetting) { //if it is setting part they cant go on each other
        int xTile =  (int) Math.floor(x / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(y / (Consts.TILE_HEIGHT + 1)); // padding
        
        if (xTile >= xMax || yTile >= yMax) {
            return false ;
        }
        
        return (!(matrix[xTile][yTile][0] == TileType.Water.ordinal()) && matrix[xTile][yTile][1] == -1); //&& matrix[xTile][yTile][1] == -1
    }
    
    public boolean canPlaceAtWithTileIndex(int tileX, int tileY) {
    	return (!(matrix[tileX][tileY][0] == TileType.Water.ordinal())); // && matrix[tileX][tileY][1] == -1
    }

    public FigureType getFigureAt(int x, int y) {
        FigureType type = null;
        if (matrix[getTileX(x)][getTileY(y)][1] < 0) {
            type = FigureType.Null;
        } else {
            type = FigureType.values()[matrix[getTileX(x)][getTileY(y)][1]];
        }

        return type;
    }
    
    public void drawFigure(int x, int y, FigureType figure) {
        
    }

    public void update() {

    }

}
