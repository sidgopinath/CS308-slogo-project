package model.instructions;

import java.util.List;

import view.SLogoView;

public class UserInstruction extends Instruction {

	private int numArgs;
	
	public UserInstruction(List<Instruction> dependencies, String instructionType, SLogoView view) {
		super(dependencies, instructionType, view);
	}
	
	public enum implementers {
		To;
	}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return numArgs;
	}
}