package model.instructions;

import model.Polar;

public class Instruction {
    public int turtleId;
    public Polar polar;
    public boolean penUp;
    
    public Instruction(int turtleId,Polar polar,boolean penUp){
        this.turtleId=turtleId;
        this.polar=polar;
        this.penUp=penUp;
    }
}
