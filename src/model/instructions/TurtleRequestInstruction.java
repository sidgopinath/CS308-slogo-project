package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;

public class TurtleRequestInstruction extends Instruction {
	
	public TurtleRequestInstruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
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
			return myView.getXCor(myEnvironment.getActiveTurtle());
		case "YCOORDINATE":
			return myView.getYCor(myEnvironment.getActiveTurtle());
		case "HEADING":
			return myView.getHeading(myEnvironment.getActiveTurtle());
		case "ISPENDOWN":
			return myView.getPenDown(myEnvironment.getActiveTurtle());
		case "ISSHOWING":
			return myView.isShowing(myEnvironment.getActiveTurtle());
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}