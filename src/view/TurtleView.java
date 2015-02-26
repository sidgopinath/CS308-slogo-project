//Turtle view class only accessible to the View

package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleView extends ImageView {

	private double myHeading;
	private boolean isVisible;
	private boolean penUp=false;
	private double myXCoord;
	private double myYCoord;

	// private int myID;

	public TurtleView(Image img) {
		super(img);
		setFitWidth(30);
		setFitHeight(30);
		penUp = false;
		myHeading = 0;
		isVisible = true;
	}

	//relative
	public void move(double moveX, double moveY) {
		setTranslateX(moveX);
		setTranslateY(moveY);
	}

	//absolute
	public double setXY(double x, double y) {
        double newX=getTranslateX();
        double newY=getTranslateY();
		setTranslateX(x);
		setTranslateY(-y);
		return Math.sqrt(Math.pow(newX-x,2)+Math.pow(newY-y, 2));
	}
	

	public void setRelativeHeading(double angle) {
		myHeading = myHeading + angle; 
		myHeading = adjustAngle(myHeading);
		setRotate(myHeading);
	}
	
	public void setAbsoluteHeading(double angle){
		double distanceToMove = myHeading - angle;
		myHeading = adjustAngle(myHeading - distanceToMove);
		setRotate(myHeading);
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
	
	public double getYCoord(){
		return -1 * getTranslateY();	
	}
	
	private double adjustAngle(double angle){
		angle = angle % 360;
		if (angle < 0){
			angle = 360 - (-1)*angle;
		}
		return angle;
	}
}
