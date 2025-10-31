package ui;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ecs100.*;
import main.Main;
import model.Cow;
import model.GardenItem;
import manager.TaskManager;
import model.Task;
import util.Constants;

/* ====================================================================================================================	*/
/**
 * 	GUI: 				Interprets data from Main and other classes and displays that data to the user.
 *
 * 	@version  			1.0
 * 	@since    			1.0
 */
/* ====================================================================================================================	*/


public class GUI {

//	Fields:
	// For methods that needs to know how big the sprites are (32x32px):
	private static int SPRITE_WIDTH = 32; // <- Sprites will all be standardized to 32x32px in Photoshop
	private static int SPRITE_HEIGHT = 32;

	// Top and left of the grid:
	private static int GARDEN_GRID_TOP = 160; // <- Top and left where the garden grid should be drawn
	private static int GARDEN_GRID_LEFT = 448;

	// For methods that need to know the total height and width of the garden:
	private static int GARDEN_GRID_WIDTH = SPRITE_WIDTH*Main.getGardenWidth(); // <- Total width and height of the whole grid. This is for checking if the mouse is hovering over the grid.
	private static int GARDEN_GRID_HEIGHT = SPRITE_HEIGHT*Main.getGardenHeight();

	// Task display area:
	private static int TASK_DISPLAY_LEFT = 20;
	private static int TASK_DISPLAY_TOP = 160;
	private static int TASK_DISPLAY_WIDTH = 400;
	private static int TASK_DISPLAY_HEIGHT = 500;

//	Collections:
	private static HashMap<Integer, Integer> GUIxValues = new HashMap<Integer, Integer>(); // <- Converts X and Y values to scaled up co-ords of where those images should be drawn the on-screen grid.
	private static HashMap<Integer, Integer> GUIyValues = new HashMap<Integer, Integer>();

	private static ArrayList<Tile> tiles = new ArrayList<Tile>(); // <- Each square belongs to a 'tile' class. This is so the x and y position of tile the mouse is over can be stored and accessed.

//	Class-Wide Variables:
	Tile hoveredTile; // <- The tile currently being hovered over.
	private String currentCategory = Constants.CATEGORY_EASY_WINS; // Current task category being displayed

//	Constructor:
	public GUI() {

		// Initialize TaskManager and create default tasks
		TaskManager.init();
		ToDoUI.ensureDefaultTasks();

		createGrid(Main.getGardenWidth(), Main.getGardenHeight()); // <- Draws the grid of tiles
		convertXAndYValues(); // <- Fills the HashMaps with all x and y positions of the garden and their equivalent on the GUI. This is so the GUI knows where to draw an item from its x and y fields.

		UI.setMouseMotionListener(this::doMouse); // <- Create mouse listener

		// UI Buttons - Garden controls:
		UI.addButton("QUIT", UI::quit );
		UI.addButton("Buy cow", this::buy);

		// Task Category Buttons:
		UI.addButton("🟢 Easy Wins", () -> showTaskCategory(Constants.CATEGORY_EASY_WINS));
		UI.addButton("🥦 Nutrition", () -> showTaskCategory(Constants.CATEGORY_NUTRITION));
		UI.addButton("🏃 Movement", () -> showTaskCategory(Constants.CATEGORY_MOVEMENT));
		UI.addButton("💬 Connection", () -> showTaskCategory(Constants.CATEGORY_CONNECTION));
		UI.addButton("⏰ Productivity", () -> showTaskCategory(Constants.CATEGORY_PRODUCTIVITY));

		// Display initial task category
		showTaskCategory(Constants.CATEGORY_EASY_WINS);
	}

/* ====================================================================================================================	*/

	// Task display methods:

	/** Show tasks for a specific category */
	private void showTaskCategory(String category) {
		this.currentCategory = category;
		displayTasks();
	}

	/** Display tasks in the text area */
	private void displayTasks() {
		List<Task> tasks = TaskManager.getTasksByCategory(currentCategory);

		// Clear previous text
		UI.clearText();

		// Get category display name
		String displayName = ToDoUI.getCategoryDisplayName(currentCategory);
		UI.println("=== " + displayName + " ===");
		UI.println();

		if (tasks.isEmpty()) {
			UI.println("No tasks found in this category.");
		} else {
			for (Task task : tasks) {
				String checkbox = task.isCompleted() ? "[✓] " : "[ ] ";
				UI.println(checkbox + task.getTitle());
			}
		}

		UI.println();
		UI.println("Total tasks: " + tasks.size());
	}

	// Garden methods:

	public void buy() {
		Main.buy();
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

		// If the mouse is currently within the grid, do these things:
		if ( x > GARDEN_GRID_LEFT && x < GARDEN_GRID_LEFT+GARDEN_GRID_WIDTH && y > GARDEN_GRID_TOP && y < GARDEN_GRID_TOP+GARDEN_GRID_HEIGHT ) {

			// Check which tile the mouse is over, and set hoveredTile to that tile.
			for (Tile tile : tiles) {
				if ( tile.checkForHovered(x, y) ) {
					hoveredTile = tile;
					// Removed console spam - only log on click instead
				}
			}

			// When the user clicks on a tile:
			if ( action.equals("pressed") && hoveredTile != null ) {
				UI.println("Clicked tile at "+hoveredTile.getGridX()+"X and "+hoveredTile.getGridY()+"Y.");
				// Removed automatic cow spawning - user should use buy() method instead
			}

		}
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

	/// getGUIX:
	/** Takes an x value and returns where it should be drawn on screen.
	*
	*	@param int			x: The x value on the grid where the item should be drawn.
	*	@return ->			int: The GUI x coordinate, or -1 if invalid.
	*																														*/
	public static int getGUIX(int x) {
		Integer result = GUIxValues.get(x);
		if (result == null) {
			UI.println("Error: Invalid X coordinate: " + x);
			return -1;
		}
		return result;
	}

	/// getGUIY:
	/** Takes an y value and returns where it should be drawn on screen.
	*
	*	@param int			y: The y value on the grid where the item should be drawn.
	*	@return ->			int: The GUI y coordinate, or -1 if invalid.
	*																														*/
	public static int getGUIY(int y) {
		Integer result = GUIyValues.get(y);
		if (result == null) {
			UI.println("Error: Invalid Y coordinate: " + y);
			return -1;
		}
		return result;
	}

}
