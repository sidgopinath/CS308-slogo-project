package view;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Workspace extends StackPane {

	private Rectangle myBackground;
	private Map<Integer, TurtleView> myTurtles;
	private SideBar mySidebar;
	private Group myLines;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private TurtleView myActiveTurtle;
	//TODO: set initial id to 1
	private static final int INITIAL_TURTLE_ID = 0;
	
	public Workspace(
			Dimension2D dimensions, SideBar sidebar) {

		myBackground = new Rectangle(dimensions.getWidth() * 11.7 / 16,
				dimensions.getHeight() * 10 / 16);
		myBackground.setFill(Color.WHITE);
    	myTurtles = new HashMap<Integer, TurtleView>(); //TODO: move
		mySidebar = sidebar;
		myLines = new Group();
		myLines.setManaged(false);

		
		
	
		getChildren().addAll(myBackground, myLines);
	//TODO: change both values within the method from 0 to 1
		
		//create initial turtle (id = 1)
		addTurtle();
		setActiveTurtle(INITIAL_TURTLE_ID);

	}

	public void addTurtle() {// int id, TurtleView turtle) {
		TurtleView newTurtle;
		int newID = myTurtles.size();//+1;
		// We utilize a hashmap because if in the future turtles can be deleted,
		// we do not want to have ID's that are reused/changed
		 newTurtle = new TurtleView(newID,
				new Image(myResources.getString("DefaultTurtleImage")));
		myTurtles.put(newID, newTurtle);
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

	public void setActiveTurtle(int ID) { // could stick this into slogoview so
											// that we do not need to pass a
											// sidebar. but that seems less
											// relevant :(
		myActiveTurtle = myTurtles.get(ID);
		mySidebar.updateTurtleProperties(ID, this);
		System.out.println("newID" + ID);
	}

	private void configureTurtleEventHandler(int ID) {
		TurtleView selectedTurtle = myTurtles.get(ID);
		selectedTurtle.setOnMouseClicked(e -> setActiveTurtle(selectedTurtle.getID()));
	}

	//TODO
	public TurtleView getActiveTurtle() {
		return myActiveTurtle;
	}
	
	public Map<Integer, TurtleView> getTurtleMap(){
		return myTurtles;
	}
	
	public Group getLines(){
		return myLines;
	}
	
	public void clearLines(){
		myLines.getChildren().clear();
	}
}
