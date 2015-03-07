package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

	public double getCurrentTurtleID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getNumTurtles() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
