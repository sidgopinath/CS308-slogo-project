package controller;


import javafx.application.Application;
import javafx.stage.Stage;
import view.SLogoView;

public class Main extends Application {

	private SLogoController sLogoController;
	private SLogoView myView;

	@Override
	public void start(Stage stage) throws Exception {
		myView = new SLogoView(stage);
		sLogoController = new SLogoController(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}	
}