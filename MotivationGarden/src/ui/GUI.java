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
	private static int STORE_TILE_COUNT = 6; // <- This needs to be one higher than it actually is for some reason for the mouse listener to work. Not sure what's going on there but hey if it works it works.
	private static int STORE_TILE_SIZE = 64; // <- Store tiles are 64x64p
	private static int STORE_GRID_TOP = GARDEN_GRID_TOP-((SPRITE_HEIGHT*6)-8); // <- Draw store between garden and top of screen.
	private static int STORE_GRID_LEFT = GARDEN_GRID_LEFT; // <- Use the same y-position as the garden.
	private static int STORE_GRID_WIDTH = STORE_TILE_SIZE*STORE_TILE_COUNT;
	
//	Collections:
	private static HashMap<Integer, Integer> GUIxValues = new HashMap<Integer, Integer>(); // <- Converts X and Y values to scaled up co-ords of where those images should be drawn the on-screen grid.
	private static HashMap<Integer, Integer> GUIyValues = new HashMap<Integer, Integer>();

	private static ArrayList<Tile> tiles = new ArrayList<Tile>(); // <- Each square belongs to a 'tile' class. This is so the x and y position of tile the mouse is over can be stored and accessed.
	private static ArrayList<StoreTile> storeTiles = new ArrayList<StoreTile>();
	
//	Class-Wide Variables:
	private static Boolean SHOW_HIGHLIGHT = false; // <- If this is true, the grid tile the mouse is over will be highlighted.
	private static Boolean IN_STORE = false; // <- This gets set to true if the mouse is within the store's area. Used to draw either the normal image or the 'mouse over' image.
	private static Tile HOVERED_TILE; // <- Will be set to tile currently being hovered over.
	private static StoreTile HOVERED_STORE_TILE; // <- Will be set to the StoreTile currently being hovered over.
	private static GardenItem ITEM_BEING_PLACED = null; // <- When the user is placing an item, stores what that item is until they click on the grid to place it.

//	Images:
	private static String GARDEN_IMG = "../MotivationGarden/resources/images/ui/garden.png"; // <- Static images that are drawn every frame.
	private static String BARN_IMG = "../MotivationGarden/resources/images/ui/barn.png";
	private static String BACKGROUND_IMG = "../MotivationGarden/resources/images/ui/background.png";
	
//	Constructor
	public GUI() {
		
		createGrid(Main.getGardenWidth(), Main.getGardenHeight()); // <- Creates the tiles arraylist
		createStore(); // <- Creates the StoreTiles arraylist
		convertXAndYValues(); // <- Fills the HashMaps with all x and y positions of the garden and their equivalent on the GUI. This is so the GUI knows where to draw an item from its x and y fields.
		
		UI.setMouseMotionListener(this::doMouse); // <- Create mouse listener
		
		// UI Buttons:
		UI.addButton("SAVE", this::save);
		UI.addButton("LOAD", this::load);
		UI.addButton("QUIT", UI::quit );
		
	}
	
	// DataController methods to save/load JSON files could run from here?
	private void save() { /* save method here */ }
	private void load() { /* load method here */ }
	
/* ====================================================================================================================	*/
	
	/// drawWorld:
	/** Runs the GUI's draw methods on all gardenItems + static images that need to be on screen. Called by main when the world updates.
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
        if (SHOW_HIGHLIGHT && HOVERED_TILE != null) {
        	HOVERED_TILE.drawHighlight();
        }
        drawStore();
    }
	
	/// createGrid:
	/** Draws the tiles on screen and creates an instance of a class containing that tile's properties.
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
	
	/// createStore:
	/** Creates an arraylist of on-screen store tiles with their respective properties.
	*  
	*	@return ->			N/A.	
	*																														*/
	public static void createStore() {
		
		int x = STORE_GRID_LEFT; // <- Start drawing at the point set in fields.
		int y = STORE_GRID_TOP;
		
		for (int i = 0 ; i < 5 ; i++) {
			
			double xMin = x;
			double xMax = x+STORE_TILE_SIZE;
			double yMin = y;
			double yMax = y+STORE_TILE_SIZE; // <- Minimum and maximum x/y values that cover this tile. Used by mouse listener
			int id = i; // <- StoreTiles have an id based on the order they were made.
			
			GardenItem item = null; // <- item is assigned to the GardenObject it should sell based on what loop we're running
			switch (id) {
			
			// StoreTiles get the item they're selling as an argument, so they can get its sprite, price, etc.
			case 0:  	item = 	new Flower(-1, -1); 	break; // <- Tile 1 gets flower , tile 2 gets Tree, etc
			case 1: 	item = 	new Tree(-1, -1);		break;
			case 2:		item = 	new Chicken(-1, -1);	break;
			case 3:		item = 	new Pig(-1, -1);		break;
			case 4:		item = 	new Cow(-1, -1);		break;
			default: break;
			}
			
			storeTiles.add(new StoreTile(x, y, xMin, xMax, yMin, yMax, id, item)); // <- Add the new StoreTile to the list
			
			x+= STORE_TILE_SIZE; // <- Move to the right and repeat.
			
		}
		
	}
	
	/// drawStaticImages: 
	/** Draws static images on screen that will not move (background, barn, etc).
	*  
	*	@return ->			N/A.	
	*																														*/
	public static void drawStaticImages() {
		
		UI.drawImage(BACKGROUND_IMG, 0, 0); // <- Background (water)
		UI.drawImage(GARDEN_IMG, GARDEN_GRID_LEFT, GARDEN_GRID_TOP-(SPRITE_HEIGHT*3)); // <- Garden (grass)
		UI.drawImage(BARN_IMG, GARDEN_GRID_LEFT+GARDEN_GRID_WIDTH/2-64, GARDEN_GRID_TOP-(SPRITE_HEIGHT*3)); // <- Barn
		
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
		
		// GARDEN MOUSE ACTIONS:
		
		// If the mouse is currently within the garden grid, do these things:
		if ( x > GARDEN_GRID_LEFT && x < GARDEN_GRID_LEFT+GARDEN_GRID_WIDTH && y > GARDEN_GRID_TOP && y < GARDEN_GRID_TOP+GARDEN_GRID_HEIGHT ) {
			
			// Check which tile the mouse is over, and set hoveredTile to that tile.
			for (Tile tile : tiles) {
				if ( tile.checkForHovered(x, y) ) {
					HOVERED_TILE = tile;
				}
			}
			
			// When the user clicks on a tile:
			if ( action.equals("pressed") ) {
				if ( ITEM_BEING_PLACED != null ) { // <- Run this code if there's an item from the store ready to be placed and the user clicks
					
					int placementX = HOVERED_TILE.getGridX(); // <- Ask the hovered tile where this item should go
					int placementY = HOVERED_TILE.getGridY(); //		on the grid.
					
					// Check what kind of item it is and buy it at the appropriate place:
					if ( ITEM_BEING_PLACED instanceof Flower ) { 
						StoreManager.buyItem(new Flower(placementX, placementY)); 
					}
					else if ( ITEM_BEING_PLACED instanceof Tree ) { 
						StoreManager.buyItem(new Tree(placementX, placementY)); 
					}
					else if ( ITEM_BEING_PLACED instanceof Chicken ) { 
						StoreManager.buyItem(new Chicken(placementX, placementY)); 
					}
					else if ( ITEM_BEING_PLACED instanceof Pig ) { 
						StoreManager.buyItem(new Pig(placementX, placementY)); 
					}
					else if ( ITEM_BEING_PLACED instanceof Cow ) { 
						StoreManager.buyItem(new Cow(placementX, placementY)); 
					}
					
					SHOW_HIGHLIGHT = false; // <- Stop highlighting tiles and set itemBeingPlaced back to null
					ITEM_BEING_PLACED = null;
					
				}
			}
		}
		
		// STORE MOUSE ACTIONS:
		
		// If the mouse is currently within the store grid, do these things:
		if ( x > STORE_GRID_LEFT && x < STORE_GRID_WIDTH && y > STORE_GRID_TOP && y < STORE_GRID_TOP+STORE_TILE_SIZE) {
			
			IN_STORE = true; // <- Remember that the mouse is in the store. This is used by drawWorld() to draw the selected tile image.
			
			// Check which store tile the mouse is over, and set hoveredStoreTile to that store tile.
			for (StoreTile storeTile : storeTiles) {
				if ( storeTile.checkForHovered(x, y) ) {
					HOVERED_STORE_TILE = storeTile;
				}
			}
			
			// When the user clicks on a store tile:
			if ( action.equals("pressed") ) {
				GardenItem itemBeingBought = HOVERED_STORE_TILE.getItem(); // <- Ask the hovered store tile what item it sells
				int price = itemBeingBought.getPrice(); // <- Check the price and see if it can be bought
				
				// If the user doesn't have enough money:
				if ( price > StoreManager.getMoney() ) {  // <- Do this if the item is too expensive
					try {
						StoreCoin.turnRed(); // <- Coin turns red for 0.5 seconds
					} catch (InterruptedException e) { // <- turnRed() has Thread.sleep, so we need this excpetion
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// If they can afford it:
				else {
					ITEM_BEING_PLACED = itemBeingBought; // <- Remember the item and place it when the user clicks the grid.
					SHOW_HIGHLIGHT = true; // <- Highlight the tile being hovered over on the grid until the item is placed.
				}
			}
		}
		else IN_STORE = false; // <- Anywhere else on screen means the mouse is not in the store.
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
				if (item instanceof Tree) {
					UI.drawImage(sprite, getGUIX(x)-16, getGUIY(y)-60); // <- The tree is a bigger image, so draw it at a bit of an offset to make it look nice.
				}
				else UI.drawImage(sprite, getGUIX(x), getGUIY(y)); // <- Everything else draw normally
			}
			else { // <- If the item doesn't have a sprite, draw a red circle instead.
				UI.setColor(Color.RED);
				UI.drawOval(getGUIX(x), getGUIY(y), SPRITE_WIDTH, SPRITE_HEIGHT);
			}
		}
	}
	
	/// drawStore:
	/** Draws all the StoreTiles as well as the coin icon / total money on screen. Used by drawWorld()
	*  
	*	@return ->			N/A.	
	*																														*/
	public static void drawStore() {
		
		for (StoreTile storeTile : storeTiles) {
			UI.drawString("$ "+storeTile.getItem().getPrice(), storeTile.getX()+16, storeTile.getY()-10); // <- Draw the item's price
			if (storeTile == HOVERED_STORE_TILE && IN_STORE) { // <- If the mouse is in the store area, check which
				storeTile.drawHighlight(); //					should be highlighted by the mouse and draw a different image.
			}
			else storeTile.drawTile();
		}
		// Draw the coin a bit further to the left than where the garden grid ends, and at the same y-position as the store
		StoreCoin.drawCoin(GARDEN_GRID_LEFT+GARDEN_GRID_WIDTH-112, STORE_GRID_TOP);
		
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

}
