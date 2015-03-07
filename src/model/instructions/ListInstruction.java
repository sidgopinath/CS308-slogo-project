package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;
import view.ViewUpdater;

public class ListInstruction extends Instruction {
	
	public ListInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	@Override
	/**
	 * Does nothing, since lists can't be executed
	 */
	public double execute() {
		return 0;
	}
	
	@Override
	public int getNumberOfArguments() {
		return 1;
	}
}