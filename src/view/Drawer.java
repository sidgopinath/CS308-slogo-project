package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import model.Polar;
import model.turtle.TurtleCommand;

public class Drawer {
    private Color color;
    private double[] myXBounds = new double[2];
    private double[] myYBounds = new double[2];
    private double halfX;
    private double halfY;
    
    public Drawer(double xMax, double yMax){
    	myXBounds[0] = 20;
    	myXBounds[1] = xMax+20;
    	myYBounds[0] = 15;
    	myYBounds[1] = yMax+15;
    	halfX=0.5*(myXBounds[1]-myXBounds[0]);
    	halfY=0.5*(myYBounds[1]-myYBounds[0]);
    	color = Color.BLACK;
    }
    
   //may have to remove list from turtlecommand
   public List<Polyline> draw(Map<Integer, TurtleView> turtles, List<TurtleCommand> instructions){
	   ArrayList<Polyline> lines= new ArrayList<Polyline>();
       Iterator<TurtleCommand> it = instructions.iterator();
       while(it.hasNext()){
           TurtleCommand command = it.next();

           TurtleView turtle = turtles.get(command.getTurtleId());
           Polar polar = command.getPolar();
          
           //move turtle and draw line
           //TODO: this is a conditional, nested if-else. Could we possibly make this less condition-specific?
           if(polar.distance!=0){
               System.out.println("Y bound is: "+myYBounds[0]+" to "+myYBounds[1]);
               double angle=turtle.getRotate();
               double turtleX=turtle.getLayoutX();
               double turtleY=turtle.getLayoutY();
               double moveX=Math.sin(Math.toRadians(polar.angle+180-angle))*polar.distance;
               double moveY=Math.cos(Math.toRadians(polar.angle+180-angle))*polar.distance;
               double startX=turtleX+turtle.getTranslateX()+turtle.getFitWidth()/2;
               double startY=turtleY+turtle.getTranslateY()+turtle.getFitHeight()/2;
               double newX=turtleX+moveX+turtle.getTranslateX()+15;
               double newY=turtleY+moveY+turtle.getTranslateY()+15;
               System.out.println("turtleY is "+turtleY);
               System.out.println("new Y is "+newY);
               if (newY < myYBounds[0]){
                   double endX=(turtle.getTranslateX()+turtleX+turtle.getFitWidth()/2)*(newY-myYBounds[0])/moveY;
                   turtle.move(turtle.getTranslateX()+moveX,wrapY(0,newY));
                   if(!turtle.getPenUp()){
                       double endY=turtle.getTranslateY()+turtleY+turtle.getFitHeight()/2;
                       System.out.println("endX is "+endX);
                       lines.add(drawLine(startX,startY,endX,myYBounds[0]));
                       lines.add(drawLine(startX,myYBounds[1],endX,endY));
                   }
               }else if (newY > myYBounds[1]){
                   turtle.move(turtle.getTranslateX()+moveX,wrapY(1,newY));
                   if(!turtle.getPenUp()){
                       double endX=turtle.getTranslateX()+turtleX+turtle.getFitWidth()/2;
                       double endY=turtle.getTranslateY()+turtleY+turtle.getFitHeight()/2;
                       lines.add(drawLine(startX,startY,endX,myYBounds[1]));
                       lines.add(drawLine(startX,myYBounds[0],endX,endY));
                   }
               }else if(newX < myXBounds[0]){
                   double right=halfX-myXBounds[0]+newX;
                   turtle.move(right,turtle.getTranslateY()+moveY);
                   if(!turtle.getPenUp()){
                       double endX=turtle.getTranslateX()+turtleX+turtle.getFitWidth()/2;
                       double endY=turtle.getTranslateY()+turtleY+turtle.getFitHeight()/2;
                       lines.add(drawLine(startX,startY,myXBounds[0],endY));
                       lines.add(drawLine(myXBounds[1],startY,endX,endY));
                   }
               }else if(newX > myXBounds[1]){
                   double left=-(halfX-newX+myXBounds[1]);
                   turtle.move(left,turtle.getTranslateY()+moveY);
                   if(!turtle.getPenUp()){
                       double endX=turtle.getTranslateX()+turtleX+turtle.getFitWidth()/2;
                       double endY=turtle.getTranslateY()+turtleY+turtle.getFitHeight()/2;
                       lines.add(drawLine(startX,startY,myXBounds[1],endY));
                       lines.add(drawLine(myXBounds[0],startY,endX,endY));
                   }
               }
               else{
                   turtle.move(turtle.getTranslateX()+moveX,turtle.getTranslateY()+moveY);
                   double endX=turtle.getTranslateX()+turtleX+turtle.getFitWidth()/2;
                   double endY=turtle.getTranslateY()+turtleY+turtle.getFitHeight()/2;
                   System.out.println(endX+","+endY);
                   if(!turtle.getPenUp()){
                       Polyline polyline = new Polyline();
                       polyline.setStroke(color);
                       polyline.getPoints().addAll(new Double[]{startX, startY,endX, endY});
                       lines.add(polyline);
                   }
               }
           }else{
        	   if (command.isRelative()){
        		   turtle.setRelativeHeading(polar.angle);
        	   }
        	   else{
        		   turtle.setAbsoluteHeading(polar.angle);
        	   }
           }
       }
       return lines;
   }

    private double wrapY (int i, double newY) {
        return Math.pow(-1,i)*halfY-myYBounds[i]+newY;
}

    private Polyline drawLine (double startX, double startY, double endX, double endY) {
        Polyline polyline = new Polyline();
        polyline.setStroke(color);
        polyline.getPoints().addAll(new Double[]{startX, startY,endX, endY});
        return polyline;
    }

    public void changeColor(Color c) {
        System.out.println("color" + color.toString());

        color=c;
    }
    
   /* public void setPenUp(boolean isUp){
    	penUp = isUp;
    } */
}
