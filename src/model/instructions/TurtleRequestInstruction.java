package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;


public class TurtleRequestInstruction extends Instruction {
	
	public TurtleRequestInstruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
	}
	// TODO call view for these fields



	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "XCOR":
			return 0.0;
		case "YCOR":
			return 0.0;
		case "HEADING":
			return myView.getHeading();
		case "PENDOWN":
			return myView.getPenDown();
		case "SHOWING":
			return myView.isShowing();
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}
}