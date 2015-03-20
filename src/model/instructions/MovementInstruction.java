// This entire file is part of my masterpiece.
// Sid Gopinath

package model.instructions;

import java.util.ArrayList;
import java.util.List;

import model.ExecutionEnvironment;
import model.PolarCoordinate;
import model.TurtleCommand;
import view.ViewUpdater;

/**
 * Movement instructions for the turtle are stored here
 * These methods usually call the updateView method
 * The turtle is directly affected by these methods
 * Observable comes into play also here
 * @author Greg, Sid, Callie
 *
 */

public class MovementInstruction extends Instruction {
	private TurtleCommand myTurtle;
	private boolean myJump;
	private PolarCoordinate myPolar;
	private double myReturnVal;
	
	public enum implementers {
		FORWARD(1),
		BACKWARD(1),
		LEFT(1),
		RIGHT(1),
		SETHEADING(1),
		SETTOWARDS(2),
		SETPOSITION(2),
		PENDOWN(0),
		PENUP(0),
		SHOWTURTLE(0),
		HIDETURTLE(0),
		CLEARSCREEN(0),
		HOME(0);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
	}
	
	public MovementInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
		myDependencies = dependencies;
		myInstructionType = instructionType;
		myTurtle = null;
		myJump = true;
	}
	
	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "FORWARD":
			myReturnVal = myDependencies.get(0).execute();
			myPolar = new PolarCoordinate(0, myReturnVal);
			updateView();
			return myReturnVal;
		case "BACKWARD":
			myReturnVal = myDependencies.get(0).execute();
			myPolar = new PolarCoordinate(0, -myReturnVal);
			updateView();
			return myDependencies.get(0).execute();
		case "LEFT":
			myReturnVal = myDependencies.get(0).execute();
			myPolar = new PolarCoordinate(-myReturnVal, 0);
			updateView();
			return myDependencies.get(0).execute();
		case "RIGHT":
			myReturnVal = myDependencies.get(0).execute();
			myPolar = new PolarCoordinate(myReturnVal, 0);
			updateView();
			return myReturnVal;
		case "SETHEADING":
			myJump = false;
			myPolar = new PolarCoordinate(myDependencies.get(0).execute(), 0);
			updateView();
			return 0.0;
		case "SETTOWARDS":
			return myViewUpdater.towards(myEnvironment.myActiveTurtle, myDependencies.get(0).execute(), myDependencies.get(1).execute());
		case "SETPOSITION":
			myJump = true;
			return myViewUpdater.setXY(0, myDependencies.get(0).execute(), myDependencies.get(1).execute());
		case "PENDOWN":
			myViewUpdater.setPenUp(myEnvironment.myActiveTurtle, false);
			return 1.0;
		case "PENUP":
			myViewUpdater.setPenUp(myEnvironment.myActiveTurtle, true);
			return 0.0;
		case "SHOWTURTLE":
			myViewUpdater.showTurtle(myEnvironment.myActiveTurtle, true);
			return 1.0;
		case "HIDETURTLE":
			myViewUpdater.showTurtle(myEnvironment.myActiveTurtle, false);
			return 0.0;
		case "CLEARSCREEN":
			return myViewUpdater.clearScreen(myEnvironment.myActiveTurtle);
		case "HOME":
			myJump = true;
			return myViewUpdater.setXY(myEnvironment.myActiveTurtle,0,0);
		default: 
			return 0;
		}
	}

	private void updateView() {
		List<TurtleCommand> commandList = new ArrayList<TurtleCommand>();
		commandList.add(new TurtleCommand(myEnvironment.myActiveTurtle, myPolar, myJump));
		myViewUpdater.updateWorkspace(commandList);
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
}