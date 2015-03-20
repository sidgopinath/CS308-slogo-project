package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;
/**
 * Class which contains sprint 3's instructions, most of which involve interaction with the view.  Some have not been implemented due to complexities in
 * how the front end is laid out.
 **/
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
			return myViewUpdater.setShape(myDependencies.get(0).execute(), myEnvironment.myActiveTurtle);
		//case "SETPALETTE":
			//return 0;
		case "GETPENCOLOR":
			return 0; //myViewUpdater.getBackgroundColor();
		case "GETSHAPE":
			return 0;
		case "STAMP":
			return myViewUpdater.createStamp(myEnvironment.myActiveTurtle);
		case "CLEARSTAMPS":
			 return myViewUpdater.clearStamps(myEnvironment.myActiveTurtle);
		default:
			return -1;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}
