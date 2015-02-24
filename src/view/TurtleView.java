//Turtle view class only accessible to the View

package view;


import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public class TurtleView extends ImageView{
	
	private int myHeading;
	private int myXLocation;
	private int myYLocation;
	private int myID;

	public TurtleView(Image img){
		super (img);
        setFitWidth(100);
        setFitHeight(100);
	}
	
	public void move(){
		//convertPolartoCartesian();
	}
	
	
}
