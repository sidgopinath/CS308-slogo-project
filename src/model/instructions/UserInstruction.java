package model.instructions;

import java.util.List;

public class UserInstruction extends Instruction {

	private int numArgs;
	
	public UserInstruction(List<Instruction> dependencies, String instructionType) {
		super(dependencies, instructionType);
	}

	public enum implementers {
		To;
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