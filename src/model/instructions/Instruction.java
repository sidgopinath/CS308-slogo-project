package model.instructions;

import model.Polar;

public abstract class Instruction {
    public int turtleId;
    public Polar polar;
    public boolean penUp;
    public boolean jump;
    
    public Instruction(int turtleId,Polar polar,boolean penUp,boolean jump){
        this.turtleId=turtleId;
        this.polar=polar;
        this.penUp=penUp;
        this.jump=jump;
    }
    
    public abstract void execute();
}