package model;

public class Polar {
    
	/**
	 * Consider refactoring this
	 * Getter methods instead of public variables?
	 */
	private double myAngle;
    private double myDistance;
    
    /**
     * Takes in angle and distance and creates Polar object
     * @param angle
     * @param distance
     */
    public Polar(double angle,double distance){
        myAngle=angle;
        myDistance=distance;
    }
    
    /**
     * Converts coordinate input to polar coordinates
     * @param coordinates
     */
    public Polar(double[] coordinates){
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
