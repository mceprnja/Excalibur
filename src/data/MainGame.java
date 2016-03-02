package data;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.Consts;
import utilities.FigureType;
import utilities.IMapTile;
import utilities.MapTile;

public class MainGame extends BasicGame {
	
    private IMapTile mapTile = new MapTile();

    private Input input;
    private int[][] capacities = {{ 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 }, { 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 }};
    private int[][] remaining = {{ 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 }, { 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 }};
    private int[][] alive = {{ 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 }, { 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 }};
    private int indexOfFigureToPlace = 0;
    private boolean isRightPlayerPlaying;
    private boolean amIAllowedToPlaceAtThatTile = false;
    int figureToPlaceColor = 0; //0 red, 1 - blue
    int tileColorBeforeRemovingDelete = 0;
    
    private FigureType currentFigure = null;
    private int currentFigureTileColor;
    private boolean iscurrentFigureSwordOrDragon;
    private Image image;
    private int oldTileX, oldTileY;
    int mouseX, mouseY;
    boolean wasDown = false;
    boolean isGameFinished = false;
    boolean isInitialFigureSettingSillOn = true;
    

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Excalibur"));
            appgc.setDisplayMode(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(1000);
            appgc.setIcon("images/icon.png");
            // appgc.setAlwaysRender(true);
            // appgc.setClearEachFrame(false);
            
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainGame(String gamename) {
        super(gamename);
        
        
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    }
    

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        input = container.getInput();
//        System.out.println("TILE X " + mapTile.getTileX(input.getMouseX()) + " TILE Y " + mapTile.getTileY(input.getMouseY()));
        
        
        if (input.isKeyPressed(Input.KEY_1) && isInitialFigureSettingSillOn) { //initial setting
            if (remaining[figureToPlaceColor][indexOfFigureToPlace] > 0) {
                int mouseX = input.getMouseX();
                int mouseY = input.getMouseY();
                if (mapTile.canPlaceAt(mouseX, mouseY, isInitialFigureSettingSillOn, figureToPlaceColor)) {
                    mapTile.setFigureAt(mouseX, mouseY, indexOfFigureToPlace, figureToPlaceColor);
                    remaining[figureToPlaceColor][indexOfFigureToPlace]--;
                    showRemaining();
                }
            }
        } else if (input.isKeyPressed(Input.KEY_2)) {
        	mouseX = input.getMouseX();
            mouseY = input.getMouseY();
        	FigureType currentFigure = mapTile.getFigureAt(mouseX, mouseY);
        	int figureIndex = currentFigure.getIndex();
        	
        	boolean isRedFigure = mapTile.getTileColor(mouseX, mouseY) == 0 ? true : false;
        	int colorIndex = isRedFigure ? 0 : 1;
            
        	
        	if(currentFigure != FigureType.Null && this.isRedTurn() == isRedFigure) {
        		if(remaining[colorIndex][figureIndex] < capacities[colorIndex][figureIndex]) {
        			mapTile.clearTile(mouseX, mouseY);
                    remaining[colorIndex][currentFigure.ordinal()]++;
                    showRemaining();
        		}
        	} else if(this.isRedTurn() != isRedFigure){
        		System.out.println("Nije tvoja boja pa ne mozes brisat to");
        	}
        } else if (input.isKeyPressed(Keyboard.KEY_TAB)) {
            indexOfFigureToPlace++;
            indexOfFigureToPlace %= remaining[figureToPlaceColor].length;

            System.out.println("Now placing " + FigureType.values()[indexOfFigureToPlace].getName());
        } else if (input.isKeyPressed(Keyboard.KEY_SPACE)) {
            showRemaining();
        } else if (input.isKeyPressed(Keyboard.KEY_R)) {
            mapTile.clearAll();
            isInitialFigureSettingSillOn = true;
            for (int i = 0; i < remaining.length; i++) {
            	for(int j = 0; j < remaining[figureToPlaceColor].length; j++) {
                    remaining[i][j] = capacities[i][j];
                    alive[i][j] = capacities[i][j];
            	}
            }
            showRemaining();
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            mouseX = input.getMouseX();
            mouseY = input.getMouseY();
            updateAmIAllowedToPlaceThere();
        } else if (input.isKeyPressed(Input.KEY_F1)){
        	figureToPlaceColor = ++figureToPlaceColor % 2;
        	if(figureToPlaceColor == 0){
        		System.out.println("Placing red now");
        	} else {
        		System.out.println("Placing blue now");
        	}	
        } else if (input.isKeyPressed(Input.KEY_F2)){
        	isInitialFigureSettingSillOn = false;
        }
        
        
        
        
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
        	boolean isRedFigure = mapTile.getTileColor(mouseX, mouseY) == 0 ? true : false;
        	oldTileX = mouseX;
            oldTileY = mouseY;
            currentFigure = mapTile.getFigureAt(mouseX, mouseY);
            currentFigureTileColor = mapTile.getTileColor(mouseX, mouseY);
            
            this.iscurrentFigureSwordOrDragon = (currentFigure == FigureType.Dragon || currentFigure == FigureType.Sword) ? true : false;
            this.isRightPlayerPlaying = (isRedFigure == this.isRedTurn()) ? true : false;
            
            if(iscurrentFigureSwordOrDragon == false && isRightPlayerPlaying){
                mapTile.clearTile(oldTileX, oldTileY);
                
            } else {
            	System.out.println("Vjerojatno je ili mac ili zmaj ILI NIJE TVOJ RED");
            }
            
            wasDown = true;
        }
        
        if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && wasDown && !iscurrentFigureSwordOrDragon) {
//        	boolean amIOnSamePlace = (mapTile.getTileX(mouseX) == mapTile.getTileX(oldTileX) && mapTile.getTileY(mouseY) == mapTile.getTileY(oldTileY)) ? true : false;
        	int indexOfFigureAtNextTile = mapTile.getFigureAt(mouseX, mouseY).getIndex();
        			
        	if (currentFigure == FigureType.Null) {
                System.out.println("it was null");
            } else {
            	int futureTileColor = mapTile.getTileColor(mouseX, mouseY);

                if (mapTile.canPlaceAt(mouseX, mouseY, currentFigureTileColor, futureTileColor, isInitialFigureSettingSillOn) && amIAllowedToPlaceAtThatTile && isRightPlayerPlaying) {
                    int possibleFightResolver = mapTile.setFigureAt(mouseX, mouseY, oldTileX, oldTileY, currentFigureTileColor, currentFigure);
                    if(possibleFightResolver != 5) {
                    	if(possibleFightResolver == 100) {
                    		System.out.println("IMAMO POBJEDNIKA");
                    		container.sleep(700);
                    		isGameFinished = true;
                    	} else if (possibleFightResolver == 0) {
                    		alive[futureTileColor][indexOfFigureAtNextTile]--;
                    		alive[currentFigureTileColor][currentFigure.getIndex()]--;
                    	} else if (possibleFightResolver == 1) {
                    		alive[futureTileColor][indexOfFigureAtNextTile]--;
                    	} else {
                    		alive[currentFigureTileColor][currentFigure.getIndex()]--;
                    	}
                    } else {
                    	//do nothing
                    }
                } else {
                    mapTile.setFigureAt(oldTileX, oldTileY, currentFigure.ordinal(), currentFigureTileColor);
                }
            }
            wasDown = false;
            currentFigure = null;
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	if(isGameFinished == false){
//    		mapTile.render(g);
    		mapTile.render(g, figureToPlaceColor);
            
            if (currentFigure != null && iscurrentFigureSwordOrDragon == false && isRightPlayerPlaying) {
                image = new Image(currentFigure.getPath());
                g.drawImage(image, mouseX, mouseY, mouseX + Consts.TILE_WIDTH, mouseY + Consts.TILE_HEIGHT, 0, 0,
                        image.getWidth(), image.getHeight());
            }
    	} else {
    		String winner = isRedTurn() == true ? "red" : "blue";
        	g.drawString("Winner is " + winner + " player", Consts.TILE_WIDTH * 6, Consts.TILE_HEIGHT * 4 );
    	}
    }

    private void showRemaining() {
    	this.showColor();    	
    	
        for (int i = 0; i < remaining[figureToPlaceColor].length; i++) {
            System.out.print(remaining[figureToPlaceColor][i] + " " + FigureType.values()[i].getName() + ", ");
        }
        System.out.println();
    }
    
    private void showColor() {
    	if(figureToPlaceColor == 0){
    		System.out.println("RED FIGURES");
    	} else {
    		System.out.println("BLUE FIGURES");
    	}
    }
    
    private boolean isScoutMovementValid(int tileIndexX, int tileIndexY, int oldTileIndexX, int oldTileIndexY) {
    	
    	return (tileIndexX == oldTileIndexX || tileIndexY == oldTileIndexY) ?
    			true : false;

    }
    
    private boolean isRedTurn(){
    	return figureToPlaceColor == 0;
    }
    
    private boolean isOtherFiguresMovementValid(int tileIndexX, int tileIndexY, int oldTileIndexX, int oldTileIndexY) {
    	
    	return ((tileIndexX == oldTileIndexX + 1 || tileIndexX == oldTileIndexX - 1) && tileIndexY == oldTileIndexY) 
 			|| ((tileIndexY == oldTileIndexY + 1 || tileIndexY == oldTileIndexY - 1) && tileIndexX == oldTileIndexX) ?
 					true : false;
    }
    
    private boolean doesScoutCrossOtherFigureOrWater(int tileIndexX, int tileIndexY, int oldTileIndexX, int oldTileIndexY) { //radi udesno - ulivo moze preskakivat
    	if(tileIndexX == oldTileIndexX){
    		for(int i = oldTileIndexY + 1; i < tileIndexY; i++) {
    			if(!mapTile.canPlaceAtWithTileIndex(tileIndexX, i)){
    				return true;
    			}
    		}
    		for(int i = oldTileIndexY - 1; i > tileIndexY; i--){
    			if(!mapTile.canPlaceAtWithTileIndex(tileIndexX, i)){
    				return true;
    			}
    		}
    		
    	} else {
    		for(int i = oldTileIndexX + 1; i < tileIndexX; i++) {
    			if(!mapTile.canPlaceAtWithTileIndex(i, tileIndexY)){
    				return true;
    			}
    		}
    		for(int i = oldTileIndexX - 1; i > tileIndexX; i--){
    			if(!mapTile.canPlaceAtWithTileIndex(i, tileIndexY)){
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }

    private void updateAmIAllowedToPlaceThere(){
    	if(currentFigure != null && iscurrentFigureSwordOrDragon == false) {
        	int tileIndexX = mapTile.getTileX(mouseX);
        	int tileIndexY =mapTile.getTileY(mouseY);
        	int oldTileIndexX = mapTile.getTileX(oldTileX);
        	int oldTileIndexY = mapTile.getTileY(oldTileY);
        	boolean amIScout = currentFigure == FigureType.Scout;
        	
        	if(amIScout == true){
        		if(isScoutMovementValid(tileIndexX, tileIndexY, oldTileIndexX, oldTileIndexY) && !doesScoutCrossOtherFigureOrWater(tileIndexX, tileIndexY, oldTileIndexX, oldTileIndexY)) {
        			amIAllowedToPlaceAtThatTile = true;
        		} else {
        			amIAllowedToPlaceAtThatTile = false;
        		}
        	} else {
        		if(isOtherFiguresMovementValid(tileIndexX, tileIndexY, oldTileIndexX, oldTileIndexY)){
        			amIAllowedToPlaceAtThatTile = true;
        		} else {
        			amIAllowedToPlaceAtThatTile = false;
        		}
        	}
        	
        }
    }
}
