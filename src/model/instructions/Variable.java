package model.instructions;

import model.ExecutionEnvironment;

public class Variable extends Instruction{
	
	public Variable(String instructionType, ExecutionEnvironment environment) {
		super(instructionType, environment);
	}

	@Override
	public double execute(){
		return myEnvironment.getVariable(myInstructionType);
	}
	@Override
	public int getNumberOfArguments() {
		return 0;
	}
}
