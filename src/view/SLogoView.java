//TODO: use resource file instead of strings. Pass resource file as parameters to other classes

//TODO for backend: in order to change the state of the turtle, commands must always be passed back into the parser so have one global update method call theupdateTurtlePropperties method. 
//TODO: variableschange when a user-defined command is put even though thse variables should not really be set directly. or maybe it should?

package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.geometry.Dimension2D;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ExecutionEnvironment;
import model.Parser;

/**
 * Contains all the visual properties of SLogo, containing the workspace/display, the editor, and the sidebar.
 * @author Callie
 *
 */

public class SLogoView {
	private Stage myStage;
	protected ResourceBundle myResources = ResourceBundle
			.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private Map<String, Node> variables;
	private Drawer drawer;
	private Workspace myWorkspace;
	private Dimension2D myDimensions;
	private SideBar mySidebar;
	private Editor myEditor;
	private Parser myParser;
	private ExecutionEnvironment myEnvironment = null;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	
	public SLogoView(Stage s, Dimension2D myDimensions) {
		myStage = s;
		this.myDimensions = myDimensions;
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

	private Parser createNewParser(SLogoView view) {
		return new Parser(view);
	}

	private Button configureAddTurtlesButton() {
		Button newTurtleButton = new Button(myResources.getString("AddTurtle"));
		newTurtleButton.setStyle("-fx-base: #b6e7c9;");
		newTurtleButton.setOnAction(e -> myWorkspace.addTurtle(myEnvironment));
		GridPane.setHalignment(newTurtleButton, HPos.CENTER);
		return newTurtleButton;
	}

	public GridPane configureUI() {
		GridPane root = new GridPane();
		setGridPaneConstraints(root);
		Map<Integer, TurtleView> myTurtles = new HashMap<Integer, TurtleView>(); // TODO:																				// move

		mySidebar = new SideBar();
		myWorkspace = new Workspace(myDimensions, mySidebar);
		drawer = new Drawer(myWorkspace);
		CustomizationBar customizationBar = new CustomizationBar( myTurtles, drawer, myWorkspace, myStage,
				myDimensions);
		root.add(customizationBar, 0, 0);
		root.add(configureAddTurtlesButton(), 1, 0);
		root.add(myWorkspace, 0, 1);
		root.add(mySidebar, 1, 1, 1, 2);
		
		myParser = createNewParser(this);
		mySidebar.setParser(myParser);
		customizationBar.setParser(myParser);

		myEditor = new Editor(myParser, mySidebar, myDimensions);
		root.add(myEditor, 0, 2);
		return root;
	}

	public void updateVariables(Map<String, Double> variableUpdates) {
		Iterator<Entry<String, Double>> it = variableUpdates.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Double> variable = it.next();
			String name = variable.getKey();
			double value = variable.getValue();
			if (variables.get(name) == null) {
				mySidebar.updateVariable(new Property(name, value));
		//	} else {
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
		mySidebar.updateTurtleProperties(id, myWorkspace); // are these methods
															// duplicated
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
	public void setEnvironment(ExecutionEnvironment e){
		myEnvironment = e;
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


	public SideBar getSidebar(){
		return mySidebar;
	}
	
	public Workspace getWorkspace(){
		return myWorkspace;
	}
	
	public Drawer getDrawer(){
		return drawer;
	}


}
