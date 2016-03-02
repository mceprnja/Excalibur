package utilities;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface IMapTile {
	public void render(Graphics g) throws SlickException;
	public int getTileColor(int mouseX, int mouseY);
	public int getTileX(int mouseX);
	public int getTileY(int mouseY);
	public void setFigureAt(int mouseX, int mouseY, int figure, int figureColor);
	public int setFigureAt(int mouseX, int mouseY, int oldX, int oldY, int oldTileColor, FigureType currentFigure);
	public void clearTile(int mouseX, int mouseY);
	public boolean canPlaceAt(int mouseX, int mouseY, int currentTileColor, int futureTileColor);
	public boolean canPlaceAt(int mouseX, int mouseY, boolean isSetting);
	public boolean canPlaceAtWithTileIndex(int tileX, int tileY);
	public FigureType getFigureAt(int mouseX, int mouseY);
	public void clearAll();
}
