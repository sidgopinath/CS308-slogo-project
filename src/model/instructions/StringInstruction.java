package model.instructions;

import model.ExecutionEnvironment;

public class StringInstruction extends Instruction{

	public StringInstruction(String instructionType,ExecutionEnvironment environment) {
		super(instructionType, environment);
	}

	@Override
	public double execute(){
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}
}
