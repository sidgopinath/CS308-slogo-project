package model;

public class Polar {
    public double angle;
    public double distance;
    
    public Polar(double angle,double distance){
        this.angle=angle;
        this.distance=distance;
    }
    public Polar(double[] coordinates){
    	this.angle = Math.atan2(coordinates[1], coordinates[0]);
    	this.distance=Math.sqrt(Math.pow(coordinates[0],2)+Math.pow(coordinates[1],2));
    }
}
