package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.instructions.Instruction;

public class ExecutionEnvironment extends Observable{
	
	private Map<String,Instruction> myUserInstructionMap;
	private Map<String, Double> myVariableMap;
	
	public ExecutionEnvironment(){
		clear();
	}
	
	public void addCommand(String commandName, Instruction inInstruction){
		if(myUserInstructionMap.containsKey(commandName))
			myUserInstructionMap.remove(commandName);
		myUserInstructionMap.put(commandName, inInstruction);
		updateObserver();
	}
	
	public void addVariable(String variableName, Double value){
		if(myVariableMap.containsKey(variableName))
			myVariableMap.remove(variableName);
		myVariableMap.put(variableName, value);
		updateObserver();
	}
	
	/*public void updateVariableName(String oldName, String newName){
		myVariableMap.put(newName, myVariableMap.get(oldName));
		myVariableMap.remove(oldName);
		updateObserver();
	}*/

	private void updateObserver() {
		setChanged();
		notifyObservers();
		clearChanged();
	}

	public Double getVariable(String key){
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
		myVariableMap = new HashMap<String, Double>();
	}
	
	public Map<String, Instruction> getUserCommandMap(){
		return myUserInstructionMap;
	}
	
	public Map<String, Double> getVariableMap(){
		return myVariableMap;
	}
}