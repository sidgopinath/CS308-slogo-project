package controller;

import java.lang.reflect.InvocationTargetException;

import model.Parser;
import view.SLogoView;


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
	public void parseInput(String input) {
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