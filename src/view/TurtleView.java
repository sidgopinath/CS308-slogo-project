//Turtle view class only accessible to the View

package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleView extends ImageView {

	private int myHeading;
	private boolean penUp=false;

	// private int myID;

	public TurtleView(Image img) {
		super(img);
		setFitWidth(30);
		setFitHeight(30);
		myHeading = 0;
	}

	public void move(double moveX, double moveY) {
		setTranslateX(moveX);
		setTranslateY(moveY);
	}

	public void turn(double angle) {
		setRotate(angle);
	}

	public double setXY(double originX, double originY, double x, double y) {
        double newX=getTranslateX();
        double newY=getTranslateY();
		setTranslateX(x);
		setTranslateY(-y);
		return Math.sqrt(Math.pow(newX-x,2)+Math.pow(newY-y, 2));
	}

	public void setHeading(double angle) {
		setRotate(angle);
	}
	
	public void setPenUp(boolean isUp){
		penUp = isUp;
	}
	
	public boolean getPenUp(){
		return penUp;
	}

}
