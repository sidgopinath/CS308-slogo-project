package model.instructions;


public class TurtleRequestInstruction extends Instruction {
	// TODO call view for these fields
	private String[] myInput;

	public TurtleRequestInstruction(String[] input) {
		myInput = input;
	}

	@Override
	public double execute() {
		switch(myInput[0].toUpperCase()){
		case "XCOR":
			return 0;
		case "YCOR":
			return 0;
		case "HEADING":
			return 0;
		case "PENDOWN":
			return 0;
		case "SHOWING":
			return 0;
		default: 
			return 0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}

}
