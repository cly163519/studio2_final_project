package main;
import java.util.ArrayList;
import javax.swing.*;
import ecs100.*;
import manager.StoreManager;
import manager.TaskManager;
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
        
        // STARTING ITEMS:
        plants.add(new Tree(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); // <- Spawn two trees somewhere random on the grid.
        plants.add(new Tree(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT)));
        plants.add(new Flower(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); // <- Spawn three flowers somewhere random on the grid.
        plants.add(new Flower(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); 
        plants.add(new Flower(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); 
        // Randomly spawn either a cow, pig, or chicken to start:
        int randomNumber = randomInt(1, 3);
        switch (randomNumber) {
        case 1: animals.add(new Cow(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); 		break;
        case 2: animals.add(new Pig(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); 		break;
        case 3: animals.add(new Chicken(randomInt(1, GARDEN_WIDTH), randomInt(1, GARDEN_HEIGHT))); 	break;
        default: break;
        }

        GUI.drawWorld();

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
            GUI.drawWorld();
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
	
	/** Returns the the world's plants ArrayList.
	*  
	*	@param param		N/A.
	*	@return ->			ArrayList<Animal>.	
	*																														*/
	public static ArrayList<Plant> getPlants() {
		return plants;
	}
	
	/** Returns the the world's animals ArrayList.
	*  
	*	@param param		N/A.
	*	@return ->			ArrayList<Animal>.	
	*																														*/
	public static ArrayList<Animal> getAnimals() {
		return animals;
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
    
	/** Returns a random double between two values.
	*  
	*	@param double		minValue: the minimum number that can be returned.
	*	@param double		maxValue: the maximum number that can be returned.
	*	@return ->			double.	
	*																														*/
    public static double randomDouble(double minValue, double maxValue) {
        return minValue + (Math.random() * (maxValue));
    }

/* ====================================================================================================================	*/

    public static void main(String[] args) throws InterruptedException {
        UI.initialise();
        new Main();
    }
}
