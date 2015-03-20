// This entire file is part of my masterpiece.
// Greg McKeon
package view;

import java.util.Observable;

public class ViewUpdateModule extends Observable{
	private double background = 0;
	private double penColor = 0;
	private double penSize = 2;
	private double shape = 0;
	private double turtle = 1;
	public double setBackground(double execute) {
		// TODO Auto-generated method stub
		background = execute;
		update();
		return background;
	}
	protected double getBackground(){
		return background;
	}
	public double setPenColor(double execute) {
		// TODO Auto-generated method stub
		penColor = execute;
		update();
		return penColor;
	}
	protected double getPenColor() {
		// TODO Auto-generated method stub
		return penColor;
	}
	public double setPenSize(double execute) {
		// TODO Auto-generated method stub
		penSize = execute;
		update();
		return penColor;
	}
	protected double getPenSize() {
		// TODO Auto-generated method stub
		return penSize;
	}
	private void update(){
		setChanged();
		notifyObservers();
	}
	public double setShape(double execute, int activeTurtle) {
		// TODO Auto-generated method stub
		shape = execute;
		turtle = activeTurtle;
		update();
		return execute;
	}
	protected double getShape() {
		// TODO Auto-generated method stub
		return shape;
	}
	protected double getTurtle() {
		// TODO Auto-generated method stub
		return turtle;
	}
}
