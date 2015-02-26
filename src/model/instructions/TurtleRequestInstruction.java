package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;


public class TurtleRequestInstruction extends Instruction {
	
	public TurtleRequestInstruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
	}
	
	public enum implementers {
		XCOR(0),
		YCOR(0),
		HEADING(0),
		PENDOWN(0),
		SHOWING(0);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
	}
	
	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "XCOR":
			return 0.0;
		case "YCOR":
			return 0.0;
		case "HEADING":
			return myView.getHeading(0);
		case "PENDOWN":
			return myView.getPenDown(0);
		case "SHOWING":
			return myView.isShowing(0);
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}