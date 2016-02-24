package figures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import data.Figure;
import data.Mover;

public class Dragon extends Figure implements Mover {


    @Override
    public void move(int x, int y) {

    }
    
    public void render(Graphics g) throws SlickException {
        g.drawImage(new Image("images/dragon.png"), 00, 0, 55, 55, 0, 0, 128, 128);
    }

}
