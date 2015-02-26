//TODO: use resource file instead of strings. Pass resource file as parameters to other classes

package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Polar;
import model.turtle.Turtle;
import model.turtle.TurtleCommand;
import controller.SLogoController;

public class SLogoView {
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
		setGridPaneConstraints();
		setDefaultWorkspace();	
		myRoot.add(configureTopMenu(),0,0,2,1);
		myRoot.add(configureTopRow(), 0, 1, 2, 1);
		myRoot.add(myWorkspace, 0, 2);
		mySidebar = new SideBar(myTurtles, myStage, myWorkspace, drawer, myController);
		myRoot.add(mySidebar, 1, 2, 1, 2); // col,
		myEditor = new Editor(myController, mySidebar); // row,
		myRoot.add(myEditor, 0, 3);
	}

	
	
	//does this do anything?
	public String updateWorkspace(List<TurtleCommand> instructionList) {
		String returnString = null;
		for (TurtleCommand instruction : instructionList) {
			returnString += updateFromInstruction(instruction) + "\n";
		}
        List<Polyline> newlines=drawer.draw(myTurtles, instructionList);
        lines.getChildren().addAll(newlines);
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
				
				//TODO:
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
		stage.show();
	}
	
	//for testing
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
    
    private void displayPage(String loc){
    	
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load("file://" + System.getProperty("user.dir") + loc);

		Stage stage = new Stage();
		setupScene(stage, browser, 1000, 750);        	


    }
    
   
    
    private MenuBar configureTopMenu(){
		Menu file = new Menu("File"); 	
		Menu info = new Menu("Info"); 	
		MenuItem help = new MenuItem("Help");
		
		//perhaps change these expressions into lambdas?
		help.setOnAction(e -> displayPage("/src/resources/help.html"));		
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(exit);
		info.getItems().addAll(help);
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent t) {
	                Platform.exit();
	            }
	        });        
		 
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file, info);
		return menuBar;
    }

    private Text configureTopRow(){
    	Text title = new Text("SLogo");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER); // why does this not work
    	return title;
	}
    
    private void setGridPaneConstraints(){
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
    
    private void setDefaultWorkspace(){
    	TurtleView turtle = new TurtleView(new Image(Strings.DEFAULT_TURTLE_IMG));
		myTurtles.put(0, turtle);
		myWorkspace = new Workspace(myTurtles, lines);
		drawer = new Drawer(myWorkspace.getGridWidth(), myWorkspace.getGridHeight());
    }
    
    //consider moving this to the controller and giving the list of turtles to the controller?
    public double setPenUp(int id, boolean setPen){
    	if (setPen){
     	   myTurtles.get(id).setPenUp(true);
     	   return 0;
    	}
    	myTurtles.get(id).setPenUp(false);
    	return 1;
      
    }
}
