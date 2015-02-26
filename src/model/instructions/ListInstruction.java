package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;

public class ListInstruction extends Instruction {
	

	public ListInstruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
	}

	@Override
	public double execute() {
		// Does nothing, since lists cant be executed
		return 0;
	}
	public List<Instruction> getDependencies(){
		// Returns the list back to the caller
		return myDependencies;
	}
	@Override
	public int getNumberOfArguments() {
		return 1;
	}
}
