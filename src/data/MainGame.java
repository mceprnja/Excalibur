package data;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class MainGame extends BasicGame {

    public static int TILE_HEIGHT = 64;
    public static int TILE_WIDTH = 64;
    public static int SCREEN_WIDTH = TILE_HEIGHT * 10;
    public static int SCREEN_HEIGHT = TILE_WIDTH * 10;
    private MapTile mapTile = new MapTile(SCREEN_WIDTH, SCREEN_HEIGHT);
   

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Excalibur"));
            appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
            appgc.setTargetFrameRate(100);
            appgc.setIcon("images/icon.png");
//            appgc.setAlwaysRender(true);
//            appgc.setClearEachFrame(false);
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        // TODO Auto-generated method stub
        mapTile.render(g);
    }
}