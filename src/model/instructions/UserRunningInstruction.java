package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 * 
 * @author Greg
 *
 */

public class UserRunningInstruction extends Instruction{
	// dependencies will be a list of constants for assignment
	public UserRunningInstruction(List<Instruction> dependencies,
			String instructionType, ViewUpdater updater,
			ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	@Override
	public double execute() {
		double returnVal = 0;
		Instruction userCommand = myEnvironment.getCommand(myInstructionType);
		// add all variables given, assigned to proper names
		for(int i = 0; i<myDependencies.get(0).myDependencies.size(); i++){
			myEnvironment.addVariable(userCommand.myDependencies.get(1).myDependencies.get(i).getName(), myDependencies.get(0).myDependencies.get(i).execute());	
		}
		for(Instruction i: userCommand.myDependencies.get(2).myDependencies){
			returnVal = i.execute();
		}
		System.out.println(returnVal);
		return returnVal;
	}

	@Override
	public int getNumberOfArguments() {
		return 1;
	}
}