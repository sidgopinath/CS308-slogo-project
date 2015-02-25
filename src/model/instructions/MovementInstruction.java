package model.instructions;

import model.Polar;
import model.turtle.TurtleCommand;


public class MovementInstruction extends Instruction {
	private String[] myInput;
	private TurtleCommand myTurtle;
	private boolean myPenUp;
	private boolean myJump;
	private Polar myPolar;
	
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
		myPenUp = false;
		myJump = false;
	}
	
	@Override
	public double execute() {
		double inputDouble = Double.parseDouble(myInput[1]);
		switch(myInput[0].toUpperCase()){
		case "FORWARD":
			myPolar = new Polar(0, inputDouble);
			return inputDouble;
		case "BACKWARD":
			myPolar = new Polar(0, -inputDouble);
			return Double.parseDouble(myInput[1]);
		case "LEFT":
			myPolar = new Polar(-inputDouble, 0);
			return Double.parseDouble(myInput[1]);
		case "RIGHT":
			myPolar = new Polar(inputDouble, 0);
			return Double.parseDouble(myInput[1]);
		case "SETHEADING":
			// Need view
			myPolar = new Polar(inputDouble, 0);
			return 0.0;
		case "TOWARDS":
			// need view
			return 0.0;
		case "SETXY":
			//need view
			myJump = true;
			myPolar = new Polar(Double.parseDouble(myInput[1]), Double.parseDouble(myInput[2]));
			return 0.0;
		case "PENDOWN":
			myPenUp = false;
			return 1.0;
		case "PENUP":
			myPenUp = true;
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
