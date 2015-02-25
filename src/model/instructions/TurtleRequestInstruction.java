package model.instructions;

import java.util.List;

import view.SLogoView;


public class TurtleRequestInstruction extends Instruction {
	
	public TurtleRequestInstruction(List<Instruction> dependencies, String instructionType, SLogoView view) {
		super(dependencies, instructionType, view);
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
			return 0.0;
		case "PENDOWN":
			return 0.0;
		case "SHOWING":
			return 0.0;
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}
}