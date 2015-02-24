package model.instructions;

public class MathInstruction extends Instruction{
	// First input is command type, second is first argument, third is second argument
	// Division by Zero returns 0 always
	// need to fix so that it returns the right number of arguments needed to the command tree
	String[] myInput;
	public MathInstruction(String[] input) {
		super(input);
		myInput = input;
	}

	@Override
	public double execute() {
		switch(myInput[0].toUpperCase()){
		case "SUM":
			return Double.parseDouble(myInput[1])+Double.parseDouble(myInput[2]);
		case "DIFFERENCE":
			return Double.parseDouble(myInput[1])-Double.parseDouble(myInput[2]);
		case "PRODUCT":
			return Double.parseDouble(myInput[1])*Double.parseDouble(myInput[2]);
		case "QUOTIENT":
			return divByZeroCheck(Double.parseDouble(myInput[1])/Double.parseDouble(myInput[2]));
		case "REMAINDER":
			return Double.parseDouble(myInput[1])%Double.parseDouble(myInput[2]);
		case "MINUS":
			return -Double.parseDouble(myInput[1]);
		case "RANDOM":
			return Math.random()*Double.parseDouble(myInput[1]);
		case "SIN":
			return Math.toDegrees(Math.sin(Double.parseDouble(myInput[1])));
		case "COS":
			return Math.toDegrees(Math.cos(Double.parseDouble(myInput[1])));
		case "TAN":
			return Math.toDegrees(divByZeroCheck((Math.tan(Double.parseDouble(myInput[1])))));
		case "ATAN":
			return Math.toDegrees(divByZeroCheck((Math.atan(Double.parseDouble(myInput[1])))));
		case "LOG":
			return Math.log(Double.parseDouble(myInput[1]));
		case "POW":
			return Math.pow(Double.parseDouble(myInput[1]), Double.parseDouble(myInput[2]));
		case "PI":
			return Math.PI;
		default: 
			// view.displayException(new Exception());
			return 0;
		}
	}

	private double divByZeroCheck(double zeroCheck) {
		if(zeroCheck==Double.POSITIVE_INFINITY||zeroCheck==Double.NEGATIVE_INFINITY||zeroCheck==Double.NaN)
			return 0.0;
		else
			return zeroCheck;
	}

	@Override
	public int getNumberOfArguments(String match) {
		switch(myInput[0].toUpperCase()){
		case "SUM":
			return 2;
		case "DIFFERENCE":
			return 2;
		case "PRODUCT":
			return 2;
		case "QUOTIENT":
			return 2;
		case "REMAINDER":
			return 2;
		case "MINUS":
			return 1;
		case "RANDOM":
			return 1;
		case "SIN":
			return 1;
		case "COS":
			return 1;
		case "TAN":
			return 1;
		case "ATAN":
			return 1;
		case "LOG":
			return 1;
		case "POW":
			return 2;
		case "PI":
			return 0;
		default: 
			// view.displayException(new Exception());
			return 0;
		}
	}

}
