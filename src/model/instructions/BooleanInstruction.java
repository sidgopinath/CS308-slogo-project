package model.instructions;

public class BooleanInstruction extends Instruction {
	String [] myInput;
	// because of enums, need to trim the question mark
	public enum implementers {
			LESSTHAN (2),
			GREATERTHAN (2),
			EQUAL (2),
			NOTEQUAL (2),
			AND (2),
			OR (2),
			NOT (1);
	    private int numArgs;
		implementers(int args){
	    	this.numArgs=args;
	    }
	}
	public BooleanInstruction(String[] input) {
		super(input);
		myInput = input;
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
		switch(myInput[1].toUpperCase()){
		case "LESSTHAN":
			return boolToDouble(Double.parseDouble(myInput[1])<Double.parseDouble(myInput[2]));
		case "GREATERTHAN":
			return boolToDouble(Double.parseDouble(myInput[1])>Double.parseDouble(myInput[2]));
		case "EQUAL":
			return boolToDouble(Double.parseDouble(myInput[1])==Double.parseDouble(myInput[2]));
		case "NOTEQUAL":
			return boolToDouble(Double.parseDouble(myInput[1])!=Double.parseDouble(myInput[2]));
		case "AND":
			return boolToDouble(Double.parseDouble(myInput[1])==0&&Double.parseDouble(myInput[2])==0);
		case "OR":
			return boolToDouble(Double.parseDouble(myInput[1])!=0||Double.parseDouble(myInput[2])!=0);
		case "NOT":
			return boolToDouble(Double.parseDouble(myInput[1])==0);
		default:
			// need Error
			return -1;
		}
		
	}
	private double boolToDouble(Boolean input){
		if(input)
			return 1;
		return 0;
	}
	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInput[0].toUpperCase()).numArgs;
	}

}
