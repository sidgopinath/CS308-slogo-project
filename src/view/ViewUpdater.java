package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.ExecutionEnvironment;
import model.TurtleCommand;

public class ViewUpdater implements Observer{

	Workspace myWorkspace;
	SideBar mySidebar;
	Drawer myDrawer;
	
	public ViewUpdater(SLogoView view){
		myWorkspace = view.getWorkspace();
		mySidebar = view.getSidebar();
		myDrawer = view.getDrawer();
	}
	
	public double clearScreen(int id){
		myWorkspace.getTurtleMap().get(id).setAbsoluteHeading(0);
		myWorkspace.clearLines();
		double dist = myWorkspace.getTurtleMap().get(id).setXY(0, 0);
		mySidebar.updateTurtleProperties(id, myWorkspace);
		return dist;
	}
	
	public void showTurtle(int id, boolean show) {
		myWorkspace.getTurtleMap().get(id).showTurtle(show);
		mySidebar.updateTurtleProperties(id, myWorkspace);
	}
	
	public double setXY(int id, double x, double y) {
		double xy = myWorkspace.getTurtleMap().get(id).setXY(x, y);
		mySidebar.updateTurtleProperties(id, myWorkspace); 
		return xy;
	}
	
	public double towards(int id, double x, double y) {
		double angle = Math.toDegrees(Math.atan2(x, y));
		return setHeading(id, angle, false);
	}



	public double setHeading(int id, double angle, boolean relative) {
		double heading;
		if (relative) {
			heading = myWorkspace.getTurtleMap().get(id).setRelativeHeading(angle);

		} else {
			heading = myWorkspace.getTurtleMap().get(id).setAbsoluteHeading(angle);
		}
		mySidebar.updateTurtleProperties(id, myWorkspace);
		return heading;
	}

	public void setPenUp(int id, boolean setPen) {
		myWorkspace.getTurtleMap().get(id).setPenUp(setPen);
		mySidebar.updateTurtleProperties(id, myWorkspace);
	}

	public double getPenDown(int id) {
		if (myWorkspace.getTurtleMap().get(id).getPenUp())
			return 0;
		return 1;
	}

	public double isShowing(int id) {
		if (myWorkspace.getTurtleMap().get(id).isVisible())
			return 1;
		return 0;
	}

	public double getHeading(int id) {
		return myWorkspace.getTurtleMap().get(id).getRotate();
	}

	// these definitely methods should not be in SLogoView;
	public double getXCor(int id) {
		return myWorkspace.getTurtleMap().get(id).getTranslateX();
	}

	public double getYCor(int id) {
		return Double.parseDouble(myWorkspace.getTurtleMap().get(id).getYCoord());
	}
	
	
	private void updateCommand(String s) {
		mySidebar.updateCommand(s);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		ExecutionEnvironment env = (ExecutionEnvironment) o;
		mySidebar.updateExecutionEnvironment(env);
		for (String s : env.getVariableMap().keySet()) {
			double value = env.getVariableMap().get(s);
			mySidebar.updateVariable(new Property(s, value));
		}
		for (String s : env.getUserCommandMap().keySet()) {
			updateCommand(s);
		}
	}
	
	public void updateWorkspace(List<TurtleCommand> instructionList) {
		myWorkspace.getLines().getChildren().addAll(myDrawer.draw(myWorkspace.getTurtleMap(), instructionList, mySidebar));
	}

	public double setBackgroundColor(double index){
		int colorIndex = (int) index;
		
		//TODO
		List<Color> colorList = new ArrayList<Color>();
		colorList.add(Color.RED);
		colorList.add(Color.ORANGE);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.BLUE);
		colorList.add(Color.PURPLE);
		colorList.add(Color.BLACK);
		
		myWorkspace.setBackground(colorList.get(colorIndex));
		return index;
	}
	
	public double setPenColor(double index){
		int colorIndex = (int) index;
		
		//TODO
		List<Color> colorList = new ArrayList<Color>();
		colorList.add(Color.RED);
		colorList.add(Color.ORANGE);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.BLUE);
		colorList.add(Color.PURPLE);
		colorList.add(Color.BLACK);

		myDrawer.changeColor(colorList.get(colorIndex));	
		return index;
	}
	
	public double setPenSize(double index){
		myDrawer.setStroke(index);
		return index;
	}
	
	public double setShape(double i, int id){
		//TODO
		int index = (int) i;
		if (index == 0){
			myWorkspace.getActiveTurtle().setImage(new Image("resources/images/defaultTurtle.png")); //default turtle
			myWorkspace.getTurtleMap().get(id).setImageID(index);
		}
		else if (index == 1){
			myWorkspace.getActiveTurtle().setImage(new Image("resources/images/happyTurtle.png")); //happy turtle
			myWorkspace.getTurtleMap().get(id).setImageID(index);
		}
		else{
			//TODO
		//	myWorkspace.getActiveTurtle().setImage(new Image("resources/images/triangleTurtle.png")); //triangle
			myWorkspace.getTurtleMap().get(id).setImageID(index);
		}
		return index;
	}
	
	public double getShapeIndex(int id){
		return myWorkspace.getTurtleMap().get(id).getImageID();
	}
	
	/*public double getPenColor(){
		return myWorkspace.getPenColor();
	}*/
	
	
	
	
	
	
	
}
