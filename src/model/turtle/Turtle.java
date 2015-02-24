package model.turtle;

public class Turtle {
private double xPosition;
private double yPosition;
private double degreeHeading;
private boolean penDown;
private boolean isVisible;
private int id;
public Turtle(int inId){
	id = inId;
	isVisible = true;
	penDown = true;
	degreeHeading = 90;
	xPosition = 0;
	yPosition = 0;
}
public void setPenDown(boolean b){
	penDown = b;
}
}
