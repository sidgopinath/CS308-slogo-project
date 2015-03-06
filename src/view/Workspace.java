package view;

import java.util.Map;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Workspace extends StackPane {

	// TOOD: check for hardcoded visual display values and make it so that they
	// are dynamically fit

	// private StackPane myDisplay;
	private Rectangle myBackground;
	private Map<Integer, TurtleView> myTurtles;
	private SideBar mySidebar;
	// TODO: move myTUrtles to this class

	private TurtleView myActiveTurtle;
	
	//TODO: set to 1
	private static final int INITIAL_TURTLE_ID = 0;

	//

	// public static final double X_ADJUSTMENT = GRID_WIDTH / 2;
	// public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;

	// TODO: associate a Group of lines with the TurtleView object (maybe as a
	// myDrawing variable, so that way it can be moved easily and associated
	// with the correct turtle)
	public Workspace(Map<Integer, TurtleView> turtleList, Group lines,
			Dimension2D dimensions, SideBar sidebar) {
		// setPadding(new Insets(0, dimensions.getWidth()/85,
		// dimensions.getWidth()/85, dimensions.getWidth()/85));
		myBackground = new Rectangle(dimensions.getWidth() * 11.7 / 16,
				dimensions.getHeight() * 10 / 16);
		myBackground.setFill(Color.WHITE);
		myTurtles = turtleList;
		mySidebar = sidebar;

		getChildren().addAll(myBackground, lines);
	//change both values within the method from 0 to 1
		addTurtle();
		setActiveTurtle(INITIAL_TURTLE_ID);

	}

	public void addTurtle() {// int id, TurtleView turtle) {
		TurtleView newTurtle;
		System.out.println(myTurtles.size());
		int newID = myTurtles.size(); //TODO:+1;
		System.out.println("newid" + newID);
		// We utilize a hashmap because if in the future turtles can be deleted,
		// we do not want to have ID's that are reused/changed
		 newTurtle = new TurtleView(newID,
				new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(newID, newTurtle); // TODO: newId's are utilized twice. is
											// that okay?
		System.out.println(myTurtles);
		configureTurtleEventHandler(newID);
		getChildren().add(newTurtle);
		System.out.println("where is the turtle image");
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

	public void setActiveTurtle(int ID) { // could stick this into slogoview so
											// that we do not need to pass a
											// sidebar. but that seems less
											// relevant :(
		myActiveTurtle = myTurtles.get(ID);
		mySidebar.updateTurtleProperties(ID, this);
		System.out.println("newID" + ID);
	}

	/*
	 * //this may not be necessary private void configureTurtleEventHandlers(){
	 * for (Integer i: myTurtles.keySet()){ TurtleView activeTurtle =
	 * myTurtles.get(i); activeTurtle.setOnMouseClicked(e ->
	 * setActiveTurtleID(activeTurtle.getID())); } }
	 */

	private void configureTurtleEventHandler(int ID) {
		TurtleView selectedTurtle = myTurtles.get(ID);
		selectedTurtle.setOnMouseClicked(e -> setActiveTurtle(selectedTurtle.getID()));
	}

	public TurtleView getActiveTurtle() {
		return myActiveTurtle;
	}
	
	public Map<Integer, TurtleView> getTurtleMap(){
		return myTurtles;
	}
}
