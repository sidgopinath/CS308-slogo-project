package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.instructions.Instruction;

public class ExecutionEnvironment extends Observable{
	private Map<String,Instruction> userInstructionMap;
	private Map<String, Instruction> variableMap;
	public ExecutionEnvironment(){
		clear();
	}
	public void addInstruction(String commandName, Instruction inInstruction){
		userInstructionMap.put(commandName, inInstruction);
		setChanged();
		notifyObservers();
		clearChanged();
	}
	public void addVariable(String variableName, Instruction value){
		variableMap.put(variableName, value);
		setChanged();
		notifyObservers();
		clearChanged();
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
	
	public Map<String, Instruction> getUserInstructionMap(){
		return userInstructionMap;
	}
	
	public Map<String, Instruction> getVariableMap(){
		return variableMap;
	}
	
	
}
