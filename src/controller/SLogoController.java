package controller;

import javafx.stage.Stage;

import model.Parser;
import view.SLogoView;

;

public class SLogoController {

	private Parser myParser;
	private SLogoView myView;

	public SLogoController(Stage s) {
		myParser = new Parser();
		
		myView = new SLogoView(s);
		//MainView?
	}

}
