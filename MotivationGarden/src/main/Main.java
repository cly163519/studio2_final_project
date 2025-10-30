package main;

import java.util.ArrayList;

import ecs100.*;
import manager.StoreManager;
import model.Animal;
import model.Chicken;
import model.Cow;
import model.Flower;
import model.GardenItem;
import model.Pig;
import model.Tree;
import ui.GUI;

public class Main {
	
//	Collections:
	static ArrayList<GardenItem> items = new ArrayList<GardenItem>();
	static ArrayList<GardenItem> newItems = new ArrayList<GardenItem>(); // <- Items to be added at the end of the next updateWorld (to avoid concurrent modification exception)

//	Class-Wide Variables:
	
	private static int GARDEN_WIDTH = 17;
	private static int GARDEN_HEIGHT = 17;

/* ====================================================================================================================	*/

	public Main() throws InterruptedException {
		
		StoreManager.setMoney(100);
		
		GUI GUI = new GUI(); // <- Creates GUI  
		//String test = Console.askString("Type a string:");
		//Console.println(test);
		
		Cow cow = new Cow(3, 3);
		items.add(cow);
		Pig pig = new Pig(3, 1);
		items.add(pig);
		Chicken chicken = new Chicken(4, 10);
		items.add(chicken);
		Flower flower = new Flower(5, 2);
		items.add(flower);
		Tree tree = new Tree(6, 5);
		items.add(tree);
		
		updateWorld();
		drawWorld();
		
		UI.println(cow.getImagePath());
		
		for (int i = 0 ; i < 5 ; i++) {
			pig.moveRandomly();
			/*UI.println(pig.getPositionX());
			UI.println(pig.getPositionY());*/
		}

	}
	
	public static void buy() {
		UI.println("You have $"+StoreManager.getMoney());
		String input = UI.askString("What to buy: ");
		int positionX = UI.askInt("X Position: ");
		int positionY = UI.askInt("Y position: ");
		switch (input) {
			case "COW": 
				Cow newCow = new Cow(positionX, positionY);
				if (StoreManager.canBuy(newCow)) {
					newItems.add(newCow); 
					StoreManager.setMoney( StoreManager.getMoney() - newCow.getPrice() );
				}
				else UI.println("You can't afford that!");
				break;
			case "CHICKEN": 
				Chicken newChicken = new Chicken(positionX, positionY);
				if (StoreManager.canBuy(newChicken)) {
					newItems.add(newChicken);
					StoreManager.setMoney( StoreManager.getMoney() - newChicken.getPrice() );
				}
				else UI.println("You can't afford that!");
				break;
			case "PIG": 
				Pig newPig = new Pig(positionX, positionY);
				if (StoreManager.canBuy(newPig)) {
					newItems.add(newPig); 
					StoreManager.setMoney( StoreManager.getMoney() - newPig.getPrice() );
				}
				else UI.println("You can't afford that!");
				break;
			case "TREE": 
				Tree newTree = new Tree(positionX, positionY);
				if (StoreManager.canBuy(newTree)) {
					newItems.add(newTree); 
					StoreManager.setMoney( StoreManager.getMoney() - newTree.getPrice() );
				}
				else UI.println("You can't afford that!");
				break;
			case "FLOWER": 
				Flower newFlower = new Flower(positionX, positionY);
				if (StoreManager.canBuy(newFlower)) {
					newItems.add(newFlower); 
					StoreManager.setMoney( StoreManager.getMoney() - newFlower.getPrice() );
				}
				else UI.println("You can't afford that!");
				break;
			default:
				UI.println("Input not recognized.");
				break;
		}
	}

	/** drawworld.
	*  
	*	@param param		N/A.
	*	@return ->			int.	
	*																														*/
	public void drawWorld() {
		for (GardenItem item : items) {
			GUI.drawItem(item);
		}
	}
	
	/** updateworld.
	*  
	*	@param param		N/A.
	*	@return ->			int.	
	 * @throws InterruptedException 
	*																														*/
	public void updateWorld() throws InterruptedException {
		
		while (true) {
			for (GardenItem item : items) {
				if (item instanceof Animal) {
					item.moveRandomly();
					/*UI.println(item.getPositionX());
					UI.println(item.getPositionY());*/
					UI.clearGraphics();
					drawWorld();
				}
			}
			if (newItems.size() > 0) {
				items.addAll(newItems);
				newItems.clear();
			}
			Thread.sleep(1000);
		}
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
	
	/** Returns a random integer.
	*  
	*	@param param		N/A.
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
