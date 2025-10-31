package main;

import java.util.ArrayList;

import javax.swing.*;

import ecs100.*;
import manager.StoreManager;
import model.*;
import ui.GUI;
import ui.TodoPanel;

public class Main {

    // Collections for farm
    static ArrayList<GardenItem> items = new ArrayList<>();
    static ArrayList<GardenItem> newItems = new ArrayList<>();

    private static int GARDEN_WIDTH = 17;
    private static int GARDEN_HEIGHT = 17;

    public Main() throws InterruptedException {

        // Start farm money
        StoreManager.setMoney(100);

        // Create GUI for farm
        GUI gui = new GUI();

        // Add some initial animals/trees/flowers
        items.add(new Cow(3, 3));
        items.add(new Pig(3, 1));
        items.add(new Chicken(4, 10));
        items.add(new Flower(5, 2));
        items.add(new Tree(6, 5));

        // Draw initial farm
        drawWorld();

        // Launch To-Do list window
        SwingUtilities.invokeLater(() -> {
            JFrame todoFrame = new JFrame("To-Do List with Coins");
            todoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            todoFrame.add(new TodoPanel());
            todoFrame.setSize(400, 500);
            todoFrame.setLocation(50, 50); // separate from farm window
            todoFrame.setVisible(true);
        });

        // Farm world update loop
        updateWorld();
    }

    public static void buy() {
        UI.println("You have $" + StoreManager.getMoney());
        String input = UI.askString("What to buy: ");
        int positionX = UI.askInt("X Position: ");
        int positionY = UI.askInt("Y position: ");
        switch (input.toUpperCase()) {
            case "COW":
                Cow newCow = new Cow(positionX, positionY);
                if (StoreManager.canBuy(newCow)) {
                    newItems.add(newCow);
                    StoreManager.setMoney(StoreManager.getMoney() - newCow.getPrice());
                } else UI.println("You can't afford that!");
                break;
            case "CHICKEN":
                Chicken newChicken = new Chicken(positionX, positionY);
                if (StoreManager.canBuy(newChicken)) {
                    newItems.add(newChicken);
                    StoreManager.setMoney(StoreManager.getMoney() - newChicken.getPrice());
                } else UI.println("You can't afford that!");
                break;
            case "PIG":
                Pig newPig = new Pig(positionX, positionY);
                if (StoreManager.canBuy(newPig)) {
                    newItems.add(newPig);
                    StoreManager.setMoney(StoreManager.getMoney() - newPig.getPrice());
                } else UI.println("You can't afford that!");
                break;
            case "TREE":
                Tree newTree = new Tree(positionX, positionY);
                if (StoreManager.canBuy(newTree)) {
                    newItems.add(newTree);
                    StoreManager.setMoney(StoreManager.getMoney() - newTree.getPrice());
                } else UI.println("You can't afford that!");
                break;
            case "FLOWER":
                Flower newFlower = new Flower(positionX, positionY);
                if (StoreManager.canBuy(newFlower)) {
                    newItems.add(newFlower);
                    StoreManager.setMoney(StoreManager.getMoney() - newFlower.getPrice());
                } else UI.println("You can't afford that!");
                break;
            default:
                UI.println("Input not recognized.");
        }
    }

    public void drawWorld() {
        for (GardenItem item : items) {
            GUI.drawItem(item);
        }
    }

    public void updateWorld() throws InterruptedException {
        while (true) {
            for (GardenItem item : items) {
                if (item instanceof Animal) {
                    item.moveRandomly();
                    UI.clearGraphics();
                    drawWorld();
                }
            }
            if (!newItems.isEmpty()) {
                items.addAll(newItems);
                newItems.clear();
            }
            Thread.sleep(1000);
        }
    }

    public static int getGardenHeight() { return GARDEN_HEIGHT; }
    public static int getGardenWidth() { return GARDEN_WIDTH; }

    public static int randomInt(int minValue, int maxValue) {
        return minValue + (int)(Math.random() * (maxValue - minValue + 1));
    }

    public static void main(String[] args) throws InterruptedException {
        UI.initialise();
        new Main();
    }
}
