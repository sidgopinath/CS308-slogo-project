//Turtle view class only accessible to the View

package view;

import controller.Converter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleView extends ImageView{
	
	private int myHeading;
//	private int myID;

	public TurtleView(Image img){
		super(img);
        setFitWidth(30);
        setFitHeight(30);
        myHeading = 0;
	}
	
	public void move(double moveX, double moveY){
        setTranslateX(moveX);
        setTranslateY(moveY);
	}

    public void turn (double angle) {
        setRotate(angle);
    }
	
}
