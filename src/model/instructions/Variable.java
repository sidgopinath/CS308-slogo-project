// This entire file is part of my masterpiece.
// Sid Gopinath

package model.instructions;

import model.ExecutionEnvironment;

/**
 * In our program, Variables are stored as Instructions
 * This allows them to be "executed" like other instructions
 * This class interacts very directly with the ExecutionEnvironment, since that is where variables are stored
 * @author Greg
 *
 */

public class Variable extends Instruction{
	
	public Variable(String instructionType, ExecutionEnvironment environment) {
		super(instructionType, environment);
	}

	@Override
	public double execute(){
		return myEnvironment.myVariableMap.get(myInstructionType);
	}
	@Override
	public int getNumberOfArguments() {
		return 0;
	}
}
