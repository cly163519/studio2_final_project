package model;

import main.Main;
import util.Constants;

public class Chicken extends Animal {
	
	// PROTECTED FIELDS INHERITED FROM 'ITEM':
	// x and y position, imagePath (file path to image that GUI will draw)
	
	String type;
	int moveChance;
	
	public Chicken(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.type = "chicken";
		this.moveChance = 8;
		this.price = 18;
	}
	
	@Override
    public String getImagePath() {
        return Constants.ANIMAL_IMAGE_DIR + this.type + ".png";
    }
	
	@Override
    public void moveRandomly() {
		int randomNumber = Main.randomInt(1, 10);
		if ( randomNumber >= moveChance ) {
			
			int moveX = Main.randomInt(-1, 1);
	        if (this.positionX + moveX >= 1 && this.positionX + moveX <= Main.getGardenWidth() ) {
	        	this.positionX += moveX;
	        }
	        int moveY = Main.randomInt(-1, 1);
	        
	        if (this.positionY + moveY >= 1 && this.positionY + moveY <= Main.getGardenHeight() ) {
	        	this.positionY += moveY;
	        }
		}
    }
	
}
