package model.instructions;

import model.Polar;
import model.turtle.TurtleCommand;


public class MovementInstruction extends Instruction {
	String[] myInput;
	TurtleCommand myTurtle;
	private boolean myPenUp;
	private boolean myJump;
	private Polar myPolar;
	//private int myTurtleId;
	
	public enum implementers {
		FORWARD(1),
		BACKWARD(1),
		LEFT(1),
		RIGHT(1),
		SETHEADING(1),
		TOWARDS(2),
		SETXY(2),
		PENDOWN(0),
		PENUP(0),
		SHOWTURTLE(0),
		HIDETURTLE(0),
		NATURALLOG(1),
		CLEARSCREEN(2),
		HOME(0);
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
	}
	public MovementInstruction(String[] input) {
		myInput = input;
		myTurtle = null;
	}
	
	@Override
	public double execute() {
		switch(myInput[0].toUpperCase()){
		case "FORWARD":
			double inputDouble = Double.parseDouble(myInput[1]);
			myPolar = new Polar(0, inputDouble);
			myPenUp = false;
			myJump = false;
			return inputDouble;
		case "BACKWARD":
			return Double.parseDouble(myInput[1]);
		case "LEFT":
			return Double.parseDouble(myInput[1]);
		case "RIGHT":
			return Double.parseDouble(myInput[1]);
		case "SETHEADING":
			// Need view
			return 0.0;
		case "TOWARDS":
			// need view
			return 0.0;
		case "SETXY":
			//need view
			return 0.0;
		case "PENDOWN":
			return 1.0;
		case "PENUP":
			return 0.0;
		case "SHOWTURTLE":
			//need view
			return 1.0;
		case "HIDETURTLE":
			//need view
			return 0.0;
		case "CLEARSCREEN":
			//need view
			return 0.0;
		case "HOME":
			//need view
			return 0.0;
		default: 
			// view.displayException(new Exception());
			return 0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInput[0].toUpperCase()).numArgs;
	}

	@Override
	public TurtleCommand getTurtleCommand() {
		myTurtle = new TurtleCommand(0, myPolar, myPenUp, myJump);
		return myTurtle;
	}
}
