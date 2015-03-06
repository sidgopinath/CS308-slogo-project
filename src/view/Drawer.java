package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import model.Polar;
import model.turtle.TurtleCommand;

public class Drawer {
	private Color myColor;
	private double[] myXBounds = new double[2];
	private double[] myYBounds = new double[2];
	private double[] myHalf = new double[2];
	private Workspace myWorkspace;

	public Drawer(Workspace workspace){//double xMax, double yMax) {
		myWorkspace = workspace;
		double xMax = myWorkspace.getGridWidth();
		double yMax = myWorkspace.getGridHeight();
		myXBounds[0] = 0;
		myXBounds[1] = xMax+5;
		myYBounds[0] = 0;
		myYBounds[1] = yMax;
		myHalf[0] = 0.5 * (myXBounds[1] - myXBounds[0]);
		myHalf[1] = 0.5 * (myYBounds[1] - myYBounds[0]);
		myColor = Color.BLACK;
	}

	// may have to remove list from turtlecommand
	// TODO: remove list if it is no longer a list being used
	
	//get rid of list of turtlecommands if there is only one
	public List<Polyline> draw(Map<Integer, TurtleView> turtles,
			List<TurtleCommand> instructions, SideBar sidebar) {
		ArrayList<Polyline> lines = new ArrayList<Polyline>();
		Iterator<TurtleCommand> it = instructions.iterator();
		while (it.hasNext()) {
			TurtleCommand command = it.next();
			System.out.println(command.getTurtleId());
			TurtleView turtle = turtles.get(command.getTurtleId());
			System.out.println(turtle);
			Polar polar = command.getPolar();
			// move turtle and draw line
			if (polar.myDistance != 0) {
				double angle = turtle.getRotate();
				double turtleX = turtle.getLayoutX();
				double turtleY = turtle.getLayoutY();
				double moveX = Math.sin(Math.toRadians(polar.myAngle + 180 - angle))
						* polar.myDistance;
				double moveY = Math.cos(Math.toRadians(polar.myAngle + 180 - angle))
						* polar.myDistance;
				double startX = turtleX + turtle.getTranslateX() + turtle.getFitWidth()
						/ 2;
				double startY = turtleY + turtle.getTranslateY() + turtle.getFitHeight()
						/ 2;
				double newX = turtleX + moveX + turtle.getTranslateX() + 15;
				double newY = turtleY + moveY + turtle.getTranslateY() + 15;
				if (newY < myYBounds[0]) {
					wrapY(1, turtle, polar, lines, 0, newY, startX, moveX, moveY,
							turtleY, startY);
				} else if (newY > myYBounds[1]) {
					wrapY(1, turtle, polar, lines, 1, newY, startX, moveX, moveY,
							turtleY, startY);
				} else if (newX < myXBounds[0]) {
					wrapX(0, turtle, polar, lines, 0, newX, startY, moveY, moveX,
							turtleX, startX);
				} else if (newX > myXBounds[1]) {
					wrapX(0, turtle, polar, lines, 1, newX, startY, moveY, moveX,
							turtleX, startX);
				} else {
					turtle.move(turtle.getTranslateX() + moveX, turtle.getTranslateY()
							+ moveY);
					double endX = startX + moveX;
					double endY = startY + moveY;
					if (!turtle.getPenUp()) {
						Polyline polyline = new Polyline();
						polyline.setStroke(myColor);
						polyline.getPoints().addAll(
								new Double[] { startX, startY, endX, endY });
						lines.add(polyline);
					}
				}
			} else {
				if (command.isRelative()) {
					turtle.setRelativeHeading(polar.myAngle);
				} else {
					turtle.setAbsoluteHeading(polar.myAngle);
				}
			}
			sidebar.updateTurtleProperties(command.getTurtleId(), myWorkspace);
		}

		return lines;
	}

	private void wrapY(int dir, TurtleView turtle, Polar polar,
			ArrayList<Polyline> lines, int i, double newY, double startX, double moveX,
			double moveY, double turtleY, double startY) {
		double endX1 = startX + moveX
				* Math.abs((Math.pow(-1, i) * (myYBounds[i] - newY + moveY)) / moveY);
		double endX2 = startX + moveX;
		System.out.println(Math.pow(-1, i) * myHalf[dir] - myYBounds[i] + newY);
		turtle.move(turtle.getTranslateX() + moveX, Math.pow(-1, i) * myHalf[dir]
				- myYBounds[i] + newY);
		if (!turtle.getPenUp()) {
			double endY = turtle.getTranslateY() + turtleY + turtle.getFitHeight() / 2;
			lines.add(drawLine(startX, startY, endX1, myYBounds[i]));
			lines.add(drawLine(endX1, myYBounds[1 - i], endX2, endY));
		}
	}

	private void wrapX(int dir, TurtleView turtle, Polar polar,
			ArrayList<Polyline> lines, int i, double newX, double startY, double moveY,
			double moveX, double turtleX, double startX) {
		double endY1 = startY + moveY
				* Math.abs(Math.pow(-1, i) * (newX - moveX - myXBounds[i]) / moveX);
		double endY2 = startY + moveY;
		turtle.move(Math.pow(-1, i) * myHalf[dir] - myXBounds[i] + newX,
				turtle.getTranslateY() + moveY);
		if (!turtle.getPenUp()) {
			double endX = turtle.getTranslateX() + turtleX + turtle.getFitWidth() / 2;
			lines.add(drawLine(startX, startY, myXBounds[i], endY1));
			lines.add(drawLine(myXBounds[1 - i], endY1, endX, endY2));
		}
	}

	private Polyline drawLine(double startX, double startY, double endX, double endY) {
		Polyline polyline = new Polyline();
		polyline.setStroke(myColor);
		polyline.getPoints().addAll(new Double[] { startX, startY, endX, endY });
		return polyline;
	}

	/*
	 * private getEditedTurtleID(List<TurtleCommand> instructions){
	 * Iterator<TurtleCommand> it = instructions.iterator(); while
	 * (it.hasNext()) { TurtleCommand command = it.next(); return
	 * command.getTurtleId(); } }
	 */
	// It may be better to remove this method above and directly pass the
	// updates to the sidebar

	public void changeColor(Color c) {
		myColor = c;
	}

	/*
	 * public void setPenUp(boolean isUp){ penUp = isUp; }
	 */
}
