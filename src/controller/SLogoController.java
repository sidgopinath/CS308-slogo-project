package controller;

import java.lang.reflect.InvocationTargetException;

import view.SLogoView;
import javafx.stage.Stage;
import model.ModelException;
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
	public SLogoController(SLogoView view) {
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
	 * Called from view to change the input language
	 * @param language
	 */
	public void setLanguage(String language){
		myParser.setLanguage(language);
	}
}