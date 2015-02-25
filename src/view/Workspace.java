package view;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resources.Strings;

public class Workspace extends StackPane{
	
	//private StackPane myDisplay;
	private Rectangle myBackground;
    private Group myLines = new Group();
	private Map<Integer, TurtleView> myTurtles = new HashMap<Integer,TurtleView>();


	public static final int GRID_WIDTH = 800;
	public static final int GRID_HEIGHT = 550;
	//adjusts anchorpane coordinates to set 0,0 as the center of the gridsets center point at the
	public static final double X_ADJUSTMENT = GRID_WIDTH / 2;  
	public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;  
	
	
	public Workspace(){
	     setPadding(new Insets(15));
	     setAlignment(Pos.CENTER);
	     
	     //is there a way to dynamically set grid size
	     myBackground = new Rectangle(GRID_WIDTH,GRID_HEIGHT);
	     myBackground.setFill(Color.WHITE);
	     	     
	     TurtleView turtle = new TurtleView(new Image(Strings.DEFAULT_TURTLE_IMG));
	     myLines.getChildren().add(turtle);
	     myTurtles.put(0,turtle);
	     
	     getChildren().addAll(myBackground,myLines);
	     
	}
	
	public Map<Integer, TurtleView> getTurtles(){
		return myTurtles;
	}
	
	public void addTurtle(int id, TurtleView turtle){
		myTurtles.put(id, turtle);
	}
	
	public void setBackground(Color color){
		myBackground.setFill(color);
	}

}
