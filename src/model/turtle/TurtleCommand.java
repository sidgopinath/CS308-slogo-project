package model.turtle;
import model.Polar;

public class TurtleCommand {
    
 /*
  * Note to use final variables:
  * NOCHANGE = 0
  * CHANGE_TO_DOWN = 1
  * CHANGE_TO_UP = 2
  * 
  */

	private int myTurtleId;
    private Polar myPolar;
    private int myPenChange;
    private boolean isRelative;

    public TurtleCommand(int turtleId, Polar polar, int change, boolean relative){
    	myTurtleId=turtleId;
    	myPolar=polar;
        myPenChange = change;
        isRelative=relative;
    }
    
    public void setTurtleId(int turtleId) {
 		myTurtleId = turtleId;
 	}

 	public void setPolar(Polar polar) {
 		myPolar = polar;
 	}

 	public void setPenChange(int penChange) {
 		myPenChange = penChange;
 	}

	public int getPenChange() {
		return myPenChange;
	}
	
	public int getTurtleId() {
		return myTurtleId;
	}

	public Polar getPolar() {
		return myPolar;
	}

	public boolean isRelative() {
		return isRelative;
	}

   

}
