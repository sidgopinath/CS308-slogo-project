package model.instructions;
import model.Polar;

public class TurtleCommand {
    
    public int turtleId;
    public Polar polar;
    public boolean penUp;
    public boolean jump;

    public TurtleCommand(int turtleId,Polar polar,boolean penUp,boolean jump){
        this.turtleId=turtleId;
        this.polar=polar;
        this.penUp=penUp;
        this.jump=jump;
    }

}
