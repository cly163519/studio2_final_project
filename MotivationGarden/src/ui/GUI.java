package ui;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import ecs100.*;
import main.Main;
import manager.StoreManager;
import model.Animal;
import model.Chicken;
import model.Cow;
import model.Flower;
import model.GardenItem;
import model.Pig;
import model.Tree;

/* ====================================================================================================================	*/
/**
 * 	GUI: 				Interprets data from Main and other classes and displays that data to the user.
 *
 * 	@version  			1.0
 * 	@since    			1.0
 * 
 */
/* ====================================================================================================================	*/

public class GUI {

//	Fields:
	// For methods that needs to know how big the sprites are (32x32px):
	private static int SPRITE_WIDTH = 32; // <- Sprites will all be standardized to 32x32px in Photoshop
	private static int SPRITE_HEIGHT = 32;
	
	// Top and left of the grid:
	private static int GARDEN_GRID_TOP = SPRITE_HEIGHT*7; // <- Top and left where the garden grid should be drawn
	private static int GARDEN_GRID_LEFT = SPRITE_WIDTH*3;
	
	// For methods that need to know the total height and width of the garden:
	private static int GARDEN_GRID_WIDTH = SPRITE_WIDTH*Main.getGardenWidth(); // <- Total width and height of the whole grid. This is for checking if the mouse is hovering over the grid.
	private static int GARDEN_GRID_HEIGHT = SPRITE_HEIGHT*Main.getGardenHeight();
	
	// Store tiles configuration:
	private static int STORE_TILE_COUNT = 6;
	private static int STORE_TILE_SIZE = 64;
	private static int STORE_GRID_TOP = GARDEN_GRID_TOP-((SPRITE_HEIGHT*6)-8);
	private static int STORE_GRID_LEFT = GARDEN_GRID_LEFT;
	private static int STORE_GRID_WIDTH = STORE_TILE_SIZE*STORE_TILE_COUNT;
	
//	Collections:
	private static HashMap<Integer, Integer> GUIxValues = new HashMap<Integer, Integer>(); // <- Converts X and Y values to scaled up co-ords of where those images should be drawn the on-screen grid.
	private static HashMap<Integer, Integer> GUIyValues = new HashMap<Integer, Integer>();

	private static ArrayList<Tile> tiles = new ArrayList<Tile>(); // <- Each square belongs to a 'tile' class. This is so the x and y position of tile the mouse is over can be stored and accessed.
	private static ArrayList<StoreTile> storeTiles = new ArrayList<StoreTile>();
	
//	Class-Wide Variables:
	private static Boolean showHighlight = false;
	private static Boolean inStore = false;
	private static Tile hoveredTile; // <- The tile currently being hovered over.
	private static StoreTile hoveredStoreTile;
	private static GardenItem itemBeingPlaced = null;

//	Images:
	private static String gardenImg = "../MotivationGarden/resources/images/ui/garden.png";
	private static String barnImg = "../MotivationGarden/resources/images/ui/barn.png";
	private static String backgroundImg = "../MotivationGarden/resources/images/ui/background.png";
	
//	Constructor
	public GUI() {
		
		createGrid(Main.getGardenWidth(), Main.getGardenHeight()); // <- Draws the grid of tiles
		createStore();
		convertXAndYValues(); // <- Fills the HashMaps with all x and y positions of the garden and their equivalent on the GUI. This is so the GUI knows where to draw an item from its x and y fields.
		
		UI.setMouseMotionListener(this::doMouse); // <- Create mouse listener
		
		// UI Buttons:
		UI.addButton("SAVE", UI::quit );
		UI.addButton("LOAD", UI::quit );
		UI.addButton("QUIT", UI::quit );
		
		
	}
	
/* ====================================================================================================================	*/
	
	
	/// drawWorld:
	/** Runs the GUI's draw methods on all gardenItems + static images that need to be on screen..
	*  
	*	@return ->			N/A.	
	*																														*/
    public static void drawWorld() {
    	UI.clearGraphics();
    	drawStaticImages();
        for (GardenItem animal : Main.getAnimals() ) {
            drawItem(animal);
        }
        for (GardenItem plant : Main.getPlants() ) {
            drawItem(plant);
        }
        if (showHighlight && hoveredTile != null) {
        	hoveredTile.drawHighlight();
        }
        drawStore();
    }
	
	/// createGrid:
	/** Draws the tiles on screen and create a class containing that tile's properties.
	*  
	*	@param int			gardenWidth: How many tiles will be drawn horizontally.
	*	@param int			gardenHeight: How many vertical rows of tiles will be drawn.
	*	@return ->			N/A.	
	*																														*/
	public static void createGrid(int gardenWidth, int gardenHeight) {
		
		int x = GARDEN_GRID_LEFT; // <- Initial X and Y positions for the grid
		int y = GARDEN_GRID_TOP;
		
		drawStaticImages();
		
		for (int i = 0 ; i < gardenHeight ; i++) { // <- Draws a row, then move down by the height of the sprite [gardenWidth] times
			for (int j = 0 ; j < gardenWidth ; j ++) { // <- Draws a square, then moves to the right [gardenWidth] times
				
				// FIELDS FOR CONSTRUCTING THE TILE:
				double xMin = x; // <- The minimum x and y values that cover this tile. This is so the mouse listener knows
				double xMax = x+SPRITE_WIDTH; // when the mouse is hovering over a tile.
				double yMin = y;
				double yMax = y+SPRITE_HEIGHT; 
				int gridX = j+1; // <- Storing the x and y values that the gardenItem objects will use. This is so
				int gridY = i+1; // we know which tile is at which place in the grid.
				
				Tile newTile = new Tile(x, y, xMin, xMax, yMin, yMax, gridX, gridY);
				tiles.add(newTile); // <- Add the tile to an ArrayList so we can access it later.
				
				newTile.drawTile();
				
				x += SPRITE_WIDTH; // <- Move right before next loop
				
			}
			x = GARDEN_GRID_LEFT; // <- Move back to far left
			y += SPRITE_HEIGHT; // <- Move down before next loop
		}	
	}
	
	public static void createStore() {
		
		int x = STORE_GRID_LEFT;
		int y = STORE_GRID_TOP;
		
		for (int i = 0 ; i < 5 ; i++) {
			
			double xMin = x;
			double xMax = x+STORE_TILE_SIZE;
			double yMin = y;
			double yMax = y+STORE_TILE_SIZE;
			int id = i;
			
			GardenItem item = null;
			switch (id) {
			
			case 0:  	item = 	new Flower(-1, -1); 	break;
			case 1: 	item = 	new Tree(-1, -1);		break;
			case 2:		item = 	new Chicken(-1, -1);	break;
			case 3:		item = 	new Pig(-1, -1);		break;
			case 4:		item = 	new Cow(-1, -1);		break;
			default: break;
			
			}
			
			storeTiles.add(new StoreTile(x, y, xMin, xMax, yMin, yMax, id, item));
			
			x+= STORE_TILE_SIZE;
			
		}
		
	}
	
	/// drawStaticImages: 
	/** Draws static images on screen that will not move (background, barn, etc).
	*  
	*	@return ->			N/A.	
	*																														*/
	public static void drawStaticImages() {
		
		UI.drawImage(backgroundImg, 0, 0); // <- Background (water)
		UI.drawImage(gardenImg, GARDEN_GRID_LEFT, GARDEN_GRID_TOP-(SPRITE_HEIGHT*3)); // <- Garden (grass)
		UI.drawImage(barnImg, GARDEN_GRID_LEFT+GARDEN_GRID_WIDTH/2-64, GARDEN_GRID_TOP-(SPRITE_HEIGHT*3)); // <- Barn
		
	}
	
	/// convertXAndYValues:
	/** Takes all the valid x and y values of the grid and converts them into where we want them to be drawn on the screen. These are stored in HashMaps that we can use to do the conversion whenever we need it.
	*  
	*	@param N/A			
	*	@return ->			N/A.	
	*																														*/
	private static void convertXAndYValues() {
		
		int GUIX = GARDEN_GRID_LEFT; // <- x1 and y1 will be assigned to the wherever the top and left of the grid is.
		int GUIY = GARDEN_GRID_TOP;
		
		// Convert X:
		for (int i = 1 ; i <= Main.getGardenWidth(); i++) {
			GUIxValues.put(i, GUIX); // <- Put i and the place we want to draw items in the HashMap.
			GUIX += SPRITE_WIDTH; // <- Move left by the width of the image before looping again.
		}
		
		//Convert Y:
		for (int i = 1 ; i <= Main.getGardenHeight(); i++) {
			GUIyValues.put(i, GUIY);
			GUIY += SPRITE_HEIGHT;
		}
		
	}
	
	/// doMouse:
	/** The mouse listener for doing all things mouse-related.
	*  
	*	@param String		Action: tracks mouse clicks, etc.			
	*	@param double		x: Mouse position x
	*	@param double		y: Mouse position y
	*	@return ->			N/A.	
	*																														*/
	private void doMouse(String action, double x, double y) {
		
		// If the mouse is currently within the garden grid, do these things:
		if ( x > GARDEN_GRID_LEFT && x < GARDEN_GRID_LEFT+GARDEN_GRID_WIDTH && y > GARDEN_GRID_TOP && y < GARDEN_GRID_TOP+GARDEN_GRID_HEIGHT ) {
			
			// Check which tile the mouse is over, and set hoveredTile to that tile.
			for (Tile tile : tiles) {
				if ( tile.checkForHovered(x, y) ) {
					hoveredTile = tile;
				}
			}
			
			// When the user clicks on a tile:
			if ( action.equals("pressed") ) {
				if ( itemBeingPlaced != null ) {
					
					int placementX = hoveredTile.getGridX();
					int placementY = hoveredTile.getGridY();
					
					if ( itemBeingPlaced instanceof Flower ) { 
						StoreManager.buyItem(new Flower(placementX, placementY)); 
					}
					else if ( itemBeingPlaced instanceof Tree ) { 
						StoreManager.buyItem(new Tree(placementX, placementY)); 
					}
					else if ( itemBeingPlaced instanceof Chicken ) { 
						StoreManager.buyItem(new Chicken(placementX, placementY)); 
					}
					else if ( itemBeingPlaced instanceof Pig ) { 
						StoreManager.buyItem(new Pig(placementX, placementY)); 
					}
					else if ( itemBeingPlaced instanceof Cow ) { 
						StoreManager.buyItem(new Cow(placementX, placementY)); 
					}
					
					showHighlight = false;
					itemBeingPlaced = null;
					
				}
			}
			
		}
		
		// If the mouse is currently within the store grid, do these things:
		if ( x > STORE_GRID_LEFT && x < STORE_GRID_WIDTH && y > STORE_GRID_TOP && y < STORE_GRID_TOP+STORE_TILE_SIZE) {
			
			inStore = true;
			
			// Check which store tile the mouse is over, and set hoveredStoreTile to that store tile.
			for (StoreTile storeTile : storeTiles) {
				if ( storeTile.checkForHovered(x, y) ) {
					hoveredStoreTile = storeTile;
				}
			}
			
			// When the user clicks on a store tile:
			if ( action.equals("pressed") ) {
				GardenItem itemBeingBought = hoveredStoreTile.getItem();
				int price = itemBeingBought.getPrice();
				if ( price > StoreManager.getMoney() ) { UI.println("Can't afford"); }
				else {
					itemBeingPlaced = itemBeingBought;
					showHighlight = true;
				}
			}
		}
		else inStore = false;
	}
	
	/// drawItem:
	/** Any GardenItem can be drawn on the screen by passing it through this method. It's based on the GardenItem's x and y fields.
	*  
	*	@param GardenItem	GardenItem: This GardenItem's sprite will be drawn where it belongs on the grid (decided by x and y)
	*	@return ->			N/A.	
	*																														*/
	public static void drawItem(GardenItem item) {
		String sprite = item.getImagePath();
		int x = item.getPositionX();
		int y = item.getPositionY();
		if (x < 1 || x > Main.getGardenWidth() || y < 1 || y > Main.getGardenHeight()) {
			UI.println("Item out of bounds!"); // <- If an item is outside of the world's bounds, don't draw it.
		}
		else {
			if (sprite != null) { // <- If everything looks good, draw sprite on grid.
				UI.drawImage(sprite, getGUIX(x), getGUIY(y));
			}
			else { // <- If the item doesn't have a sprite, draw a red circle instead.
				UI.setColor(Color.RED);
				UI.drawOval(getGUIX(x), getGUIY(y), SPRITE_WIDTH, SPRITE_HEIGHT);
			}
		}
	}
	
	public static void drawStore() {
		
		for (StoreTile storeTile : storeTiles) {
			UI.drawString("$ "+storeTile.getItem().getPrice(), storeTile.getX()+16, storeTile.getY()-10);
			if (storeTile == hoveredStoreTile && inStore) {
				storeTile.drawHighlight();
			}
			else storeTile.drawTile();
		}
		
	}
	
	/// getGUIX:
	/** Takes an x value and returns where it should be drawn on screen.
	*  
	*	@param int			x: The x value on the grid where the item should be drawn.
	*	@return ->			N/A.	
	*																														*/
	public static int getGUIX(int x) {
		return GUIxValues.get(x);
	}
	
	/// getGUIY:
	/** Takes an y value and returns where it should be drawn on screen.
	*  
	*	@param int			y: The y value on the grid where the item should be drawn.
	*	@return ->			N/A.	
	*																														*/
	public static int getGUIY(int y) {
		return GUIyValues.get(y);
	}
	
	// TEMPORARY METHODS (These are just here for testing):
	public void clearAll() {
		UI.clearGraphics();
	}
	public void drawCow() {
		int x = UI.askInt("X position: ");
		int y = UI.askInt("Y position: ");
		Cow cow = new Cow(x, y);
		drawItem(cow);
	}	
}
