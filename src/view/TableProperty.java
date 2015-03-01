package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableProperty {

	private StringProperty myName;
	private StringProperty myVar;

	public TableProperty(String varName, String varValue) {
		this.myName = new SimpleStringProperty(varName);
		this.myVar = new SimpleStringProperty(varValue);
	}
	
	public TableProperty(String varName, double varValue){
		this.myName = new SimpleStringProperty(varName);
		this.myVar = new SimpleStringProperty(Double.toString(varValue));
	}
	

	public void setName(String name) {
		myName.set(name);
	}

	public String getName() {
		return myName.get();
	}

	public void setValue(String value) {
		myVar.set(value);
	}

	public String getValue() {
		return myVar.get();
	}

	public String getVar() {
		return myVar.get();
	}

	public StringProperty myNameProperty() {
		return myName;
	}

	public StringProperty myVarProperty() {
		return myVar;
	}
}
