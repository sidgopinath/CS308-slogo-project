package model.instructions;

import java.util.List;


public class BooleanInstruction extends Instruction {
	List<Instruction> myDependencies;
	String myType;
	public BooleanInstruction(List<Instruction> dependencies, String myInstructionType) {
		myDependencies = dependencies;
		myType = myInstructionType;
	}
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
	@Override
	public double execute() {
		Instruction arg1 = myDependencies.get(0);
		Instruction arg2 = myDependencies.get(1);
		switch(myType.toUpperCase()){
		case "LESSTHAN":
			return boolToDouble(arg1.execute()<arg2.execute());
		case "GREATERTHAN":
			return boolToDouble(arg1.execute()>arg2.execute());
		case "EQUAL":
			return boolToDouble(arg1.execute()==arg2.execute());
		case "NOTEQUAL":
			return boolToDouble(arg1.execute()!=arg2.execute());
		case "AND":
			return boolToDouble(arg1.execute()==0&&arg2.execute()==0);
		case "OR":
			return boolToDouble(arg1.execute()!=0||arg2.execute()!=0);
		case "NOT":
			return boolToDouble(arg1.execute()==0);
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
		return implementers.valueOf(myType.toUpperCase()).numArgs;
	}

}
