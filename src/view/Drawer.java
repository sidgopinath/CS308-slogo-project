package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import model.Polar;
import model.turtle.TurtleCommand;

public class Drawer {
    private Color color;
    private double myXBounds;
    private double myYBounds;
    
    public Drawer(double xMax, double yMax){
    	myXBounds = xMax;
    	myYBounds = yMax;
    	color = Color.BLACK;
    }

   public List<Polyline> draw(Map<Integer, TurtleView> turtles, List<TurtleCommand> instrucions){
       ArrayList<Polyline> lines= new ArrayList<Polyline>();
       Iterator<TurtleCommand> it = instrucions.iterator();
       while(it.hasNext()){
           TurtleCommand instruction = it.next();
           TurtleView turtle = turtles.get(instruction.turtleId);
           Polar polar = instruction.polar;
           if(polar.distance!=0){
               double angle=turtle.getRotate();
               double turtleX=turtle.getLayoutX();
               double turtleY=turtle.getLayoutY();
               double moveX=Math.sin(Math.toRadians(polar.angle+180-angle))*polar.distance;
               double moveY=Math.cos(Math.toRadians(polar.angle+180-angle))*polar.distance;
               double startX=turtleX+turtle.getTranslateX()+turtle.getFitWidth()/2;
               double startY=turtleY+turtle.getTranslateY()+turtle.getFitHeight()/2;
               System.out.println(startX+","+startY);
               
               double newX = turtle.getTranslateX()+moveX;
               double newY = turtle.getTranslateY()+moveY;
               if (newX < myXBounds && newY < myYBounds){
	               turtle.move(turtle.getTranslateX()+moveX,turtle.getTranslateY()+moveY);
	               double endX=turtle.getTranslateX()+turtleX+turtle.getFitWidth()/2;
	               double endY=turtle.getTranslateY()+turtleY+turtle.getFitHeight()/2;
	               System.out.println(endX+","+endY);
	               if(!instruction.penUp){
	                   Polyline polyline = new Polyline();
	                   polyline.setStroke(color);
	                   polyline.getPoints().addAll(new Double[]{startX, startY,endX, endY});
	                   lines.add(polyline);
	               }
               }
               else{
            	   //TODO: make turtles and lines invisible when out of bounds
               }
           }else{
               turtle.turn(turtle.getRotate()+polar.angle);
           }
       }
       return lines;
   }

    public void changeColor(Color c) {
        System.out.println("color" + color.toString());

        color=c;

    }
}
