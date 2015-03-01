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
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ExecutionEnvironment;
import model.turtle.Turtle;
import model.turtle.TurtleCommand;
import controller.SLogoController;

public class SLogoView implements Observer {
	private Stage myStage;
	private GridPane myRoot;
	protected ResourceBundle myResources;
	private Map<String, Node> variables;
	private Drawer drawer;
	private Workspace myWorkspace;
	private Group lines = new Group();
	private SLogoController myController;
	private Dimension2D myDimensions;
	private SideBar mySidebar;
	private Editor myEditor;
	private Scene myScene;

	// TODO: move myTUrtles to relavant class (Workspace). Maybe drawer too? But
	// there is no functionality after moving it
	private Map<Integer, TurtleView> myTurtles = new HashMap<Integer, TurtleView>();
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";

	public SLogoView(Stage s) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
		myRoot = new GridPane();
		myStage = s;
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		myDimensions = new Dimension2D(bounds.getWidth(), bounds.getHeight()); //this may be incorrect

		
		myScene = setupScene(myStage, myRoot, myDimensions.getWidth(), myDimensions.getHeight());
		createNewController(this);
		lines.setManaged(false);

		myStage.setResizable(true);

		
		configureUI();
	}

	private void configureUI() {
		setGridPaneConstraints();

		createInitialTurtle();
		
		
		mySidebar = new SideBar(myTurtles, myController, myResources);
		setDefaultWorkspace();
		myRoot.add(configureTopMenu(), 0, 0, 2, 1);
		myRoot.add(configureTopRow(), 0, 1, 2, 1);
		myRoot.add(new CustomizationBar(myController, myTurtles, drawer, myWorkspace,
				myStage, myDimensions), 0, 2);
		myRoot.add(configureAddTurtlesButton(), 1, 2);
		myRoot.add(myWorkspace, 0, 3);
		myRoot.add(mySidebar, 1, 3, 1, 2);
		myEditor = new Editor(myController, mySidebar, myDimensions);
		myRoot.add(myEditor, 0, 4);
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
		List<Polyline> newlines = drawer.draw(myTurtles, instructionList, mySidebar);
		lines.getChildren().addAll(newlines);

		// return returnString;
	}

	public Turtle getTurtleInfo(int index) {
		ImageView temp = myTurtles.get(index);
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
				mySidebar.updateVariable(new VariableView(name, value));
			} else {
				// variables.get(name).setText(value);
			}
		}
	}

	private Scene setupScene(Stage stage, Parent root, double xSize, double ySize) {
		Scene scene = new Scene(root, xSize, ySize);
		stage.setTitle(myResources.getString("Title"));
		stage.setScene(scene);
		// scene.setOnKeyPressed(e -> handleKeyInput(e));
		stage.show();
		return scene;
	}

	public double towards(int id, double x, double y) {
		double angle = Math.toDegrees(Math.atan2(x, y));
		return setHeading(id, angle, false);
	}

	// for testing
	/*
	 * private void handleKeyInput(KeyEvent e) { KeyCode keyCode = e.getCode();
	 * if (keyCode == KeyCode.D) { ArrayList<TurtleCommand> instructions = new
	 * ArrayList<TurtleCommand>(); instructions.add(new TurtleCommand(0, new
	 * Polar(30, 0), true)); List<Polyline> newlines = drawer.draw(myTurtles,
	 * instructions, mySidebar); lines.getChildren().addAll(newlines); } else if
	 * (keyCode == KeyCode.W) { ArrayList<TurtleCommand> instructions = new
	 * ArrayList<TurtleCommand>(); instructions.add(new TurtleCommand(0, new
	 * Polar(0, 15), false)); List<Polyline> newlines = drawer.draw(myTurtles,
	 * instructions); lines.getChildren().addAll(newlines); } else if (keyCode
	 * == KeyCode.E) { System.out.print(towards(0, -10, 10)); } else if (keyCode
	 * == KeyCode.Q) { System.out.print(clearScreen(0)); } else if (keyCode ==
	 * KeyCode.A) { System.out.print(myTurtles.get(0).getTranslateX() + "," +
	 * myTurtles.get(0).getTranslateY()); } }
	 */

	private void displayPage(String loc) {
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load("file://" + System.getProperty("user.dir") + loc);

		Stage stage = new Stage();
		setupScene(stage, browser, 1000, 750);
	}

	private MenuBar configureTopMenu() {
		Menu file = new Menu("File");
		Menu info = new Menu("Info");
		MenuItem help = new MenuItem("Help");

		// perhaps change these expressions into lambdas?
		help.setOnAction(e -> displayPage("/src/resources/help.html"));
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(exit);
		info.getItems().addAll(help);

		exit.setOnAction(e -> Platform.exit());

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file, info);
		return menuBar;
	}

	// TODO: make into a tab pane
	private Text configureTopRow() {
		Text title = new Text("SLogo");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER); // why does this not work
		return title;
	}

	private void setGridPaneConstraints() {
		// menu bar
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(2);
		// tabs
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(7);
		// customization bar
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(4);
		// workspace
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(67);
		// editor
		RowConstraints row4 = new RowConstraints();
		row4.setPercentHeight(20);

		myRoot.getRowConstraints().add(row0);
		myRoot.getRowConstraints().add(row1);
		myRoot.getRowConstraints().add(row2);
		myRoot.getRowConstraints().add(row3);
		myRoot.getRowConstraints().add(row4);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(75);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(25);
		myRoot.getColumnConstraints().add(col1);
		myRoot.getColumnConstraints().add(col2);
	}

	private void setDefaultWorkspace() {
		myWorkspace = new Workspace(myTurtles, lines, myDimensions, mySidebar);
		drawer = new Drawer(myWorkspace.getGridWidth(), myWorkspace.getGridHeight());
	}

	// ---------

	// methods for the backend to call. TODO: organize better

	public double setXY(int id, double x, double y) {
		double xy = myTurtles.get(id).setXY(x, y);
		mySidebar.updateTurtleProperties(id); // are these methods duplicated
												// code since they are all the
												// same thing with just one
												// added line in them?
		return xy;
	}

	public double setHeading(int id, double angle, boolean relative) {
		double heading;
		if (relative) {
			heading = myTurtles.get(id).setRelativeHeading(angle);

		} else {
			heading = myTurtles.get(id).setAbsoluteHeading(angle);
		}
		mySidebar.updateTurtleProperties(id);
		return heading;
	}

	public void setPenUp(int id, boolean setPen) {
		/*
		 * if (setPen){ myTurtles.get(id).setPenUp(true); //return 0; }
		 * myTurtles.get(id).setPenUp(false); //return 1;
		 */

		myTurtles.get(id).setPenUp(setPen);
		mySidebar.updateTurtleProperties(id);
	}

	public double getPenDown(int id) {
		if (myTurtles.get(id).getPenUp())
			return 0;
		return 1;
	}

	public double isShowing(int id) {
		if (myTurtles.get(id).isVisible())
			return 1;
		return 0;
	}

	public void showTurtle(int id, boolean show) {
		myTurtles.get(id).showTurtle(show);
		mySidebar.updateTurtleProperties(id);
	}

	public double getHeading(int id) {
		return myTurtles.get(id).getRotate();
	}

	// these definitely methods should not be in SLogoView;
	public double getXCor(int id) {
		return myTurtles.get(0).getTranslateX();
	}

	public double getYCor(int id) {
		return myTurtles.get(0).getYCoord();
	}

	public void openDialog(String message) {
		Stage stage = new Stage();
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		Text text = new Text(message);
		root.getChildren().add(text);

		
		//TODO: my dimensions does not actually work properly. it is set to the stage dimensions but is not updated in real-time
		Scene scene = new Scene(root, myDimensions.getWidth() / 4,
				myDimensions.getHeight() / 6);

		stage.setTitle("Error");
		stage.setScene(scene);
		stage.show();
	}

	public void updateVariable(VariableView variable) {
		mySidebar.updateVariable(variable);
	}

	// TODO THIS
	// this should be in the workspace, but it would have to be called twice in
	// this class and in that class
	// but then again maybe not because the group is added on in this class
	public double clearScreen(int id) {
		// these group of lines somehow need to be connected with the turtle
		lines.getChildren().clear();
		myTurtles.get(id).setAbsoluteHeading(0);
		// update with clearscreen
		double dist = myTurtles.get(id).setXY(0, 0);
		mySidebar.updateTurtleProperties(id); // dist is used twice so that
												// turtle properties can be
												// updated before returning the
												// value
		return dist;
	}

	@Override
	public void update(Observable o, Object arg) {
		ExecutionEnvironment env = (ExecutionEnvironment) o;
		for (String s : env.getVariableMap().keySet()) {
			double value = env.getVariableMap().get(s).execute();
			mySidebar.updateVariable(new VariableView(s, value));
		}

		for (String s : env.getUserCommandMap().keySet()) {
			updateCommand(s);
		}
	}

	public void createNewController(SLogoView view) {
		myController = new SLogoController(view);
	}

	private Button configureAddTurtlesButton() {
		// Add turtle button
		Button newTurtleButton = new Button("Add a turtle");
		newTurtleButton.setStyle("-fx-base: #b6e7c9;");
		newTurtleButton.setAlignment(Pos.CENTER_RIGHT);
		newTurtleButton.setOnAction(e -> myWorkspace.addTurtle());
		GridPane.setHalignment(newTurtleButton, HPos.CENTER);
		/*
		 * TextField textbox = new TextField("" + turtleList.size());
		 * textbox.setEditable(false);
		 * textbox.setPrefWidth(dimensions.getWidth()/100);
		 */
		// getChildren().addAll(newTurtleButton);
		return newTurtleButton;
	}

	private void updateCommand(String s) {
		mySidebar.updateCommand(s);
	}
	
	private void createInitialTurtle(){
		TurtleView turtle = new TurtleView(0, new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(0, turtle);
	}
}
