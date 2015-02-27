package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import model.ModelException;
import view.SLogoView;

public class StringInstruction extends Instruction{

	public StringInstruction(String instructionType,ExecutionEnvironment environment) {
		super(instructionType, environment);
	}

	@Override
	public double execute(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}

}
