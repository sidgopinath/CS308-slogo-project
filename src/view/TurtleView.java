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
		super(img);
        setFitWidth(300);
        setFitHeight(300);
        myXLocation = 0;
        myYLocation = 0;
	}
	
	public void move(){
		//convertPolartoCartesian();
		
	}
	
	private int[] convertPolartoCartesian(){
		
	}

	
	
	
}
