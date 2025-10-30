package main;

import java.util.ArrayList;

import ecs100.*;
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
	ArrayList<GardenItem> items = new ArrayList<GardenItem>();

//	Class-Wide Variables:
	
	private static int GARDEN_WIDTH = 17;
	private static int GARDEN_HEIGHT = 17;

/* ====================================================================================================================	*/

	public Main() throws InterruptedException {
		
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
			UI.println(pig.getPositionX());
			UI.println(pig.getPositionY());
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
					UI.println(item.getPositionX());
					UI.println(item.getPositionY());
					UI.clearGraphics();
					drawWorld();
				}
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
