package view;

import java.util.Map;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Workspace extends StackPane {

	//TOOD: check for hardcoded visual display values and make it so that they are dynamically alterable
	
	// private StackPane myDisplay;
	private Rectangle myBackground;


	// public static final double X_ADJUSTMENT = GRID_WIDTH / 2;
	// public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;
	public Workspace(Map<Integer, TurtleView> turtleList, Group lines, Dimension2D dimensions) {
		setPadding(new Insets(0, dimensions.getWidth()/85, dimensions.getWidth()/85, dimensions.getWidth()/85));

		// TODO: dynamically set grid size
		myBackground = new Rectangle(dimensions.getWidth()*11/16, dimensions.getHeight()*10/16);
		myBackground.setFill(Color.WHITE);
		TurtleView turtle = turtleList.get(0);
		turtleList.put(0, turtle);
		//setAlignment(Pos.CENTER);
		getChildren().addAll(myBackground, lines, turtle);

	}

	public void addTurtle(int id, TurtleView turtle) {
		// myTurtles.put(id, turtle);
		// add turtle into the workspace
	}

	public void setBackground(Color color) {
		myBackground.setFill(color);
	}

	public double getGridWidth() {
		return myBackground.getWidth();
	}

	public double getGridHeight() {
		return myBackground.getHeight();
	}
}
