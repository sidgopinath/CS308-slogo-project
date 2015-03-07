package model.instructions;

import model.ExecutionEnvironment;

/**
 * This class holds a constant
 * When executed, it will just return the value of the constant
 * By making constant an instruction, it allows it to reside in the tree and be more accessible overall
 * @author Greg
 *
 */

public class Constant extends Instruction{

	private double myValue;
	
	public Constant(String constantInput, ExecutionEnvironment environment) {
		super(constantInput, environment);
		myValue = Double.parseDouble(constantInput);
	}

	@Override
	public double execute() {
		return myValue;
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}
}