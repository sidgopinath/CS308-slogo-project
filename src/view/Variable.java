package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Variable {
	
     private StringProperty myName;
     private IntegerProperty myVar;

     public Variable(String varName, Integer varValue){
    	 this.myName = new SimpleStringProperty(varName);
    	 this.myVar = new SimpleIntegerProperty(varValue);
     }
     
     public void setName(String name){// NameProperty().set(value); }
    	 myName.set(name);
     }
     
     public String getName() { //return NameProperty().get(); }
    	 return myName.get();
     }
     
     public void setValue(int value){// NameProperty().set(value); }
    	 myVar.set(value);
     }
     
     public int getVar() { //return NameProperty().get(); }
    	 return myVar.get();
     }
    	
     public StringProperty myNameProperty() {return myName;}
     
     public IntegerProperty myVarProperty() {return myVar;}
     /*
     public StringProperty NameProperty() { 
         if (myName == null) 
        	 myName = new SimpleStringProperty(this, "myName");
         return myName; 
     }
     
     
     public void setLastName(Integer num) { VarProperty().set(num); }
     
     public Integer getLastName() { return VarProperty().get(); }
     
     public IntegerProperty VarProperty() { 
         if (myVar == null) 
        	 myVar = new SimpleIntegerProperty(this, "myVar");
         return myVar; 
     }*/

}
