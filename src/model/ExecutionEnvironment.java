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
	
	public void addCommand(String commandName, Instruction inInstruction){
		if(myUserInstructionMap.containsKey(commandName))
			myUserInstructionMap.remove(commandName);
		myUserInstructionMap.put(commandName, inInstruction);
		updateObserver();
	}
	
	public void addVariable(String variableName, Instruction value){
		if(myVariableMap.containsKey(variableName))
			myVariableMap.remove(variableName);
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
	
	public void removeDuplicate(String s){
		if(myVariableMap.containsKey(s))
			myVariableMap.remove(s);
	}
		
	public Instruction getCommand(String key){
		return myUserInstructionMap.get(key);

	}
	
	public void clear(){
		myUserInstructionMap = new HashMap<String, Instruction>();
		myVariableMap = new HashMap<String, Instruction>();
	}
	
	public Map<String, Instruction> getUserCommandMap(){
		return myUserInstructionMap;
	}
	
	public Map<String, Instruction> getVariableMap(){
		return myVariableMap;
	}
}