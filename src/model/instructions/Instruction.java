package model.instructions;

public abstract class Instruction {

	protected abstract double execute();
	public abstract int getNumberOfArguments();
}
