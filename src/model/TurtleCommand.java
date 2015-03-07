package model;

/**
 * This very important class holds information about Turtles
 * The getters and setters in this class are called across the program
 * This class allows the model and the view to communicate
 * The model places information into a TurtleCommand and then sends it to the View
 * The view can read the information in TurtleCommands and move the Turtle accordingly
 * This class is primarily used for commands that physically move the Turtle
 * @authors Greg, Sid, Mengchao
 *
 */

public class TurtleCommand {
    
	private int myTurtleId;
    private Polar myPolar;
    private boolean myIsRelative;

    public TurtleCommand(int turtleId, Polar polar, boolean relative){
    	myTurtleId=turtleId;
    	myPolar=polar;
        myIsRelative=relative;
    }
    
	public void setTurtleId(int turtleId) {
 		myTurtleId = turtleId;
 	}

 	public void setPolar(Polar polar) {
 		myPolar = polar;
 	}
	
	public int getTurtleId() {
		return myTurtleId;
	}

	public Polar getPolar() {
		return myPolar;
	}

	public boolean isRelative() {
		return myIsRelative;
	}
}