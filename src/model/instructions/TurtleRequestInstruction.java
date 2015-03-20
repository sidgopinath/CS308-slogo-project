// This entire file is part of my masterpiece.
// Sid Gopinath

package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 * These instructions ask the view for information and return that information
 * It relies heavily on methods in the view
 * @author Greg, Sid
 *
 */

public class TurtleRequestInstruction extends Instruction {
	
	public TurtleRequestInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}
	
	public enum implementers {
		XCOORDINATE(0),
		YCOORDINATE(0),
		HEADING(0),
		ISPENDOWN(0),
		ISSHOWING(0);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
	}
	
	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "XCOORDINATE":
			return myViewUpdater.getXCor(myEnvironment.myActiveTurtle);
		case "YCOORDINATE":
			return myViewUpdater.getYCor(myEnvironment.myActiveTurtle);
		case "HEADING":
			return myViewUpdater.getHeading(myEnvironment.myActiveTurtle);
		case "ISPENDOWN":
			return myViewUpdater.getPenDown(myEnvironment.myActiveTurtle);
		case "ISSHOWING":
			return myViewUpdater.isShowing(myEnvironment.myActiveTurtle);
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}