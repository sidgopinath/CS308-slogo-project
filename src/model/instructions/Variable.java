package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;

public class Variable extends Instruction{
	public Variable(String myName) {
		super(myName);
	}

	@Override
	public double execute() {
		return myEnvironment.getVariable(myInstructionType).execute();
	}
	@Override
	public int getNumberOfArguments() {
		return 0;
	}
	

}
