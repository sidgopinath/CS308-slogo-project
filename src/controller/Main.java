package controller;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private SLogoController sLogoController;
	public static final int HEIGHT = 600;
	public static final int WIDTH = 600;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("SLogoController");
		sLogoController = new SLogoController(HEIGHT, WIDTH, stage);
		stage.setScene(sLogoController.getScene());
		stage.setResizable(false);
		stage.show();
		Timeline animationTimeline = new Timeline(); //could be moved to controller?
		int frameRate = 60;
		sLogoController.setFrameRate(frameRate);
		sLogoController.manageTimeline(animationTimeline, frameRate); //could be moved to controller
	}

	public static void main(String[] args) {
		launch(args);
	}	
}
