package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 * This method does nothing, since lists cannot be executed
 * It just holds onto them as a ListInstruction object
 */


public class ListInstruction extends Instruction {
	
	public ListInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	@Override
	public double execute() {
		return 0;
	}
	
	@Override
	public int getNumberOfArguments() {
		return 1;
	}
}