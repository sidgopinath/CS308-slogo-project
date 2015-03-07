package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import model.Polar;
import view.SLogoView;

public class MultipleTurtlesInstruction extends Instruction{
	
	public enum implementers {
		ID(0),
		TURTLES(0),
		TELL(1),
		ASK(2),
		ASKWITH(2);
		
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
	}
}
	public MultipleTurtlesInstruction(List<Instruction> dependencies,
			String instructionType, SLogoView view,
			ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		switch(myInstructionType.toUpperCase()){
		case "ID":
			return myReturnVal;
		case "TURTLES":
			return myDependencies.get(0).execute();
		case "TELL":
			return myDependencies.get(0).execute();
		case "ASK":
			return myDependencies.get(0).execute();
		case "ASKWITH":
			return myDependencies.get(0).execute();
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return 0;
	}

}
