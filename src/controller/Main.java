package controller;


import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import view.SLogoView;

public class Main extends Application {

	private SLogoController sLogoController;
	private SLogoView myView;
	//public static final int HEIGHT = 600;
	//public static final int WIDTH = 600;

	@Override
	public void start(Stage stage) throws Exception {
		myView = new SLogoView(stage);
		//stage.setTitle("SLogoController");
		sLogoController = new SLogoController(stage);
		//stage.setScene(sLogoController.getScene());
		//stage.setResizable(false);
		//stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}	
}