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
        setFitWidth(20);
        setFitHeight(20);
	}
	
	public void move(){
		//convertPolartoCartesian();
	}
	
	public int[] convertPolartoCartesian(){
		
	}

	
	
}
