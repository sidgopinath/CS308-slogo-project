package view;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import model.ExecutionEnvironment;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Workspace for displaying turtles, lines, and the pane that displays the visual representation of the SLogo language.
 * @author Callie
 *
 */

public class Workspace extends StackPane {

	private Rectangle myBackground;
	private Map<Integer, TurtleView> myTurtles;
	private SideBar mySidebar;
	private Group myLines;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private TurtleView myActiveTurtle;
	private static final int INITIAL_TURTLE_ID = 1;
	
	public Workspace(
			Dimension2D dimensions, SideBar sidebar) {

		myBackground = new Rectangle(dimensions.getWidth() * 11.7 / 16,
				dimensions.getHeight() * 10 / 16);
		myBackground.setFill(Color.WHITE);
    	myTurtles = new HashMap<Integer, TurtleView>(); 
		mySidebar = sidebar;
		myLines = new Group();
		myLines.setManaged(false);

		getChildren().addAll(myBackground, myLines);
	//TODO: change both values within the method from 0 to 1
		
		//create initial turtle (id = 1)
		addTurtle(null);
		setActiveTurtle(INITIAL_TURTLE_ID);

	}

	public void addTurtle(ExecutionEnvironment update) {// int id, TurtleView turtle) {
		TurtleView newTurtle;
		int newID = myTurtles.size()+1;
		// We utilize a hashmap because if in the future turtles can be deleted,
		// we do not want to have ID's that are reused/changed
		 newTurtle = new TurtleView(newID,
				new Image(myResources.getString("DefaultTurtleImage")));
		myTurtles.put(newID, newTurtle);
		configureTurtleEventHandler(newID);
		getChildren().add(newTurtle);
		if(update!=null){
			update.addTurtle(newID);
			update.setActiveTurtle(newID);
		}
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

	public void setActiveTurtle(int ID) {
		myActiveTurtle = myTurtles.get(ID);
		mySidebar.updateTurtleProperties(ID, this);
	}

	private void configureTurtleEventHandler(int ID) {
		TurtleView selectedTurtle = myTurtles.get(ID);
		selectedTurtle.setOnMouseClicked(e -> setActiveTurtle(selectedTurtle.getID()));
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

	public TurtleView getActiveTurtle() {
		return myActiveTurtle;
	}

}

