package model;

import java.util.HashMap;
import java.util.Map;
import model.instructions.Instruction;

public class ExecutionEnvironment {
	private Map<String,Instruction> userInstructionMap;
	private Map<String, Instruction> variableMap;
	public ExecutionEnvironment(){
		clear();
	}
	public void addInstruction(String commandName, Instruction inInstruction){
		userInstructionMap.put(commandName, inInstruction);
	}
	public void addVariable(String variableName, Instruction value){
		variableMap.put(variableName, value);
	}
	public Instruction getVariable(String key){
		return variableMap.get(key);
	}
	public Instruction getInstruction(String key){
		return userInstructionMap.get(key);
	}
	public void clear(){
		userInstructionMap=new HashMap();
		variableMap = new HashMap();
	}
}
