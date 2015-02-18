package controller;

import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	
	/**
	 * Called by Main to initialize Controller
	 * Sets up Stage, Scene and Resources
	 * Initializes model and view (may need parameters)
	 * Displays screen, which starts timeline and begins loop between mvc
	 * @param height
	 * @param width
	 * @param stage
	 */
	public SLogoController(int height, int width, Stage stage) {
		myStage = stage;
		initializeSLogoModel();
		initializeSLogoView();
		displaySLogoScreen(height, width);
		myScene = new Scene(myGroup, width, height, Color.WHITE);
		myLanguageResourceBundle = ResourceBundle.getBundle("resources/languages/English");
	}

	/**
	 * Method to display the SLogoView (screen)
	 * Takes in height and width from Main and actually dispalys screen
	 * @param height
	 * @param width
	 */
	private void displaySLogoScreen(int height, int width) {
		
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
	 * The method that is called every frame
	 * Likely will call parseInput and then passInputToView
	 * Main loop of program contained here
	 */
	private void update(){
		
	}

	/**
	 * Returns Scene for Main
	 * Displays current Scene
	 * @return
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * May not be necessary, but worth having
	 * Can be used to change frameRate aka speed-up/slow down simulation
	 * @param frameRate
	 */
	public void setFrameRate(int frameRate) {
		myFrameRate = frameRate;
		
	}

	/**
	 * Initializes timeline and keeps timeline moving
	 * In controller so timeline can be adjusted on the fly
	 * @param animationTimeline
	 * @param frameRate
	 */
	public void manageTimeline(Timeline animationTimeline, int frameRate) {
		myTimeline = animationTimeline;
		KeyFrame frame = getKeyFrame(frameRate);
		myTimeline.setCycleCount(Timeline.INDEFINITE);
		myTimeline.getKeyFrames().add(frame);
		myTimeline.play();	
		
	}

	/**
	 * Gets frame rate to set up timeline
	 * @param frameRate
	 * @return
	 */
	private KeyFrame getKeyFrame(int frameRate) {
		return new KeyFrame(Duration.millis(1000 /frameRate), e -> update());
	}

	

}
