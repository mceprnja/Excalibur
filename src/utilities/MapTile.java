package utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapTile implements IMapTile {
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
        
    public int getTileColor(int mouseX, int mouseY){
    	int xTile = getTileX(mouseX);
    	int yTile = getTileY(mouseY);
    	int tileColor = matrix[xTile][yTile][2];
    	
    	return tileColor;
    }
    
    public int getTileX(int mouseX) {
        int xTile = (int) Math.floor(mouseX / (Consts.TILE_WIDTH + 1)); // padding
        if (xTile >= xMax)
            xTile = 0;
        return xTile;
    }

    public int getTileY(int mouseY) {
        int yTile =  (int) Math.floor(mouseY / (Consts.TILE_HEIGHT + 1)); // padding
        if (yTile >= xMax)
            yTile = 0;
        return yTile;
    }

    public TileType getTileType(int mouseX, int mouseY) {
        return TileType.values()[matrix[getTileX(mouseX)][getTileY(mouseY)][0]];
    }

    public void setFigureAt(int mouseX, int mouseY, int figure, int figureColor) {
    	boolean isRed = figureColor == 0 ? true : false;
        int xTile =  (int) Math.floor(mouseX / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(mouseY / (Consts.TILE_HEIGHT + 1)); // padding
        if (xTile >= xMax || yTile >= yMax) {
        	System.out.println("OUT");
            return;
        }
               
        this.changeTileBackground(xTile, yTile, true, isRed);
        matrix[xTile][yTile][1] = figure;
    }
    
    public int setFigureAt(int mouseX, int mouseY, int oldX, int oldY, int oldTileColor, FigureType currentFigure) {
    	boolean isRed = oldTileColor == 0 ? true : false;
        int xTile =  (int) Math.floor(mouseX / (Consts.TILE_WIDTH + 1)); // padding
        int yTile =  (int) Math.floor(mouseY / (Consts.TILE_HEIGHT + 1)); // padding
        int xOldTile = getTileX(oldX);
        int yOldTile = getTileY(oldY);
        int futureTileColor = getTileColor(oldX, oldY);
        FigureType figureAtFutureTile = getFigureAt(mouseX, mouseY);
        //        
        if (xTile >= xMax || yTile >= yMax || futureTileColor == oldTileColor || currentFigure == null) { // 
        	System.out.println("OUT");
            return -100;
        }

        if(matrix[xTile][yTile][1] != -1){	
//        	System.out.println("Pokolj");
        	int fightResolver = Figure.resolveFight(currentFigure, figureAtFutureTile);
        	System.out.println("Fight resolver : " + fightResolver);
        	
        	if(fightResolver == 100) {
        		System.out.println("IMAMO POBJEDNIKA");
        		this.changeTileBackground(xTile, yTile, true, isRed);
        		return 100;
        	} else if (fightResolver == 0) {
        		matrix[xTile][yTile][1] = -1;
        		this.changeTileBackground(xTile, yTile, false, false);
        		return 0;
        	} else if (fightResolver == 1) {
        		matrix[xTile][yTile][1] = currentFigure.getIndex();
        		this.changeTileBackground(xTile, yTile, true, isRed);
        		return 1;
        	} else {
        		matrix[xTile][yTile][1] = figureAtFutureTile.getIndex();
        		this.changeTileBackground(xTile, yTile, true, !isRed);
        		return 2;
        	}
        } else {
        	this.changeTileBackground(xTile, yTile, true, isRed);
            matrix[xTile][yTile][1] = currentFigure.getIndex();
            return 5;
        }        
    }
    
    public void clearTile(int mouseX, int mouseY) {
    	int xTile =  getTileX(mouseX);; // padding
        int yTile =  getTileY(mouseY); // padding
        
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
    
    public boolean canPlaceAt(int mouseX, int mouseY, int currentTileColor, int futureTileColor) {
    	int xTile =  getTileX(mouseX);; // padding
        int yTile =  getTileY(mouseY); // padding
        
        if (xTile >= xMax || yTile >= yMax) {
            return false ;
        }
        
        return (!(matrix[xTile][yTile][0] == TileType.Water.ordinal()) && (matrix[xTile][yTile][1] == -1 || currentTileColor != futureTileColor));
    }
    
    public boolean canPlaceAt(int mouseX, int mouseY, boolean isSetting) { //if it is setting part they cant go on each other
        int xTile =  getTileX(mouseX);; // padding
        int yTile =  getTileY(mouseY); // padding
        
        if (xTile >= xMax || yTile >= yMax) {
            return false ;
        }
        
        return (!(matrix[xTile][yTile][0] == TileType.Water.ordinal()) && matrix[xTile][yTile][1] == -1);
    }
    
    public boolean canPlaceAtWithTileIndex(int tileX, int tileY) {
    	return (!(matrix[tileX][tileY][0] == TileType.Water.ordinal())); // && matrix[tileX][tileY][1] == -1
    }

    public FigureType getFigureAt(int mouseX, int mouseY) {
        FigureType type = null;
        if (matrix[getTileX(mouseX)][getTileY(mouseY)][1] < 0) {
            type = FigureType.Null;
        } else {
            type = FigureType.values()[matrix[getTileX(mouseX)][getTileY(mouseY)][1]];
        }

        return type;
    }
    
    public void drawFigure(int x, int y, FigureType figure) {
        
    }

    public void update() {

    }
}
