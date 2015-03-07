package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;
import view.ViewUpdater;

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
			return myViewUpdater.getXCor(myEnvironment.getActiveTurtle());
		case "YCOORDINATE":
			return myViewUpdater.getYCor(myEnvironment.getActiveTurtle());
		case "HEADING":
			return myViewUpdater.getHeading(myEnvironment.getActiveTurtle());
		case "ISPENDOWN":
			return myViewUpdater.getPenDown(myEnvironment.getActiveTurtle());
		case "ISSHOWING":
			return myViewUpdater.isShowing(myEnvironment.getActiveTurtle());
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}