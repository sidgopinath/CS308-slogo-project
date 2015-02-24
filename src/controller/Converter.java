package controller;

public class Converter {
	
	/**
	 * Takes in polar coordinates and converts them into Cartesian coordinates
	 * @param degrees 
	 * @param r (distance)
	 * @return a double array containing [x-Cartesian coordinate, y-Cartesian coordinate]
	 */
	public double[] convertPolartoCartesian(double degrees, double r){
		double[] cartesianCoords = new double[2];
		
		cartesianCoords[0] = r * Math.cos(Math.toRadians(degrees));
		cartesianCoords[1] = r * Math.sin(Math.toRadians(degrees));
		
		return cartesianCoords;
	}
	
	/**
	 * Takes in Cartesian coordinates and converts them into polar coordinates
	 * @param x-coordinate on Cartesian plane
	 * @param y-coordinate on Cartesian plane
	 * @return a double array containing [theta in degrees, r as a distance]
	 */
	public double[] convertCartesiantoPolar(double x, double y){
		double[] polarCoords = new double[2];
		
		polarCoords[0] = Math.toDegrees(Math.atan(y/x));
		polarCoords[1] = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 1/2);
		
		return polarCoords;
	}

}
