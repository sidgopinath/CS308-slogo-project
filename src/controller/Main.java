package controller;

import view.SLogoView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	SLogoView myView;
	
	@Override
	public void start(Stage s) throws Exception {
		myView = new SLogoView(s);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}