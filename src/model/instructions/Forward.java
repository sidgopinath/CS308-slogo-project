package model.instructions;

import model.Polar;

public class Forward extends Instruction {

	public Forward(String[] input) {
		super(input);
		System.out.println(input[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
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
