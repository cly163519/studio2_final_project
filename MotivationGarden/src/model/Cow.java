package model;

public class Cow extends Animal {
	
	// PROTECTED FIELDS INHERITED FROM 'ITEM':
	// x and y position, sprite (file path to image that GUI will draw)
	
	public Cow(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.imagePath = "../MotivationGarden/src/model/cow.png";
	}
	
}
