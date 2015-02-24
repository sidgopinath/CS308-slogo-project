package controller;

import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Parser;

/**
 * SOME THOUGHTS:
 * Where does primary update/loop method go? I have always had it in controller, not sure if it is okay in Main
 * I really am hesitant to put initialization of Model/View in Main
 * What if something changes that means we have to add more code to Main? 
 * I have yet to see a long Main method in any code he has given us, so I'm not sure
 * How do we feel about how I described the update method below? I think we can keep the controller light on code
 * @author Sid
 *
 */

public class SLogoController {

	private Stage myStage; //primary stage. could be passed to view if necessary
	private Scene myScene; //main Scene. held in controller, updated in View, and called by Main to display
	private Group myGroup; //holds all JavaFX Objects
	private ResourceBundle myLanguageResourceBundle; //language resources
	private Timeline myTimeline; //animation timeline
	private Integer myFrameRate; //frame rate for animation timeline, allows it to be changed
	private Parser myParser;
	
	/**
	 * Called by Main to initialize Controller
	 * Sets up Stage, Scene and Resources
	 * Initializes model and view (may need parameters)
	 * Displays screen, which starts timeline and begins loop between mvc
	 * @param height
	 * @param width
	 * @param stage
	 */
	public SLogoController(Stage stage) {
		myParser = new Parser();
		myStage = stage;
		initializeSLogoModel();
		initializeSLogoView();
		myLanguageResourceBundle = ResourceBundle.getBundle("resources/languages/English");
	}
	
	/**
	 * Method to initialize view
	 * Will likely take in parameters
	 * Perhaps takes instances of Model/Controller
	 * After initialized, this is not called again
	 */
	private void initializeSLogoView(){
		
	}
	
	/**
	 * Method to initialize model
	 * Will likely take in parameter
	 * Perhaps takes instances of View/Controller
	 * After initialized, not called again
	 */
	private void initializeSLogoModel(){
		
	}
	
	/**
	 * Will be called from view when user puts in input
	 * Calls parser and then a get method to get info about Turtle
	 * @param input
	 */
	public void parseInput(String input){
		
	}
	
	/**
	 * After input has been received and parsed, this is called
	 * Passes an Object (TurtleInfo?) to view, which then knows how to interpret that
	 * @param input
	 */
	public void passInputToView(Object input){
		
	}
	

	/**
	 * Returns Scene for Main
	 * Displays current Scene
	 * @return
	 */
	public Scene getScene() {
		return myScene;
	}
}