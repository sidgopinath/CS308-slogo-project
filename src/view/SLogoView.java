//TODO: use resource file instead of strings. Pass resource file as parameters to other classes

package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Polar;
import model.turtle.Turtle;
import model.turtle.TurtleCommand;
import resources.Strings;
import controller.SLogoController;

public class SLogoView {
	private Stage myStage;
	private GridPane myRoot;
	private ResourceBundle myResources;
	private Map<String, Node> variables;
	private double myWidth;
	private double myHeight;
	private Drawer drawer = new Drawer();
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
		// create root node
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
        originX=myTurtles.get(0).getLayoutX();
        originY=myTurtles.get(0).getLayoutY();
	}

	private void configureUI() {
		// myRoot.setAlignment(Pos.CENTER);
		// myRoot.add(makeSimulationButtons(), 1, 2); // new root from a class?
		// or just a new mehtod?
		// use a new class for things like the text box since pressing update
		// will have to update naw and then the view will have to be updated as
		// well

		Text title = new Text("SLogo");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER); // why does this not work
		myRoot.add(title, 0, 0, 2, 1);

		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(5);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(75);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(20);
		myRoot.getRowConstraints().add(row1);
		myRoot.getRowConstraints().add(row2);
		myRoot.getRowConstraints().add(row3);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(70);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(30);

		// add lines to a group
		TurtleView turtle = new TurtleView(new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(0, turtle);
		myWorkspace = new Workspace(myTurtles, lines);
		// LILA
		myRoot.add(myWorkspace, 0, 1);
		myRoot.getColumnConstraints().add(col1);
		myRoot.getColumnConstraints().add(col2);
		mySidebar = new SideBar(myTurtles, myStage, myWorkspace, drawer);
		myRoot.add(mySidebar, 1, 1, 1, 2); // col,
		myEditor = new Editor(myController, mySidebar); // row,
		// colspan,
		// rowspan
		myRoot.add(myEditor, 0, 2);
	}

	// bottom row
	/*
	 * private HBox makeEditor() { HBox bottomRow = new HBox();
	 * bottomRow.setPadding(new Insets(15)); bottomRow.setSpacing(15);
	 * 
	 * // text area TextArea textEditor = new TextArea();
	 * textEditor.setMaxHeight(Double.MAX_VALUE); textEditor.setPrefSize(750,
	 * 120); // this should be dynamically // alterable?
	 * bottomRow.getChildren().add(textEditor);
	 * 
	 * // run button Button runButton = new Button("Run"); //
	 * runButton.setPrefSize(100, 120); runButton.setMaxWidth(Double.MAX_VALUE);
	 * runButton.setMaxHeight(Double.MAX_VALUE); // runButton.setPadding(new
	 * Insets(0,0,0,3)); bottomRow.getChildren().add(runButton);
	 * 
	 * return bottomRow; }
	 */

	public String updateWorkspace(List<TurtleCommand> instructionList) {
		String returnString = null;
		for (TurtleCommand instruction : instructionList) {
			returnString += updateFromInstruction(instruction) + "\n";
		}
        List<Polyline> newlines=drawer.draw(myTurtles, instructionList);
        lines.getChildren().addAll(newlines);
		// return value of command or null if there is no return value
		return returnString;
	}

	public void setXY(double x, double y) {
	}

	// make update from a single command
	private String updateFromInstruction(TurtleCommand instruction) {
		return "return value";
	}

	public Turtle getTurtleInfo(int index) {
		ImageView temp = myTurtles.get(index);
		return new Turtle(temp.getX(), temp.getY(), temp.getRotate());

	}

	public void updateVariables(Map<String, Double> variableUpdates) {
		Iterator<Entry<String, Double>> it = variableUpdates.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Double> variable = it.next();
			String name = variable.getKey();
			double value = variable.getValue();
			if (variables.get(name) == null) {
				// create the UI element to hold this variable
				// then add this element to variables (variables.put(name,UI
				// node));
				// then add this element to the grid
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
		// what happens if you set multiple scenes?
		stage.show();
	}
	private void handleKeyInput (KeyEvent e) {
	    KeyCode keyCode = e.getCode();
	    if(keyCode == KeyCode.D){
            ArrayList<TurtleCommand> instructions = new ArrayList<TurtleCommand>();
            instructions.add(new TurtleCommand(0,new Polar(30,0),false,false));
            List<Polyline> newlines=drawer.draw(myTurtles, instructions);
            lines.getChildren().addAll(newlines);
	    }else if(keyCode == KeyCode.W){
            ArrayList<TurtleCommand> instructions = new ArrayList<TurtleCommand>();
            instructions.add(new TurtleCommand(0,new Polar(0,10),false,false));
            List<Polyline> newlines=drawer.draw(myTurtles, instructions);
            lines.getChildren().addAll(newlines);
	    }else if(keyCode == KeyCode.E){
	        setXY(0,0,0);
        }else if(keyCode == KeyCode.Q){
            setHeading(0,90);
        }
    }
    public void setXY(int id, double x,double y){
        TurtleView turtle=myTurtles.get(id);
        myTurtles.get(id).setXY(originX+x-turtle.getLayoutX(),originY+y-turtle.getLayoutY());
    }
    public void setHeading(int id, double angle){
        myTurtles.get(id).setHeading(angle);
    }
	//make update from a single command
	
	//UPON BUTTON CLICK:
	
	//  File newFile = displayFileChooser();
	
	/* colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                text.setFill(colorPicker.getValue());               
            }
        });
        
        
        button.setOnAction(new EventHandler<ActionEvent>() {

    @Override
    public void handle(ActionEvent event) {
        String text = textField.getText();                              
    }
});*/
	
	//consider using labels instead of text?
	
	/*public void changeColor(Color c) {
        System.out.println("color" + c.toString());


>>>>>>> 362817cc7ce9eda7709752b2584fb82e89ec7be4

	/*
	 * colorPicker.setOnAction(new EventHandler() { public void handle(Event t)
	 * { text.setFill(colorPicker.getValue()); } });
	 * 
	 * 
	 * button.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { String text =
	 * textField.getText(); } });
	 */

	// consider using labels instead of text?

	/*
	 * public void changeColor(Color c) { System.out.println("color" +
	 * c.toString());
	 * 
	 * 
	 * 
	 * }
	 */

}
