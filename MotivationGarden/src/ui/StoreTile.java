package ui;

import ecs100.UI;
import model.GardenItem;
import model.Tree;

/* ====================================================================================================================	*/
/**
 * 	StoreTile: 			Tiles at the top of the screen that can be clicked on to buy items. Used by GUI.
 *
 * 	@version  			1.0
 * 	@since    			1.0
 */
/* ====================================================================================================================	*/

public class StoreTile {
	
//	Fields:
	private double x;
	private double y;
	
	private double xMin; // <- These minimum and maximum values are used by the mouse listener to find out if the user is hovering over this tile.
	private double xMax;
	private double yMin;
	private double yMax;
	
	private int itemID;
	private GardenItem item; // <- The StoreTile has an instance of the item it sells so it can get its properties
	
	private String sprite = "../MotivationGarden/resources/images/ui/storetile.png"; // <- For drawing the StoreTile UI
	private String hoverSprite = "../MotivationGarden/resources/images/ui/storetile_highlight.png";

//	Constructor:
	public StoreTile(double x, double y, double xMin, double xMax, double yMin, double yMax, int id, GardenItem item) {
		
		this.x = x;
		this.y = y;
		
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		
		this.itemID = id;
		this.item = item;
		
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
		UI.drawImage(sprite, x, y);
		if (item instanceof Tree) { /* Alternative tree sprite here */ }
		else UI.drawImage(item.getImagePath(), x+16, y+16);
	}
	
	/// drawHighlight:
	/** Similar to drawTile, but draws the image shown when the mouse is over the tile instead.
	*  
	*	@return ->			N/A.	
	*																														*/
	public void drawHighlight() {
		UI.drawImage(hoverSprite, x, y);
		if (item instanceof Tree) { /* Alternative tree sprite here */ }
		else UI.drawImage(item.getImagePath(), x+16, y+16);
	}
	
	// GETTERS & SETTERS:
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getID() {
		return this.itemID;
	}
	
	public GardenItem getItem() {
		return this.item;
	}
	
}
