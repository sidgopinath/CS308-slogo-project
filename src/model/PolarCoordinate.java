package model;

/**
 * A polar object stores an angle and a distance, like polar coordinates
 * This is used by the view to make commands easier for them to implement
 * @author Mengchao, Greg, Sid
 *
 */

public class PolarCoordinate {
    
	private double myAngle;
    private double myDistance;
    
    /**
     * Takes in angle and distance and creates Polar object
     * @param angle
     * @param distance
     */
    public PolarCoordinate(double angle,double distance){
        myAngle=angle;
        myDistance=distance;
    }
    
    /**
     * Converts coordinate input to polar coordinates
     * @param coordinates
     */
    public PolarCoordinate(double[] coordinates){
    	myAngle = Math.atan2(coordinates[1], coordinates[0]);
    	myDistance=Math.sqrt(Math.pow(coordinates[0],2)+Math.pow(coordinates[1],2));
    }
    
    public double getAngle(){
    	return myAngle;
    }
    
    public double getDistance(){
    	return myDistance;
    }
}