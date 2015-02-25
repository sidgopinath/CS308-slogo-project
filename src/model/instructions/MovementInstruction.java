package model.instructions;

import java.util.List;

import model.Polar;
import model.turtle.TurtleCommand;


public class MovementInstruction extends Instruction {
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
	List<Instruction> myDependencies;
	String instructionType;
	
	public MovementInstruction(List<Instruction> dependencies, String inType) {
		myDependencies = dependencies;
		instructionType = inType;
		myTurtle = null;
		myPenUp = false;
		myJump = false;
	}
	
	@Override
	public double execute() {
		Instruction arg1 = myDependencies.get(0);
		Instruction arg2 = myDependencies.get(1);
		double inputDouble;
		switch(instructionType.toUpperCase()){
		case "FORWARD":
			inputDouble = arg1.execute();
			myPolar = new Polar(0, inputDouble);
			return inputDouble;
		case "BACKWARD":
			inputDouble = arg1.execute();
			myPolar = new Polar(0, -inputDouble);
			return arg1.execute();
		case "LEFT":
			inputDouble = arg1.execute();
			myPolar = new Polar(-inputDouble, 0);
			return arg1.execute();
		case "RIGHT":
			inputDouble = arg1.execute();
			myPolar = new Polar(inputDouble, 0);
			return arg1.execute();
		case "SETHEADING":
			// Need view
			inputDouble = arg1.execute();
			myPolar = new Polar(inputDouble, 0);
			return 0.0;
		case "TOWARDS":
			// need view
			return 0.0;
		case "SETXY":
			//need view
			myJump = true;
			myPolar = new Polar(arg1.execute(), arg2.execute());
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
		return implementers.valueOf(instructionType.toUpperCase()).numArgs;
	}

	@Override
	public TurtleCommand getTurtleCommand() {
		myTurtle = new TurtleCommand(0, myPolar, myPenUp, myJump);
		return myTurtle;
	}
}
