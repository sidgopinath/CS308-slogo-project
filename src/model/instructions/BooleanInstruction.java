package model.instructions;

import java.util.List;


public class BooleanInstruction extends Instruction {

	public BooleanInstruction(List<Instruction> dependencies, String instructionType) {
		super(dependencies, instructionType);
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
		double firstDependency = myDependencies.get(0).execute();
		double secondDependency = myDependencies.get(1).execute();
		switch(myInstructionType.toUpperCase()){
		case "LESSTHAN":
			return boolToDouble(firstDependency<secondDependency);
		case "GREATERTHAN":
			return boolToDouble(firstDependency>secondDependency);
		case "EQUAL":
			return boolToDouble(firstDependency==secondDependency);
		case "NOTEQUAL":
			return boolToDouble(firstDependency!=secondDependency);
		case "AND":
			return boolToDouble(firstDependency==0 && secondDependency==0);
		case "OR":
			return boolToDouble(firstDependency!=0 || secondDependency!=0);
		case "NOT":
			return boolToDouble(firstDependency==0);
		default:
			//TODO: need Error
			return -1;
		}
	}
	
	private double boolToDouble(Boolean input){
		if(input){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}