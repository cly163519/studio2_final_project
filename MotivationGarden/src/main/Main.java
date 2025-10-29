package main;

import ecs100.*;
import ui.GUI;

public class Main {
	
//	Collections:
	/// Program-wide ArrayLists will go here, like a list of all items, to do tasks, etc. 

//	Class-Wide Variables:
	
	private static int GARDEN_WIDTH = 17;
	private static int GARDEN_HEIGHT = 17;

/* ====================================================================================================================	*/

	public Main() {
		
		GUI GUI = new GUI(); // <- Creates GUI
		//String test = Console.askString("Type a string:");
		//Console.println(test);
		
		

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
	
/* ====================================================================================================================	*/
	
	public static void main(String[] args) {
		UI.initialise();
		new Main();
	}

}
