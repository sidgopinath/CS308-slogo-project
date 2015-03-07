package view;

//note: removed many getters and setters
//also removed myProperty instance variable because it seemed to be unused...

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Property {

	private StringProperty myName;
	private DoubleProperty myVar;

	public Property(String varName, double varValue) {
		myName = new SimpleStringProperty(varName);
		myVar = new SimpleDoubleProperty(varValue);
	}

	public Property(String varName, String property) {
		myName = new SimpleStringProperty(varName);
	}

	public String getName() {
		return myName.get();
	}

	public void setValue(double value) {
		myVar.set(value);
	}

	public double getValue() {
		return myVar.get();
	}
}
