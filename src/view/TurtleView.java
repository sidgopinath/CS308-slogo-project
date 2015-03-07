package view;

import java.text.DecimalFormat;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a turtle ImageView object that carries the properties and functionality of a turtle
 * @author Callie, Mengchao
 *
 */

public class TurtleView extends ImageView {

	private double myHeading;
	private boolean penUp = false;
	private int myID;
	private int myImageID;
	private Group myStamps;

	public TurtleView(int id, Image img) {
		super(img);
		setFitWidth(30);
		setFitHeight(30);
		penUp = false;
		myHeading = 0;
		myID = id;
		myImageID = 1;
		myStamps = new Group();
	}
	

	// relative movement
	public void move(double moveX, double moveY) {
		setTranslateX(moveX);
		setTranslateY(moveY);
	}

	// absolute movement
	public double setXY(double x, double y) {
		double newX = getTranslateX();
		double newY = getTranslateY();
		setTranslateX(x);
		setTranslateY(-y);
		return Math.sqrt(Math.pow(newX - x, 2) + Math.pow(newY - y, 2));
	}

	public double setRelativeHeading(double angle) {
		setRotate(adjustAngle(getRotate() + angle));
		myHeading = getRotate();
		return myHeading;
	}

	public double setAbsoluteHeading(double angle) {
		double old = getRotate();
		setRotate(adjustAngle(angle));
		myHeading = angle;
		return getRotate() - old;
	}

	public void setPenUp(boolean isUp) {
		penUp = isUp;
	}

	public double getHeading() {
		return myHeading;
	}

	public boolean getPenUp() {
		return penUp;
	}

	public String getPenPosition() {
		if (penUp == true){
			return "Up";
		}
		return "Down";
	}

	public String isShowing() {
		if (isVisible()){
			return "Visible";
		}
		return "Hidden";
	}

	public void showTurtle(boolean show) {
		setVisible(show);
	}

	public String getYCoord() {
	    DecimalFormat decimalFormat = new DecimalFormat("#.#");
		double yCoord = getTranslateY();
		if (yCoord == -0){
			return "0";
		}
		return decimalFormat.format(-1 * yCoord);
	}
	
	/**
	 * Normalizes a double parameter to an angle between 0-360
	 * @param angle
	 * @return normalized angle between 0-360
	 */
	private double adjustAngle(double angle) {
		angle = angle % 360;
		if (angle < 0) {
			angle = 360 - (-1) * angle;
		}
		return angle;
	}

	public int getID() {
		return myID;
	}
	
	public void setImageID(int id){
		myImageID = id;
	}
	
	public int getImageID(){
		return myImageID;
	}
	
	public int createStamp(Workspace workspace){
		ImageView newStamp = new ImageView(getImage());
		newStamp.setFitWidth(30);
		newStamp.setFitHeight(30);
		newStamp.setTranslateX(getX());
		newStamp.setTranslateY(Integer.parseInt(getYCoord()));
		myStamps.getChildren().add(newStamp);
		workspace.getChildren().add(myStamps);
		return getImageID();
	}
	
	public int clearStamps(Workspace workspace){
		myStamps.getChildren().clear();
		int stampsCleared = 0;
		if (myStamps.getChildren().size() > 0)
			stampsCleared = 1;
		myStamps = new Group(); //forget all values from old list
		workspace.getChildren().add(myStamps);
		return stampsCleared; 
	}
}