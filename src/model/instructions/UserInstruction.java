package model.instructions;

import java.util.List;

// First instruction will be a listinstruction, second will be a listinstruction
public class UserInstruction extends Instruction {
	private int numArgs;
	public enum implementers {
		To;
	}
	List<Instruction> myDependencies;
	String instructionType;
	public UserInstruction(List<Instruction> dependencies, String inType) {
		myDependencies = dependencies;
		instructionType = inType;
	}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return numArgs;
	}

}
