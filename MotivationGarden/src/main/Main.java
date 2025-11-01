package main;

import java.util.ArrayList;
import javax.swing.*;

import ecs100.*;
import manager.StoreManager;
import manager.TaskManager; // ✅ added import for shared coin system
import model.*;
import ui.GUI;
import ui.TodoPanel;

public class Main {

    // Collections for farm items
	
    static ArrayList<Animal> animals = new ArrayList<>(); // <- Animals and Plants are separated so they can be read by JSON files 
    static ArrayList<Plant> plants = new ArrayList<>();
    
    static ArrayList<GardenItem> newItems = new ArrayList<>();

    // Garden grid size
    private static int GARDEN_WIDTH = 16;
    private static int GARDEN_HEIGHT = 16;

    public Main() throws InterruptedException {

        // ✅ Initialize the shared money system
        TaskManager.addCoins(100);   // starting coins for both farm + To-Do list
        StoreManager.init();         // sync StoreManager.money with TaskManager.money

        // ✅ Create GUI for farm
        GUI gui = new GUI();

        // Add some initial items
        animals.add(new Cow(3, 3));
        animals.add(new Pig(3, 1));
        animals.add(new Chicken(4, 10));
        plants.add(new Flower(5, 2));
        plants.add(new Tree(6, 5));

        // Draw initial farm
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

        // Run farm update loop
        updateWorld();
    }

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

    public void drawWorld() {
    	GUI.drawStaticImages();
        for (GardenItem animal : animals) {
            GUI.drawItem(animal);
        }
        for (GardenItem plant : plants) {
            GUI.drawItem(plant);
        }
    }

    public void updateWorld() throws InterruptedException {
        while (true) {
            for (Animal animal : animals) {
                    animal.moveRandomly();
                    UI.clearGraphics();
                    drawWorld();
                }
            if (newItems.size() > 0) {
            	for (GardenItem item : newItems) {
            		if (item instanceof Animal) {
            			animals.add((Animal) item);
            		}
            		else if (item instanceof Plant) {
            			plants.add((Plant) item);
            		}
            	}
            }
            Thread.sleep(1000);
        }
    }
    


    // Garden helpers
    public static int getGardenHeight() { return GARDEN_HEIGHT; }
    public static int getGardenWidth() { return GARDEN_WIDTH; }

    public static int randomInt(int minValue, int maxValue) {
        return minValue + (int)(Math.random() * (maxValue - minValue + 1));
    }

    // Entry point
    public static void main(String[] args) throws InterruptedException {
        UI.initialise();
        new Main();
    }
}
