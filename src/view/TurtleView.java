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

	public void setXY(double x, double y) {
		System.out.println("go to " + x + "," + y);
		setTranslateX(x);
		setTranslateY(y);
	}

	public void setRelativeHeading(double angle) {
		setRotate(myHeading + angle);
		
		System.out.println("angle");
		System.out.println(getRotate());
		
		myHeading = myHeading + angle; 
		myHeading = adjustAngle(myHeading);
		
		System.out.println("angle adjusted");
		System.out.println(myHeading);

	}
	
	public void setAbsoluteHeading(double angle){
		double distanceToMove = myHeading - angle;
		myHeading = adjustAngle(myHeading - distanceToMove);
	}
	
	public void setPenUp(boolean isUp){
		penUp = isUp;
	}
	
	public double getHeading(){
		return myHeading;
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
	
	private double adjustAngle(double angle){
		angle = angle % 360;
		if (angle < 0){
			angle = 360 - (-1)*angle;
		}
		return angle;
	}
}
