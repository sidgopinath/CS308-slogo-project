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
		switch(myType.toUpperCase()){
		case "LESSTHAN":
			return boolToDouble(myDependencies.get(0).execute()<myDependencies.get(1).execute());
		case "GREATERTHAN":
			return boolToDouble(myDependencies.get(0).execute()>myDependencies.get(1).execute());
		case "EQUAL":
			return boolToDouble(myDependencies.get(0).execute()==myDependencies.get(1).execute());
		case "NOTEQUAL":
			return boolToDouble(myDependencies.get(0).execute()!=myDependencies.get(1).execute());
		case "AND":
			return boolToDouble(myDependencies.get(0).execute()==0&&myDependencies.get(1).execute()==0);
		case "OR":
			return boolToDouble(myDependencies.get(0).execute()!=0||myDependencies.get(1).execute()!=0);
		case "NOT":
			return boolToDouble(myDependencies.get(0).execute()==0);
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
