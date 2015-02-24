package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import model.Polar;
import model.instructions.TurtleCommand;

public class Drawer {
    private Color color = Color.BLACK;

   public List<Polyline> draw(Map<Integer, TurtleView> turtles, ArrayList<TurtleCommand> instrucions){
	   System.out.println("hi");
       ArrayList<Polyline> lines= new ArrayList<Polyline>();
       Iterator<TurtleCommand> it = instrucions.iterator();
       while(it.hasNext()){
           TurtleCommand instruction = it.next();
           TurtleView turtle = turtles.get(instruction.turtleId);
           Polar polar = instruction.polar;
           double turtleX=turtle.getX();
           double turtleY=turtle.getY();
           if(polar.distance!=0){
               double turtleNewX=turtleX+Math.sin(polar.angle)*polar.distance;
               double turtleNewY=turtleY+Math.cos(polar.angle)*polar.distance;
               //just use the converter for the above code
               turtle.setX(turtleNewX);
               turtle.setY(turtleNewY);
               System.out.println(turtle.getY());
               if(!instruction.penUp){
                   Polyline polyline = new Polyline();
                   polyline.setStroke(color);
                   polyline.getPoints().addAll(new Double[]{turtleX, turtleY,turtleNewX, turtleNewY});
                   lines.add(polyline);
               }
           }else{
               turtle.setRotate(turtle.getRotate()+polar.angle);
           }
       }
       return lines;
   }

    public void changeColor(Color c) {
        color=c;
        System.out.println(color.toString());
    }
}
