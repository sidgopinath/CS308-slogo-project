package model.turtle;
import model.Polar;

public class TurtleCommand {
    

	private int myTurtleId;
    private Polar myPolar;
  //  private int myPenChange; //we utilize 0,1, and -1 since back-end does not store the current penUp of the turtle. The model instead will just alert us if the user inputs a change to the pen 
    private boolean isRelative;

    public TurtleCommand(int turtleId, Polar polar, boolean relative){
    	myTurtleId=turtleId;
    	myPolar=polar;
       // myPenChange = penChange;
        isRelative=relative;
    }
    
	public void setTurtleId(int turtleId) {
 		myTurtleId = turtleId;
 	}

 	public void setPolar(Polar polar) {
 		myPolar = polar;
 	}

 	/*public void setPenChange(int penChange) {
 		myPenChange = penChange;
 	}

	public int getPenChange() {
		return myPenChange;
	}*/
	
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
