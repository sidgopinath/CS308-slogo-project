package model.turtle;
import model.Polar;

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