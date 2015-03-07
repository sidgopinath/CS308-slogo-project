//TODO: use resource file instead of strings. Pass resource file as parameters to other classes

//TODO for backend: in order to change the state of the turtle, commands must always be passed back into the parser so have one global update method call theupdateTurtlePropperties method. 
//TODO: variableschange when a user-defined command is put even though thse variables should not really be set directly. or maybe it should?

package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import model.Parser;
import model.TurtleCommand;

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
		newTurtleButton.setAlignment(Pos.CENTER_RIGHT);
		newTurtleButton.setOnAction(e -> myWorkspace.addTurtle());
		GridPane.setHalignment(newTurtleButton, HPos.CENTER);
		return newTurtleButton;
	}

	public GridPane configureUI() {
		GridPane root = new GridPane();
		setGridPaneConstraints(root);
		Map<Integer, TurtleView> myTurtles = new HashMap<Integer, TurtleView>(); // TODO:																				// move

		mySidebar = new SideBar(myWorkspace, myParser);
		myWorkspace = new Workspace(myDimensions, mySidebar);
		drawer = new Drawer(myWorkspace);
		System.out.println("created drawer =====================================");
		root.add(new CustomizationBar(myParser, myTurtles, drawer, myWorkspace, myStage,
				myDimensions), 0, 0);
		root.add(configureAddTurtlesButton(), 1, 0);
		root.add(myWorkspace, 0, 1);
		root.add(mySidebar, 1, 1, 1, 2);
		
		//TODO: not a UI thing, make this another method that is called from mainview
		myParser = createNewParser(this);

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
		System.out.println("get drawer");
		return drawer;
	}


}
