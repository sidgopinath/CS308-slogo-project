package model.instructions;

public class BooleanInstruction extends Instruction {
	String [] myInput;
	public BooleanInstruction(String[] input) {
		super(input);
		myInput = input;
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
		switch(myInput[1].toUpperCase()){
		case "LESS?":
			return boolToDouble(Double.parseDouble(myInput[1])<Double.parseDouble(myInput[2]));
		case "GREATER?":
			return boolToDouble(Double.parseDouble(myInput[1])>Double.parseDouble(myInput[2]));
		case "EQUAL?":
			return boolToDouble(Double.parseDouble(myInput[1])==Double.parseDouble(myInput[2]));
		case "NOTEQUAL?":
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
		// TODO Auto-generated method stub
		if(myInput[1]=="Not")
			return 1;
		return 2;
	}

}
