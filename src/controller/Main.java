package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.SLogoView;

public class Main extends Application {

	private SLogoView myView;

	@Override
	public void start(Stage stage) throws Exception {
		myView = new SLogoView(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}	
}