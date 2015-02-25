package model.instructions;


public class Constant extends Instruction{

	private double myValue;
	
	public Constant(String constantInput) {
		super(constantInput);
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