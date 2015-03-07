package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;

/**
 * Main
 * Creates the view and then lets the program run
 * The view creates the parser, and they hold an instance of each other
 * @author Sid
 *
 */

public class Main extends Application {

	private MainView myView;

	@Override
	public void start(Stage stage) throws Exception {
		myView = new MainView(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}	
}