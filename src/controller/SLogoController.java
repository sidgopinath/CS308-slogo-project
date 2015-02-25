package controller;

import java.lang.reflect.InvocationTargetException;

import view.SLogoView;
import javafx.stage.Stage;
import model.Parser;


public class SLogoController {

	private Parser myParser;
	
	/**
	 * Called by Main to initialize Controller
	 * Initializes model and view
	 * @param height
	 * @param width
	 * @param stage
	 */
	public SLogoController(SLogoView view, Stage stage) {
		myParser = new Parser(view);
	}
	
	/**
	 * Will be called from view
	 * Calls parser to parse string
	 * @param input
	 */
	public void parseInput(String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		myParser.parseAndExecute(input);
	}
	
	/**
	 * After input has been received and parsed, this is called
	 * Passes input to View
	 * @param input
	 */
	public void passInputToView(Object input){
		
	}
}