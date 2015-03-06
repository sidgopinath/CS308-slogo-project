//TODO: use resource file instead of strings. Pass resource file as parameters to other classes

//TODO for backend: in order to change the state of the turtle, commands must always be passed back into the parser so have one global update method call theupdateTurtlePropperties method. 
//TODO: variableschange when a user-defined command is put even though thse variables should not really be set directly. or maybe it should?

package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ExecutionEnvironment;
import model.turtle.Turtle;
import model.turtle.TurtleCommand;
import controller.SLogoController;

public class SLogoView implements Observer {
	private Stage myStage;
	protected ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private Map<String, Node> variables;
	private Drawer drawer;
	private Workspace myWorkspace;
	private Group lines = new Group();
	private SLogoController myController=createNewController(this);
	private Dimension2D myDimensions;
	// private int activeTurtleID; //TODO: update this
	private SideBar mySidebar;
	private Editor myEditor;

	// TODO: move myTUrtles to relavant class (Workspace). Maybe drawer too? But
	// there is no functionality after moving it
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";

	public SLogoView(Stage s, Dimension2D myDimensions) {
		myStage = s;
		createNewController(this);
		// activeTurtleID = 1;
		this.myDimensions=myDimensions;
		lines.setManaged(false);
	}

	private void setGridPaneConstraints(GridPane root) {
		
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(4);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(60);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(22);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(75);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(25);
		root.getColumnConstraints().add(col1);
		root.getColumnConstraints().add(col2);
        root.getRowConstraints().add(row0);
        root.getRowConstraints().add(row1);
        root.getRowConstraints().add(row2);
	}

    private SLogoController createNewController(SLogoView view) {
        return new SLogoController(view);
    }

    private Button configureAddTurtlesButton() {
        // Add turtle button
        Button newTurtleButton = new Button(myResources.getString("AddTurtle"));
        newTurtleButton.setStyle("-fx-base: #b6e7c9;");
        newTurtleButton.setAlignment(Pos.CENTER_RIGHT);
        newTurtleButton.setOnAction(e -> myWorkspace.addTurtle());
        GridPane.setHalignment(newTurtleButton, HPos.CENTER);
        return newTurtleButton;
    }

    private void updateCommand(String s) {
        mySidebar.updateCommand(s);
    }

	// methods for the backend to call. TODO: organize better
    public GridPane configureUI() {
        GridPane root = new GridPane();
        setGridPaneConstraints(root);
        
        // set initial turtle
        
        //temp
       // TurtleView turtle = new TurtleView(0, new Image(Strings.DEFAULT_TURTLE_IMG));
      //  myTurtles.put(0, turtle);
    	Map<Integer, TurtleView> myTurtles = new HashMap<Integer, TurtleView>(); //TODO: move

        mySidebar = new SideBar(myWorkspace, myController);
        myWorkspace = new Workspace(lines, myDimensions, mySidebar);
        drawer = new Drawer(myWorkspace);
        root.add(new CustomizationBar(myController, myTurtles, drawer, myWorkspace, myStage, myDimensions), 0, 0);
        root.add(configureAddTurtlesButton(), 1, 0);
        root.add(myWorkspace, 0, 1);
        root.add(mySidebar, 1, 1, 1, 2);
        myEditor = new Editor(myController, mySidebar, myDimensions);
        root.add(myEditor, 0, 2);
        return root;
    }

    // does this do anything?
    // do we need to return anything

    // do we still need an entire list for this?
    public void updateWorkspace(List<TurtleCommand> instructionList) {
        // String returnString = null;
        /*
         * for (TurtleCommand instruction : instructionList) { returnString +=
         * updateFromInstruction(instruction) + "\n"; }
         */
    	//System.out.println(myWorkspace.getTurtleMap());
        List<Polyline> newlines = drawer.draw(myWorkspace.getTurtleMap(), instructionList, mySidebar);
        lines.getChildren().addAll(newlines);

        // return returnString;
    }

    public Turtle getTurtleInfo(int index) {
        ImageView temp = myWorkspace.getTurtleMap().get(index);
        return new Turtle(temp.getX(), temp.getY(), temp.getRotate());

    }

    // this one is not actually used
    // TODO: what is being passed in and how to update the tableview? may have
    // to iterate through observablelist
    public void updateVariables(Map<String, Double> variableUpdates) {
        Iterator<Entry<String, Double>> it = variableUpdates.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Double> variable = it.next();
            String name = variable.getKey();
            double value = variable.getValue();
            if (variables.get(name) == null) {
                // TODO: it should be passed in not as a map but as the actual
                // variable object in the parameter
                // or we can just keep the variables object as just a front end
                // thing for displaying (otherwise both front and back end have
                // access to it which may not be good)
                mySidebar.updateVariable(new Property(name, value));
            } else {
                // variables.get(name).setText(value);
            }
        }
    }

    public double towards(int id, double x, double y) {
        double angle = Math.toDegrees(Math.atan2(x, y));
        return setHeading(id, angle, false);
    }

	public double setXY(int id, double x, double y) {
		double xy = myWorkspace.getTurtleMap().get(id).setXY(x, y);
		mySidebar.updateTurtleProperties(id, myWorkspace); // are these methods duplicated
												// code since they are all the
												// same thing with just one
												// added line in them?
		return xy;
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
		/*
		 * if (setPen){ myTurtles.get(id).setPenUp(true); //return 0; }
		 * myTurtles.get(id).setPenUp(false); //return 1;
		 */

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

	public void showTurtle(int id, boolean show) {
		myWorkspace.getTurtleMap().get(id).showTurtle(show);
		mySidebar.updateTurtleProperties(id, myWorkspace);
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

	public void openDialog(String message) {
		Stage stage = new Stage();
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		Text text = new Text(message);
		root.getChildren().add(text);

		Scene scene = new Scene(root, myDimensions.getWidth() / 4,
				myDimensions.getHeight() / 6);

		stage.setTitle(myResources.getString("Error"));
		stage.setScene(scene);
		stage.show();
	}

	//public void updateVariable(Property variable) {
	//	mySidebar.updateVariable(variable);
	//}

	public double clearScreen(int id) {
		lines.getChildren().clear();
		myWorkspace.getTurtleMap().get(id).setAbsoluteHeading(0);
		double dist = myWorkspace.getTurtleMap().get(id).setXY(0, 0);
		mySidebar.updateTurtleProperties(id, myWorkspace);
		return dist;
	}

	@Override
	public void update(Observable o, Object arg) {
		ExecutionEnvironment env = (ExecutionEnvironment) o;
		for (String s : env.getVariableMap().keySet()) {
			double value = env.getVariableMap().get(s);
			mySidebar.updateVariable(new Property(s, value));
		}
		for (String s : env.getUserCommandMap().keySet()) {
			updateCommand(s);
		}
		
	}
}
