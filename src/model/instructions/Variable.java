package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;

public class Variable extends Instruction{
	public Variable(List<Instruction> dependencies, String instructionType,
			SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
	}

	@Override
	public double execute() {
		return myEnvironment.getVariable(myInstructionType).execute();
	}

	@Override
	public int getNumberOfArguments() {
		return 1;
	}

}
