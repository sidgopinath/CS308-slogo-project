package model.instructions;

import model.ExecutionEnvironment;


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