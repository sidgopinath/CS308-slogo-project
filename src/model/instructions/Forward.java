package model.instructions;

import model.Polar;

public class Forward extends Instruction {

	@Override
	protected double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return 1;
	}
	public String toString(){
		return this.getClass().toString();
	}
}
