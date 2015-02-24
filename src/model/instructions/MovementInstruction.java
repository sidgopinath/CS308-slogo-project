package model.instructions;


public class MovementInstruction extends Instruction {
	String[] myInput;
	public MovementInstruction(String[] input) {
		super(input);
		myInput = input;
	}

	@Override
	public double execute() {
		switch(myInput[0].toUpperCase()){
		case "FORWARD":
			return Double.parseDouble(myInput[1]);
		case "BACKWARD":
			return Double.parseDouble(myInput[1]);
		case "LEFT":
			return Double.parseDouble(myInput[1]);
		case "RIGHT":
			return Double.parseDouble(myInput[1]);
		case "SETHEADING":
			// Need view
			return 0.0;
		case "TOWARDS":
			// need view
			return 0.0;
		case "SETXY":
			//need view
			return 0.0;
		case "PENDOWN":
			return 1.0;
		case "PENUP":
			return 0.0;
		case "SHOWTURTLE":
			return 1.0;
		case "HIDETURTLE":
			return 0.0;
		case "CLEARSCREEN":
			//need view
			return 0.0;
		case "HOME":
			//need view
			return 0.0;
		default: 
			// view.displayException(new Exception());
			return 0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		switch(myInput[0].toUpperCase()){
		case "FORWARD":
			return 1;
		case "BACKWARD":
			return 1;
		case "LEFT":
			return 1;
		case "RIGHT":
			return 1;
		case "SETHEADING":
			return 1;
		case "TOWARDS":
			return 2;
		case "SETXY":
			return 2;
		case "PENDOWN":
			return 0;
		case "PENUP":
			return 0;
		case "SHOWTURTLE":
			return 0;
		case "HIDETURTLE":
			return 0;
		case "CLEARSCREEN":
			return 0;
		case "HOME":
			return 0;
		default: 
			// view.displayException(new Exception());
			return 0;
		}
	}
}
