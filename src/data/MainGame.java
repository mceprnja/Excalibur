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
import utilities.MapTile;
import utilities.TileType;

public class MainGame extends BasicGame {

    private MapTile mapTile = new MapTile();

    private Input input;
    private int[] capacities = { 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 };
    private int[] remaining = { 6, 1, 1, 1, 8, 2, 4, 4, 1, 3, 4, 5 };
    private int index = 0;
    private boolean amIAllowedToPlaceThere = false;
    private FigureType current;
    private boolean isCurrentSwordOrDragon;
    private Image image;
    private int oldTileX, oldTileY;
    int mouseX, mouseY;
    boolean wasDown = false;

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
        if (input.isKeyPressed(Input.KEY_1)) {
            if (remaining[index] > 0) {
                int mouseX = input.getMouseX();
                int mouseY = input.getMouseY();
                if (mapTile.canPlaceAt(mouseX, mouseY)) {
                    mapTile.setFigureAt(mouseX, mouseY, index);
                    remaining[index]--;
                    showRemaining();
                }
            }
        } else if (input.isKeyPressed(Input.KEY_2)) {
        	System.out.println("Trebao bih brisat");
        	mouseX = input.getMouseX();
            mouseY = input.getMouseY();
        	FigureType current = mapTile.getFigureAt(mouseX, mouseY);
        	int index = current.getIndex();
//        	System.out.println(index);
        	
        	if(current != FigureType.Null)
        	{
        		if(remaining[index] < capacities[index])
        		{
        			mapTile.clearTile(mouseX, mouseY);
                    remaining[current.ordinal()]++;
                    showRemaining();
        		}
        	}
        }

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
//        	System.out.println("TEST1");
            mouseX = input.getMouseX();
            mouseY = input.getMouseY();            
        }
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
//        	System.out.println("TEST2");
            current = mapTile.getFigureAt(mouseX, mouseY);
            isCurrentSwordOrDragon = current == FigureType.Dragon || current == FigureType.Sword;
            oldTileX = mouseX;
            oldTileY = mouseY;
            
            if(isCurrentSwordOrDragon == false)
            {
                mapTile.clearTile(oldTileX, oldTileY);
            }
            wasDown = true;
            

        }
        if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && wasDown && isCurrentSwordOrDragon == false) {
        	System.out.println("Jesam li na istom mistu?");
        	boolean amIOnSamePlace = (mapTile.getTileX(mouseX) == mapTile.getTileX(oldTileX) && mapTile.getTileY(mouseY) == mapTile.getTileY(oldTileY)) ? true : false;
        	System.out.println(amIOnSamePlace);
            if (current == FigureType.Null) {
                System.out.println("it was null");
            } else {
                if (mapTile.canPlaceAt(mouseX, mouseY) && amIAllowedToPlaceThere) {
                    mapTile.setFigureAt(mouseX, mouseY, current.ordinal());
                } else {
                    mapTile.setFigureAt(oldTileX, oldTileY, current.ordinal());
                }
            }
            wasDown = false;
            current = null;
        }

        if (input.isKeyPressed(Keyboard.KEY_TAB)) {
            index++;
            index %= remaining.length;

            System.out.println("Now placing " + FigureType.values()[index].getName());
        }

        if (input.isKeyPressed(Keyboard.KEY_SPACE)) {
            showRemaining();
        }
        if (input.isKeyPressed(Keyboard.KEY_R)) {
            mapTile.clearAll();
            for (int i = 0; i < remaining.length; i++) {
                remaining[i] = capacities[i];
            }
            showRemaining();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        mapTile.render(g);

        if (current != null && isCurrentSwordOrDragon == false) {
            image = new Image(current.getPath());
            g.drawImage(image, mouseX, mouseY, mouseX + Consts.TILE_WIDTH, mouseY + Consts.TILE_HEIGHT, 0, 0,
                    image.getWidth(), image.getHeight());
        }
//       NE IDE OVO OVDI
        if(current != null && isCurrentSwordOrDragon == false) {//red - if he cant place figure there, in this part i want to solve amIAllowedToPlaceThere
//        	System.out.println("Show where i can go");
        	int tileIndexX = mapTile.getTileX(mouseX);
        	int tileIndexY =mapTile.getTileY(mouseY);
        	int oldTileIndexX = mapTile.getTileX(oldTileX);
        	int oldTileIndexY = mapTile.getTileY(oldTileY);
        	boolean amIScout = current == FigureType.Scout;
        	
        	if(amIScout == true){
        		if(isScoutMovementValid(tileIndexX, tileIndexY, oldTileIndexX, oldTileIndexY) && !doesScoutCrossOtherFigureOrWater(tileIndexX, tileIndexY, oldTileIndexX, oldTileIndexY)) {
        			amIAllowedToPlaceThere = true;
        		} else {
        			amIAllowedToPlaceThere = false;
        		}
        	} else {
        		if(isOtherFiguresMovementValid(tileIndexX, tileIndexY, oldTileIndexX, oldTileIndexY)){
        			amIAllowedToPlaceThere = true;
        		} else {
        			amIAllowedToPlaceThere = false;
        		}
        	}
        	
        }
    }

    public void showRemaining() {
        for (int i = 0; i < remaining.length; i++) {
            System.out.print(remaining[i] + " " + FigureType.values()[i].getName() + ", ");
        }
        System.out.println();
    }
    
    public boolean isScoutMovementValid(int tileIndexX, int tileIndexY, int oldTileIndexX, int oldTileIndexY) { //this part fornow dont solve is taken place or is water
    	
    	return (tileIndexX == oldTileIndexX || tileIndexY == oldTileIndexY) ?
    			true : false;

    }
    
    public boolean isOtherFiguresMovementValid(int tileIndexX, int tileIndexY, int oldTileIndexX, int oldTileIndexY) {
    	
    	return ((tileIndexX == oldTileIndexX + 1 || tileIndexX == oldTileIndexX - 1) && tileIndexY == oldTileIndexY) 
 			|| ((tileIndexY == oldTileIndexY + 1 || tileIndexY == oldTileIndexY - 1) && tileIndexX == oldTileIndexX) ?
 					true : false;
    }
    
    public boolean doesScoutCrossOtherFigureOrWater(int tileIndexX, int tileIndexY, int oldTileIndexX, int oldTileIndexY) { //radi udesno - ulivo moze preskakivat
    	if(tileIndexX == oldTileIndexX){
    		for(int i = oldTileIndexY + 1; i < tileIndexY; i++) {
    			if(!mapTile.canPlaceAtWithTileIndex(tileIndexX, i)){
//    				System.out.println("Preklapa se s necim");
    				return true;
    			}
    		}
    		
    		for(int i = oldTileIndexY - 1; i > tileIndexY; i--){
    			if(!mapTile.canPlaceAtWithTileIndex(tileIndexX, i)){
//    				System.out.println("PREKLAPA SE S NECIM");
    				return true;
    			}
    		}
    		
    	} else {
    		for(int i = oldTileIndexX + 1; i < tileIndexX; i++) {
    			if(!mapTile.canPlaceAtWithTileIndex(i, tileIndexY)){
//    				System.out.println("Preklapa se s necim");
    				return true;
    			}
    		}
    		
    		for(int i = oldTileIndexX - 1; i > tileIndexX; i--){
    			if(!mapTile.canPlaceAtWithTileIndex(i, tileIndexY)){
//    				System.out.println("PREKLAPA SE S NECIM");
    				return true;
    			}
    		}
    	}
    	
    	
    	return false;
    }
}
