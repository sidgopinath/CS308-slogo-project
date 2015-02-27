package view;

import java.util.Map;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Workspace extends StackPane {

	//TOOD: check for hardcoded visual display values and make it so that they are dynamically fit
	
	// private StackPane myDisplay;
	private Rectangle myBackground;
	private Map<Integer, TurtleView> myTurtles;


	// public static final double X_ADJUSTMENT = GRID_WIDTH / 2;
	// public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;
	
	//TODO: associate a Group of lines with the TurtleView object (maybe as a myDrawing variable, so that way it can be moved easily and associated with the correct turtle)
	public Workspace(Map<Integer, TurtleView> turtleList, Group lines, Dimension2D dimensions) {
		setPadding(new Insets(0, dimensions.getWidth()/85, dimensions.getWidth()/85, dimensions.getWidth()/85));

		// TODO: dynamically set grid size
		myBackground = new Rectangle(dimensions.getWidth()*11/16, dimensions.getHeight()*10/16);
		myBackground.setFill(Color.WHITE);
		myTurtles = turtleList;
		TurtleView turtle = turtleList.get(0);
		turtleList.put(0, turtle);
		//setAlignment(Pos.CENTER);
		getChildren().addAll(myBackground, lines, turtle);

	}

	public void addTurtle(){//int id, TurtleView turtle) {
		int newId = myTurtles.size();
		//We utilize a hashmap because if in the future turtles can be deleted, we do not want to have ID's that are reused/changed
		TurtleView newTurtle = new TurtleView(newId, new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(newId, newTurtle); //TODO: newId's are utilized twice. is that okay?
		getChildren().add(newTurtle);
		
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
