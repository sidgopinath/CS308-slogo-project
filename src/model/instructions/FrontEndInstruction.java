package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import model.instructions.BooleanInstruction.implementers;
import view.SLogoView;
import view.ViewUpdater;

public class FrontEndInstruction extends Instruction{
	public FrontEndInstruction(List<Instruction> dependencies,
			String instructionType, ViewUpdater updater,
			ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	public enum implementers {
		SETBACKGROUND(1),
		SETPENCOLOR(1),
		SETPENSIZE(1),
		SETSHAPE(1),
		SETPALETTE(4),
		GETPENCOLOR(0),
		GETSHAPE(0),
		STAMP(0),
		CLEARSTAMPS(0);
		
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
	}
}

	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "SETBACKGROUND":
			return myViewUpdater.setBackgroundColor(myDependencies.get(0).execute());
		case "SETPENCOLOR":
			return myViewUpdater.setPenColor(myDependencies.get(0).execute());
		case "SETPENSIZE":
			return myViewUpdater.setPenSize(myDependencies.get(0).execute());
		case "SETSHAPE":
			return myViewUpdater.setShape(myDependencies.get(0).execute(), myEnvironment.getActiveTurtle());
		//case "SETPALETTE":
			//return 0;
		case "GETPENCOLOR":
			return 0; //myViewUpdater.getBackgroundColor();
		case "GETSHAPE":
			return 0;
		case "STAMP":
			return 0;
		case "CLEARSTAMPS":
			return 0;
		default:
			return -1;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}
