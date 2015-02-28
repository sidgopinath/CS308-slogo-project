package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.instructions.Instruction;

public class ExecutionEnvironment extends Observable{
	
	private Map<String,Instruction> myUserInstructionMap;
	private Map<String, Instruction> myVariableMap;
	
	public ExecutionEnvironment(){
		clear();
	}
	
	public void addInstruction(String commandName, Instruction inInstruction){
		myUserInstructionMap.put(commandName, inInstruction);
		updateObserver();
	}
	
	public void addVariable(String variableName, Instruction value){
		myVariableMap.put(variableName, value);
		updateObserver();
	}

	private void updateObserver() {
		setChanged();
		notifyObservers();
		clearChanged();
	}
	
	public Instruction getVariable(String key){
		return myVariableMap.get(key);
	}
	
	public Instruction getInstruction(String key){
		return myUserInstructionMap.get(key);
	}
	
	public void removeDuplicate(String s){
		if(myVariableMap.containsKey(s))
			myVariableMap.remove(s);
	}
	
	public void clear(){
		myUserInstructionMap = new HashMap<String, Instruction>();
		myVariableMap = new HashMap<String, Instruction>();
	}
	
	public Map<String, Instruction> getUserInstructionMap(){
		return myUserInstructionMap;
	}
	
	public Map<String, Instruction> getVariableMap(){
		return myVariableMap;
	}
}