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
		SetBackground(1),
		SetPenColor(1),
		SetPenSize(1),
		SetShape(1),
		SetPalette(4),
		GetPenColor(0),
		GetShape(0),
		Stamp(0),
		ClearStamps(0);
		
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
		case "SetPenColor":
			return myViewUpdater.set(myDependencies.get(0).execute());
		case "SetPenSize":
			return myViewUpdater.set(myDependencies.get(0).execute());
		case "SetShape":
			return 0;
		case "SetPalette":
			return 0;
		case "GetPenColor":
			return ViewUpdater.getBackgroundColor();
		case "GetShape":
			return 0;
		case "Stamp":
			return 0;
		case "ClearStamps":
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
