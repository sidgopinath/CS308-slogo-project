package model.instructions;

public class Constant extends Instruction{
	double myValue;
	public Constant(double input){
		myValue = input;
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
