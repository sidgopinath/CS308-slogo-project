package view;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Workspace extends StackPane {

	// private StackPane myDisplay;
	private Rectangle myBackground;

	public static final double GRID_WIDTH = 800;
	public static final double GRID_HEIGHT = 550;

	// public static final double X_ADJUSTMENT = GRID_WIDTH / 2;
	// public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;
	public Workspace(Map<Integer, TurtleView> turtleList, Group lines) {
		setPadding(new Insets(15));

		// TODO: dynamically set grid size
		myBackground = new Rectangle(GRID_WIDTH, GRID_HEIGHT);
		myBackground.setFill(Color.WHITE);
		TurtleView turtle = turtleList.get(0);
		turtleList.put(0, turtle);
		setAlignment(Pos.CENTER);
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
