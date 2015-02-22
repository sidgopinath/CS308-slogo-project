package controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	SLogoController myController;
	
	@Override
	public void start(Stage s) throws Exception {
		myController = new SLogoController(s);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}