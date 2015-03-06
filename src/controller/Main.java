package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;
import view.SLogoView;

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