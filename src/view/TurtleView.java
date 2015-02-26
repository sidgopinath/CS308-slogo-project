//Turtle view class only accessible to the View

package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleView extends ImageView {

	private double myHeading;
	private boolean penUp;
	private boolean isVisible;

	// private int myID;

	public TurtleView(Image img) {
		super(img);
		setFitWidth(30);
		setFitHeight(30);
		penUp = false;
		myHeading = 0;
		isVisible = true;
	}

	public void move(double moveX, double moveY) {
		setTranslateX(moveX);
		setTranslateY(moveY);
	}

	public void turn(double angle) {
		setRotate(angle);
	}

	public void setXY(double x, double y) {
		System.out.println("go to " + x + "," + y);
		setTranslateX(x);
		setTranslateY(y);
	}

	public void setHeading(double angle) {
		setRotate(angle);
		
		myHeading = myHeading + angle;
		if (myHeading > 360)
			myHeading = myHeading % 360;
	}
	
	public double getHeading() {
		return myHeading;
	}
	
	public void setPenUp(boolean isUp){
		penUp = isUp;
	}
	
	public boolean getPenUp(){
		return penUp;
	}
	
	public boolean isShowing(){
		return isVisible;
	}
	public void showTurtle(boolean show){
		setVisible(show);
		isVisible = show;
	}
}
