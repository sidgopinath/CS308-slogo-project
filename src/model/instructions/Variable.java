package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;

public class Variable extends Instruction{
	public Variable(List<Instruction> dependencies, String instructionType,
			SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
		myEnvironment.addVariable(myInstructionType, this);
	}

	@Override
	public double execute() {
		return myDependencies.get(0).execute();
	}

	@Override
	public int getNumberOfArguments() {
		return 1;
	}

}
