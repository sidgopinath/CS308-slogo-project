//TODO: use resource file instead of strings. Pass resource file as parameters to other classes

package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.stage.Stage;
import model.ExecutionEnvironment;
import model.Polar;
import model.turtle.Turtle;
import model.turtle.TurtleCommand;
import controller.SLogoController;

public class SLogoView implements Observer{
	private Stage myStage;
	private GridPane myRoot;
	protected ResourceBundle myResources;
	private Map<String, Node> variables;
	private double myWidth;
	private double myHeight;
	private Drawer drawer;
	private Workspace myWorkspace;
	private Group lines = new Group();
	private SLogoController myController;

	private SideBar mySidebar;
	private Editor myEditor;
	private Map<Integer, TurtleView> myTurtles = new HashMap<Integer, TurtleView>();
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	private double originX;
	private double originY;

	public SLogoView(Stage s) {
		myController = new SLogoController(this, s);
		myStage = s;
		myRoot = new GridPane();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
		lines.setManaged(false);
		configureUI();

		// TODO: dynamically set screen size using percents:
		/*
		 * Screen screen = Screen.getPrimary(); Rectangle2D bounds =
		 * screen.getVisualBounds(); myWidth = bounds.getWidth(); myHeight =
		 * bounds.getHeight();
		 */
		setupScene(myStage, myRoot, 1200, 750);
		originX = myTurtles.get(0).getLayoutX();
		originY = myTurtles.get(0).getLayoutY();
	}

	private void configureUI() {
		setGridPaneConstraints();
		setDefaultWorkspace();
		myRoot.add(configureTopMenu(), 0, 0, 2, 1);
		myRoot.add(configureTopRow(), 0, 1, 2, 1);
		myRoot.add(myWorkspace, 0, 2);
		mySidebar = new SideBar(myTurtles, myStage, myWorkspace, drawer, myController);
		myRoot.add(mySidebar, 1, 2, 1, 2); // col,
		myEditor = new Editor(myController, mySidebar); // row,
		myRoot.add(myEditor, 0, 3);
	}

	// does this do anything?
	// do we need to return anything
	public void updateWorkspace(List<TurtleCommand> instructionList) {
		// String returnString = null;
		/*
		 * for (TurtleCommand instruction : instructionList) { returnString +=
		 * updateFromInstruction(instruction) + "\n"; }
		 */
		List<Polyline> newlines = drawer.draw(myTurtles, instructionList);
		lines.getChildren().addAll(newlines);
		// return returnString;
	}

	// make update from a single command
	/*
	 * private String updateFromInstruction(TurtleCommand instruction) { return
	 * "return value"; }
	 */

	public Turtle getTurtleInfo(int index) {
		ImageView temp = myTurtles.get(index);
		return new Turtle(temp.getX(), temp.getY(), temp.getRotate());

	}

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
				mySidebar.updateVariable(new Variable(name, value));
			} else {
				// variables.get(name).setText(value);
			}
		}
	}

	private void setupScene(Stage stage, Parent root, double xSize, double ySize) {
		Scene scene = new Scene(root, xSize, ySize);
		stage.setTitle(myResources.getString("Title"));
		stage.setScene(scene);
		scene.setOnKeyPressed(e -> handleKeyInput(e));
		stage.show();
	}

	public double towards(int id, double x, double y) {

		// TODO
		return 10;
	}

	// for testing
	private void handleKeyInput(KeyEvent e) {
		KeyCode keyCode = e.getCode();
	/*	if (keyCode == KeyCode.D) {
			ArrayList<TurtleCommand> instructions = new ArrayList<TurtleCommand>();
			instructions.add(new TurtleCommand(0, new Polar(30, 0), true));
			List<Polyline> newlines = drawer.draw(myTurtles, instructions);
			lines.getChildren().addAll(newlines);
		} else if (keyCode == KeyCode.W) {
			ArrayList<TurtleCommand> instructions = new ArrayList<TurtleCommand>();
			instructions.add(new TurtleCommand(0, new Polar(0, 15), false));
			List<Polyline> newlines = drawer.draw(myTurtles, instructions);
			lines.getChildren().addAll(newlines);
		} else if (keyCode == KeyCode.E) {
			setXY(0, 0, 0);
		} else if (keyCode == KeyCode.Q) {
			setHeading(0, 90);
		} else if (keyCode == KeyCode.A) {
			System.out.print(myTurtles.get(0).getTranslateX() + ","
					+ myTurtles.get(0).getTranslateY());
		}*/
	}

	public double setXY(int id, double x, double y) {
		return myTurtles.get(id).setXY(x, y);
	}

	public void setHeading(int id, double angle) {
		myTurtles.get(id).setAbsoluteHeading(angle);
	}

	// public double towards(int id, double x, double y){
	//
	//
	// setHeading()
	// }

	public void setHeading(int id, double angle, boolean relative) {
		if (relative)
			myTurtles.get(id).setRelativeHeading(angle);
		myTurtles.get(id).setAbsoluteHeading(angle);
	}

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

		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Platform.exit();
			}
		});

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file, info);
		return menuBar;
	}

	private Text configureTopRow() {
		Text title = new Text("SLogo");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER); // why does this not work
		return title;
	}

	private void setGridPaneConstraints() {
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(2);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(5);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(73);
		RowConstraints row4 = new RowConstraints();
		row4.setPercentHeight(20);
		myRoot.getRowConstraints().add(row1);
		myRoot.getRowConstraints().add(row2);
		myRoot.getRowConstraints().add(row3);
		myRoot.getRowConstraints().add(row4);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(70);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(30);
		myRoot.getColumnConstraints().add(col1);
		myRoot.getColumnConstraints().add(col2);
	}

	private void setDefaultWorkspace() {
		TurtleView turtle = new TurtleView(new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(0, turtle);
		myWorkspace = new Workspace(myTurtles, lines);
		drawer = new Drawer(myWorkspace.getGridWidth(), myWorkspace.getGridHeight());
	}

	// method implementations. Is this bad design to have so many for each?

	// DESIGN ISSUES:

	// 1. consider moving this to the controller and giving the list of turtles
	// to
	// the controller?

	// 2. or these can be put back into the turtlecommand object and call
	// setPenUp and showTurtle every time. it will not be very efficient, but
	// may be better in design

	// here our SLogoView is acting like a controller
	// these are all methods that could technically be contained within
	// updateworkspace method....or at least conceptually are related enough to
	// be in there
	public void setPenUp(int id, boolean setPen) {
		/*
		 * if (setPen){ myTurtles.get(id).setPenUp(true); //return 0; }
		 * myTurtles.get(id).setPenUp(false); //return 1;
		 */
		myTurtles.get(id).setPenUp(setPen);
	}

	public double getPenDown(int id) {
		if (myTurtles.get(id).getPenUp())
			return 0;
		return 1;
	}

	public double isShowing(int id) {
		if (myTurtles.get(id).isShowing())
			return 1;
		return 0;
	}

	public void showTurtle(int id, boolean show) {
		myTurtles.get(id).showTurtle(show);
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

		Scene scene = new Scene(root, 300, 100);

		stage.setTitle("Error");
		stage.setScene(scene);
		stage.show();
	}

	public void updateVariable(Variable variable) {
		mySidebar.updateVariable(variable);
	}

	// TODO THIS
	// this should be in the workspace, but it would have to be called twice in
	// this class and in that class
	// but then again maybe not because the group is added on in this class
	public double clearScreen(int id) {
		// these group of lines somehow need to be connected with the turtle
		lines.getChildren().clear();
		myWorkspace.getChildren().remove(myTurtles.get(id)); //clear only the current turtle

		return 10; // TODO: calculatedistance()
	}

	@Override
	public void update(Observable o, Object arg) {
		ExecutionEnvironment env = (ExecutionEnvironment) o;
		for (String s: env.getVariableMap().keySet()){
			double value =  env.getVariableMap().get(s).execute();
			System.out.println("s" + s);
			System.out.println("value" + value);
			updateVariable(new Variable(s, value));
			System.out.println("==============updated==========");
			
		}
			
		
	}

}
