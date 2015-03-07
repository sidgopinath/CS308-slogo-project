package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import model.Polar;
import model.TurtleCommand;

public class Drawer {
	
	private Color myColor;
	private double[] myXBounds = new double[2];
	private double[] myYBounds = new double[2];
	private double[] myHalf = new double[2];
	private Workspace myWorkspace;
	private double strokeSize;
	private int myIndex;
	
	public Drawer(Workspace workspace){
		myWorkspace = workspace;
		strokeSize = 1;
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
	
	public void setStroke(Double size){
		strokeSize = size;
	}

	// may have to remove list from turtlecommand
	// TODO: remove list if it is no longer a list being used
	public List<Polyline> draw(Map<Integer, TurtleView> turtles,
			List<TurtleCommand> instructions, SideBar sidebar) {

		List<Polyline> lines = new ArrayList<Polyline>();
		Iterator<TurtleCommand> it = instructions.iterator();
		System.out.println("draweing");
		
		while (it.hasNext()) {
			TurtleCommand command = it.next();
			TurtleView turtle = turtles.get(command.getTurtleId());
			Polar polar = command.getPolar();
			
			if (polar.getDistance() != 0) {
				double angle = turtle.getRotate();
				double turtleX = turtle.getLayoutX();
				double turtleY = turtle.getLayoutY();
				double moveX = Math.sin(Math.toRadians(polar.getAngle() + 180 - angle))
						* polar.getDistance();
				double moveY = Math.cos(Math.toRadians(polar.getAngle() + 180 - angle))
						* polar.getDistance();

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
						System.out.println("drawing" + turtle.getPenUp());
						Polyline polyline = new Polyline();
						polyline.setStroke(myColor);
						System.out.println(strokeSize);
						polyline.setStrokeWidth(strokeSize);
						polyline.getPoints().addAll(
								new Double[] { startX, startY, endX, endY });
						//drawLine(startX, startY, endX, endY);
						lines.add(polyline);
					}
				}
			} 
			else {
				if (command.isRelative()) {
					turtle.setRelativeHeading(polar.getAngle());
				} 
				else {
					turtle.setAbsoluteHeading(polar.getAngle());
				}
			}
			sidebar.updateTurtleProperties(command.getTurtleId(), myWorkspace);
		}
		return lines;
	}

//	
//	private void animate(TurtleView turtle,List<Polyline> lines, Group target) {
//		myIndex = 0;
//        Timeline animation = new Timeline();
//		KeyFrame animate = new KeyFrame(Duration.millis(30),
//				e -> updateKeyFrame(turtle,lines, target));
//		animation.getKeyFrames().add(animate);
//		animation.setCycleCount(lines.size());
//		animation.play();
//	}
//	
//	private void updateKeyFrame(TurtleView turtle,List<Polyline> lines, Group target) {
//		System.out.println("turtle was at "+ turtle.getTranslateX()+" "+ turtle.getTranslateY());
//		target.getChildren().add(lines.get(myIndex));
//		turtle.move(turtle.getTranslateX()+(lines.get(1).getPoints().get(2)-lines.get(0).getPoints().get(2)), turtle.getTranslateY()+(lines.get(1).getPoints().get(3)-lines.get(0).getPoints().get(3)));
//		myIndex++;
//	}
//	
//	private List<Polyline> animator(double startX,
//			double startY, double endX, double endY) {
//		List<Polyline> myLines = new ArrayList<Polyline>();
//		for	(int i=0; i<10; i++){
//			Polyline pLine = new Polyline();
//			pLine.setStroke(myColor);
//			pLine.setStrokeWidth(strokeSize);
//			pLine.getPoints().addAll(
//					new Double[] { startX+(double)i/10*(endX-startX), startY+(double)i/10*(endY-startY), startX+(double)(i+1)/10*(endX-startX), startY+(double)(i+1)/10*(endY-startY)});
//			System.out.println("adding line from "+ startX+(double)i/10*(endX-startX)+" "+startY+(double)i/10*(endY-startY)+ " to "+startX+(double)(i+1)/10*(endX-startX)+ " "+ startY+(double)(i+1)/10*(endY-startY));
//			myLines.add(pLine);
//		}
//		return myLines;
//	}
	private void wrapY(int dir, TurtleView turtle, Polar polar,
			List<Polyline> lines, int i, double newY, double startX, double moveX,
			double moveY, double turtleY, double startY) {
		double endX1 = startX + moveX
				* Math.abs((Math.pow(-1, i) * (myYBounds[i] - newY + moveY)) / moveY);
		double endX2 = startX + moveX;
		System.out.println(Math.pow(-1, i) * myHalf[dir] - myYBounds[i] + newY);
		turtle.move(turtle.getTranslateX() + moveX, Math.pow(-1, i) * myHalf[dir]
				- myYBounds[i] + newY);
		
		System.out.println("turtle.getPenUp()" + turtle.getPenUp());
		
		if (!turtle.getPenUp()) {
			double endY = turtle.getTranslateY() + turtleY + turtle.getFitHeight() / 2;
			lines.add(drawLine(startX, startY, endX1, myYBounds[i]));
			lines.add(drawLine(endX1, myYBounds[1 - i], endX2, endY));
		}
	}

	private void wrapX(int dir, TurtleView turtle, Polar polar,
			List<Polyline> lines, int i, double newX, double startY, double moveY,
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
		polyline.setStrokeWidth(strokeSize);

		System.out.println(myColor);
		polyline.getPoints().addAll(new Double[] { startX, startY, endX, endY });
		return polyline;
	}


	public void changeColor(Color c) {
		myColor = c;
	}

}
