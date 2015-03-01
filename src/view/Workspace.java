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
	// TODO: move myTUrtles to this class
	
	private TurtleView myActiveTurtle; 
	//

	// public static final double X_ADJUSTMENT = GRID_WIDTH / 2;
	// public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;
	
	//TODO: associate a Group of lines with the TurtleView object (maybe as a myDrawing variable, so that way it can be moved easily and associated with the correct turtle)
	public Workspace(Map<Integer, TurtleView> turtleList, Group lines, Dimension2D dimensions) {
	//	setPadding(new Insets(0, dimensions.getWidth()/85, dimensions.getWidth()/85, dimensions.getWidth()/85));
		myBackground = new Rectangle(dimensions.getWidth()*11.7/16, dimensions.getHeight()*10/16);
		myBackground.setFill(Color.WHITE);
		myTurtles = turtleList;
		
		myActiveTurtle = myTurtles.get(0);
		configureTurtleEventHandler(0); //TODO: this needs to be called every time you make a new one
		//TODO: the first turtle's id needs to be made into 1

		//TODO: make the turtles list here and have the view simply "get" it
		TurtleView turtle = turtleList.get(0);
		turtleList.put(0, turtle);
		//setAlignment(Pos.CENTER);
		getChildren().addAll(myBackground, lines, turtle);

	}

	public void addTurtle(){//int id, TurtleView turtle) {
		int newID = myTurtles.size();
		//We utilize a hashmap because if in the future turtles can be deleted, we do not want to have ID's that are reused/changed
		TurtleView newTurtle = new TurtleView(newID, new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(newID, newTurtle); //TODO: newId's are utilized twice. is that okay?
		configureTurtleEventHandler(newID);
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
	
	public void setActiveTurtle(int ID){
		myActiveTurtle = myTurtles.get(ID);
		System.out.println("newID" + ID);
	}
	
/*	//this may not be necessary
	private void configureTurtleEventHandlers(){
		for (Integer i: myTurtles.keySet()){
			TurtleView activeTurtle = myTurtles.get(i);
			activeTurtle.setOnMouseClicked(e -> setActiveTurtleID(activeTurtle.getID()));
		}
	}*/
	
	private void configureTurtleEventHandler(int ID){
		TurtleView selectedTurtle = myTurtles.get(ID);
		selectedTurtle.setOnMouseClicked(e -> setActiveTurtle(selectedTurtle.getID()));
	}
	
	public TurtleView getActiveTurtle(){
		return myActiveTurtle;
	}
}
