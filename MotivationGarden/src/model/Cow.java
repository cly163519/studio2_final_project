package model;

import util.Constants;

public class Cow extends Animal {
	
	// PROTECTED FIELDS INHERITED FROM 'ITEM':
	// x and y position, imagePath (file path to image that GUI will draw)
	
	String type;
	
	public Cow(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.type = "cow";
	}
	
	@Override
    public String getImagePath() {
        return Constants.ANIMAL_IMAGE_DIR + this.type + ".png";
    }
	
}
