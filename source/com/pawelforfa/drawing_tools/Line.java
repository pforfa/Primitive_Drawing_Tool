package com.pawelforfa.drawing_tools;

public class Line extends Shape {

	private int pointX1;
	private int pointY1;
	
	private int pointX2;
	private int pointY2;
	
	public Line(int x1, int y1, int x2, int y2){
		pointX1 = x1;
		pointY1 = y1;
		pointX2 = x2;
		pointY2 = y2;
	}
	
	/**Retrieves the coordinates of the line
	 * 
	 * @return - an int array of coordinates in the respective order: x1, y1, x2, y2
	 */
	public int[] retrieveCoordinates(){
		return new int[]{pointX1, pointY1, pointX2, pointY2};
	}
	
}
