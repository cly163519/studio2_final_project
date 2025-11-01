package main;
import java.util.ArrayList;
import javax.swing.*;
import ecs100.*;
import manager.StoreManager;
import manager.TaskManager; // ✅ added import for shared coin system
import model.*;
import ui.GUI;
import ui.TodoPanel;

/* ====================================================================================================================	*/
/**
 * 	Main: 				Launches program and handles logic shared across classes.
 *
 * 	@version  			1.0
 * 	@since    			1.0
 * 
 */
/* ====================================================================================================================	*/

public class Main {

    // Collections:
    static ArrayList<Animal> animals = new ArrayList<>(); // <- Animals and Plants are separated so they can be read by JSON files 
    static ArrayList<Plant> plants = new ArrayList<>();
    
    static ArrayList<GardenItem> newItems = new ArrayList<>(); // <- Any new items are added to this list first, 
    															// then added to the plants and animals lists at the *end* of 
    															// the drawWorld method. This is avoid concurrent modification.
    // Class-Wide variables:
    private static int GARDEN_WIDTH = 16; // <- Width/height of the garden. Used by GUI and by moving animals to find
    private static int GARDEN_HEIGHT = 16; //	out were they're allowed to go.

    public Main() throws InterruptedException {

        // ✅ Initialize the shared money system
        TaskManager.addCoins(100);   // starting coins for both farm + To-Do list
        StoreManager.init();         // sync StoreManager.money with TaskManager.money

        GUI gui = new GUI(); // <- Create GUI

        // Add some initial items
        animals.add(new Cow(3, 3));
        animals.add(new Pig(3, 1));
        animals.add(new Chicken(4, 10));
        plants.add(new Flower(5, 2));
        plants.add(new Tree(6, 5));

        drawWorld();

        // ✅ Launch To-Do list window (shows the same coin balance)
        SwingUtilities.invokeLater(() -> {
            JFrame todoFrame = new JFrame("To-Do List with Coins");
            todoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            todoFrame.add(new TodoPanel());
            todoFrame.setSize(400, 500);
            todoFrame.setLocation(50, 50); // separate from farm window
            todoFrame.setVisible(true);
        });

        updateWorld(12); // <- Start updating farm continuously.
    }

/* ====================================================================================================================	*/

    // ✅ Unified buy method using shared money system
    public static void buy() {
        UI.println("You have $" + StoreManager.getMoney());
        String input = UI.askString("What to buy: ");
        int positionX = UI.askInt("X Position: ");
        int positionY = UI.askInt("Y Position: ");

        GardenItem newItem = null;

        switch (input.toUpperCase()) {
            case "COW": newItem = new Cow(positionX, positionY); break;
            case "CHICKEN": newItem = new Chicken(positionX, positionY); break;
            case "PIG": newItem = new Pig(positionX, positionY); break;
            case "TREE": newItem = new Tree(positionX, positionY); break;
            case "FLOWER": newItem = new Flower(positionX, positionY); break;
            default:
                UI.println("Input not recognized.");
                return;
        }

        if (StoreManager.buyItem(newItem)) { // ✅ deducts shared coins automatically
            newItems.add(newItem);
            UI.println("Bought " + input + "! Coins left: " + StoreManager.getMoney());
        } else {
            UI.println("You can't afford that!");
        }
    }
    
	/// drawWorld:
	/** Runs the GUI's draw methods on all gardenItems + static images that need to be on screen..
	*  
	*	@return ->			N/A.	
	*																														*/
    public void drawWorld() {
    	GUI.drawStaticImages();
    	GUI.createStore();
        for (GardenItem animal : animals) {
            GUI.drawItem(animal);
        }
        for (GardenItem plant : plants) {
            GUI.drawItem(plant);
        }
    }
    
	/// updateWorld:
	/** Moves animals, adds any GardenItems that were bought from the store to the world, draw world, then waits to repeat.
	*  
	*   @param int			frameRate: How many frames per second the world should be updated.
	*	@return ->			N/A.	
	*																														*/
    public void updateWorld(int frameRate) throws InterruptedException {
        while (true) {
            for (Animal animal : animals) {
                    animal.moveRandomly(); // <- Move animals
                }
            if (newItems.size() > 0) { // <- Add any animals and plants that were bought and stored in newItems to the world.
            	for (GardenItem item : newItems) {
            		if (item instanceof Animal) {
            			animals.add((Animal) item);
            		}
            		else if (item instanceof Plant) {
            			plants.add((Plant) item);
            		}
            	}
            	newItems.clear(); // <- Clear newItems now that they're in the right place.
            }
            UI.clearGraphics();
            drawWorld();
            Thread.sleep(1000/frameRate); // <- Repeat after 1/frameRate seconds.
        }
    }
    
	/** Adds a garden item to the newItems list. UpdateWorld will place it in the world after the next update.
	*  
	*	@param GardenItem	item: Item to be added to database.
	*	@return ->			N/A.	
	*																														*/
	public static void addToWorld(GardenItem item) {
		newItems.add(item);
	}
    
	/** Returns the garden's height.
	*  
	*	@param param		N/A.
	*	@return ->			int.	
	*																														*/
	public static int getGardenHeight() {
		return GARDEN_HEIGHT;
	}
	
	/** Returns the garden's width.
	*  
	*	@param param		N/A.
	*	@return ->			int.	
	*																														*/
	public static int getGardenWidth() {
		return GARDEN_WIDTH;
	}

	/** Returns a random integer between two values.
	*  
	*	@param int			minValue: the minimum number that can be returned.
	*	@param int			maxValue: the maximum number that can be returned.
	*	@return ->			int.	
	*																														*/
    public static int randomInt(int minValue, int maxValue) {
        return minValue + (int)(Math.random() * (maxValue - minValue + 1));
    }

/* ====================================================================================================================	*/

    public static void main(String[] args) throws InterruptedException {
        UI.initialise();
        new Main();
    }
}
