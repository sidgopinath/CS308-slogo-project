package model.instructions;

import java.util.ArrayList;
import java.util.List;

import view.SLogoView;
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
	
	public MovementInstruction(List<Instruction> dependencies, String instructionType, SLogoView view) {
		super(dependencies, instructionType, view);
		myDependencies = dependencies;
		myInstructionType = instructionType;
		myTurtle = null;
		myPenUp = false;
		myJump = false;
	}
	
	@Override
	public double execute() {
		double inputDouble = myDependencies.get(0).execute();
		switch(myInstructionType.toUpperCase()){
		case "FORWARD":
			myPolar = new Polar(0, inputDouble);
			updateView();
			return inputDouble;
		case "BACKWARD":
			myPolar = new Polar(0, -inputDouble);
			updateView();
			return myDependencies.get(0).execute();
		case "LEFT":
			myPolar = new Polar(-inputDouble, 0);
			updateView();
			return myDependencies.get(0).execute();
		case "RIGHT":
			myPolar = new Polar(inputDouble, 0);
			updateView();
			return myDependencies.get(0).execute();
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
			myPolar = new Polar(myDependencies.get(0).execute(), myDependencies.get(1).execute());
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

	private void updateView() {
		List<TurtleCommand> commandList = new ArrayList<TurtleCommand>();
		commandList.add(new TurtleCommand(0, myPolar, myJump));
		myView.updateWorkspace(commandList);
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}

	@Override
	public TurtleCommand getTurtleCommand() {
		myTurtle = new TurtleCommand(0, myPolar, myJump);
		return myTurtle;
	}
	
	//somewhere add something to setpenup in the view
}