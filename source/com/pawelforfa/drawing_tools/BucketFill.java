package com.pawelforfa.drawing_tools;

import java.util.HashSet;

public abstract class BucketFill {

	/* 
	 * Valid test colors that can be used on the canvas. 
	 * Implementation of canvas will be changed in the future to allow more color values.
	 */
	static{
		HashSet<String> validColor = new HashSet<>();
		validColor.add("b");
		validColor.add("o");
		validColor.add("r");
		validColor.add("y");
		validColor.add("m");

	}
	/**
	 * Implementation of the fill method. The bucket fill method takes the x and y coordinates
	 * as parameters and fills a bounded region within the two coordinates on the specified canvas
	 * 
	 * @param x - x coordinate on the canvas
	 * @param y - y coordinate on the canvas
	 * @param canvas - an instance of the Canvas class on which the fill method will be called.
	 */
	public static void fillColor(int x, int y, char c, Canvas canvas) {		
		// Implementation of fillColor for DrawingCoard subclass
		if(canvas instanceof DrawingBoard){	
			/* Check if the x,y coordinates are within the
			 * valid boundaries of the current canvas
			 */
			if(checkCoordinateBoundaries(x, y, canvas)){
				char[][] currentCanvas = ((DrawingBoard)canvas).getCanvasBoard();

				// Checks that if the current point is an 'x', then return and don't fill. 
				// This case occurs when the user specifies a point on which there is already a shape border.
				if(currentCanvas[y][x] == 'x'){
					return;
				}
				//Check if current point is not an 'x' and is not the fill color, if true then fill it with the fill color.
				if(currentCanvas[y][x] != 'x' && currentCanvas[y][x] != c){
					currentCanvas[y][x] = c;
				}
				/* Check if point above the current one is not an 'x' and is not the fill color, 
				 * if true then call fill color recursively and move the coordinate point up by one unit.
				 */
				if(currentCanvas[y+1][x] != 'x' && currentCanvas[y+1][x] != c){
					fillColor(x, y+1, c, canvas);
				}
				/* Check if point above the current one is not an 'x' and is not the fill color, 
				 * if true then call fill color recursively and move the coordinate point to the left by one unit.
				 */
				if(currentCanvas[y][x-1] != 'x' && currentCanvas[y][x-1] != c){
					fillColor(x-1, y, c, canvas);
				}
				/* Check if point above the current one is not an 'x' and is not the fill color, 
				 * if true then call fill color recursively and move the coordinate point to the right by one unit.
				 */
				if(currentCanvas[y][x+1] != 'x' && currentCanvas[y][x+1] != c){
					fillColor(x+1, y, c, canvas);
				}
				/* Check if point above the current one is not an 'x' and is not the fill color, 
				 * if true then call fill color recursively and move the coordinate point down by one unit.
				 */
				if(currentCanvas[y-1][x] != 'x' && currentCanvas[y-1][x] != c){
					fillColor(x, y-1, c, canvas);
				}
				return;
			}
			else{
				return;
			}
		}
		else{
			System.out.println("There is currently no implementation for the " + canvas.getClass() +" instance of DrawingBoard");
		}
	}

	/**
	 * 
	 * @param x - x coordinate on the canvas
	 * @param y - y coordinate on the canvas
	 * @param canvas - an instance of the Canvas class on which the boundary check will be called.
	 * @return - returns true when both x and y coordinates meet the boundary region of the instance of Canvas which was passed in, else returns false
	 */
	private static boolean checkCoordinateBoundaries(int x, int y, Canvas canvas){
		if(canvas instanceof DrawingBoard){
			// If both x and y are within the current canvas boundaries then return true,
			// otherwise if either boundary is violated then return false.
			return ((x < ((DrawingBoard) canvas).rightBound && x >= 1) &&
					(y < ((DrawingBoard) canvas).bottomBound && y >= 1) 
					) ? true : false;
		}
		return false;
	}

}
