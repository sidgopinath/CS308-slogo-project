package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 *  This class holds the elements of a list in its dependencies
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