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
		case "Less?":
			return boolToDouble(Double.parseDouble(myInput[1])<Double.parseDouble(myInput[2]));
		case "Greater?":
			return boolToDouble(Double.parseDouble(myInput[1])>Double.parseDouble(myInput[2]));
		case "Equal?":
			return boolToDouble(Double.parseDouble(myInput[1])==Double.parseDouble(myInput[2]));
		case "NotEqual?":
			return boolToDouble(Double.parseDouble(myInput[1])!=Double.parseDouble(myInput[2]));
		case "And":
			return boolToDouble(Double.parseDouble(myInput[1])==0&&Double.parseDouble(myInput[2])==0);
		case "Or":
			return boolToDouble(Double.parseDouble(myInput[1])!=0||Double.parseDouble(myInput[2])!=0);
		case "Not":
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
