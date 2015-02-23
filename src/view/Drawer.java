package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import model.Polar;
import model.instructions.Instruction;

public class Drawer {
    private Color color = Color.BLACK;

       public List<Polyline> draw(Map<Integer, ImageView> turtles, ArrayList<Instruction> instrucions){
           ArrayList<Polyline> lines= new ArrayList<Polyline>();
           Iterator<Instruction> it = instrucions.iterator();
           while(it.hasNext()){
               Instruction instruction = it.next();
               ImageView turtle = turtles.get(instruction.turtleId);
               Polar polar = instruction.polar;
               double turtleX=turtle.getX();
               double turtleY=turtle.getY();
               double turtleNewX=turtleX+Math.sin(polar.angle)*polar.distance;
               double turtleNewY=turtleY+Math.sin(polar.angle)*polar.distance;
               if(!instruction.penUp){
                   Polyline polyline = new Polyline();
                   polyline.setStroke(color);
                   polyline.getPoints().addAll(new Double[]{turtleX, turtleY,turtleNewX, turtleNewY});
                   lines.add(polyline);
               }
               turtle.setX(turtleNewX);
               turtle.setY(turtleNewY);
               turtle.setRotate(polar.angle);
           }
           return lines;
       }

    public void changeColor (Color c) {
        color=c;
    }
}
