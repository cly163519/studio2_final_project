package ui;
import ecs100.UI;

/* ====================================================================================================================	*/
/**
 * 	Tile: 				Valid places for items to be drawn. Used by GUI.
 *
 * 	@version  			1.0
 * 	@since    			1.0
 */
/* ====================================================================================================================	*/


public class Tile {
	
//	Fields:
	private double xMin; // <- These minimum and maximum values are used by the mouse listener to find out if the user is hovering over this tile.
	private double xMax;
	private double yMin;
	private double yMax;
	
	private int gridX; // <- Where this tile is on the grid of tiles.
	private int gridY;
	
	private int x; // <- Where this tile is in the GUI.
	private int y;
	
	private String highlightImagePath = "../MotivationGarden/resources/images/ui/tile_highlight.png";

//	Constructor:
	public Tile(int x, int y, double xMin, double xMax, double yMin, double yMax, int gridX, int gridY) {
		
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		
		this.gridX = gridX;
		this.gridY = gridY;
		
		this.x = x;
		this.y = y;
		
	}

/* ====================================================================================================================	*/
	
	/// checkForHovered:
	/** Checks if this tile is currently being hovered over by the mouse.
	*  
	*	@param x and y		These are the x and y co-ords of the mouse.
	*	@return ->			boolean.	
	*																														*/
	public boolean checkForHovered(double x, double y) {
		if (x > xMin && x < xMax && y > yMin && y < yMax) { // <- Checks if the mouse is within the bounds of this tile.
			return true;
		}
		else return false;
		
	}
	
	/// drawTile:
	/** Draws this tile on the GUI.
	*  
	*	@return ->			N/A.	
	*																														*/
	public void drawTile() {
		UI.drawRect(x, y, 32, 32);
	}
	
	public void drawHighlight() {
		UI.drawImage(highlightImagePath, x, y);
	}
	
	/// Getters & Setters:
	
	public double getxMin() {
		return xMin;
	}
	
	public double getyMin() {
		return yMin;
	}
	
	public double getxMax() {
		return xMax;
	}
	
	public double getyMax() {
		return yMax;
	}
	
	public int getGridX() {
		return gridX;
	}
	
	public int getGridY() {
		return gridY;
	}
	
			
}
