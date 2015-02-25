package model.instructions;

import java.util.List;

public class MathInstruction extends Instruction{
	// First input is command type, second is first argument, third is second argument
	// Division by Zero returns 0 always
	// need to fix so that it returns the right number of arguments needed to the command tree
	public enum implementers {
		SUM(2),
		DIFFERENCE (2),
		PRODUCT(2),
		QUOTIENT(2),
		REMAINDER(2),
		MINUS(1),
		RANDOM(1),
		SINE(1),
		COSINE(1),
		TANGENT(1),
		ARCTANGENT(1),
		NATURALLOG(1),
		POWER(2),
		PI(0);
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
}
	String instructionType;
	List<Instruction> myDependencies;
    public MathInstruction(List<Instruction> dependencies, String inType) {
    	myDependencies=dependencies;
    	instructionType = inType;
	}
	@Override
	public double execute() {
		Instruction arg1 = myDependencies.get(0);
		Instruction arg2 = myDependencies.get(1);
		switch(instructionType.toUpperCase()){
		case "SUM":
			return arg1.execute()+arg2.execute();
		case "DIFFERENCE":
			return arg1.execute()-arg2.execute();
		case "PRODUCT":
			return arg1.execute()*arg2.execute();
		case "QUOTIENT":
			return divByZeroCheck(arg1.execute()/arg2.execute());
		case "REMAINDER":
			return arg1.execute()%arg2.execute();
		case "MINUS":
			return -arg1.execute();
		case "RANDOM":
			return Math.random()*arg1.execute();
		case "SIN":
			return Math.toDegrees(Math.sin(arg1.execute()));
		case "COS":
			return Math.toDegrees(Math.cos(arg1.execute()));
		case "TAN":
			return Math.toDegrees(divByZeroCheck((Math.tan(arg1.execute()))));
		case "ATAN":
			return Math.toDegrees(divByZeroCheck((Math.atan(arg1.execute()))));
		case "LOG":
			return Math.log(arg1.execute());
		case "POW":
			return Math.pow(arg1.execute(), arg2.execute());
		case "PI":
			return Math.PI;
		default: 
			// view.displayException(new Exception());
			return 0;
		}
	}

	private double divByZeroCheck(double zeroCheck) {
		if(zeroCheck==Double.POSITIVE_INFINITY||zeroCheck==Double.NEGATIVE_INFINITY||zeroCheck==Double.NaN)
			return 0.0;
		else
			return zeroCheck;
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(instructionType.toUpperCase()).numArgs;
	}

}
