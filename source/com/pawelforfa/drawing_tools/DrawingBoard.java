package com.pawelforfa.drawing_tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DrawingBoard implements Canvas{

	private int length;
	private int width;
	private char[][] canvasBoard;	

	protected int rightBound;
	protected int bottomBound;

	private static int inputCounter = 0;
	private String outputFile;
	private PrintWriter drawingFile;

	private ArrayList<Shape> drawnShapes = new ArrayList<>();

	/**
	 * Creates a square drawing board (canvas) with a default size of 10x10
	 */
	public DrawingBoard(){
		// Call to the constructor with (length, width) parameters.
		this(10, 10);
	}

	/**
	 * Creates a square drawing board (canvas) with user specified values
	 */
	public DrawingBoard(int squareDimension){
		this(squareDimension, squareDimension);
	}
	
	/**
	 * Creates a drawing board (canvas) with user specified values
	 */
	public DrawingBoard(int length, int width) {
		outputFile = (inputCounter == 0) ? "output.txt" : "output" + inputCounter + ".txt";
		++inputCounter;
		try {
			drawingFile = new PrintWriter(new FileWriter(outputFile));
		} catch (IOException e) {
			System.err.println("A problem occured when writing to the file.");

		}
		if(length == 0 || width == 0){
			System.err.println("Both length and width dimensions must be greater than 0. Creating a canvas with a default size of 10x10. Canvas dimensions can later be changed.");
			instantiateCanvas(10,10);
		}
		else{instantiateCanvas(length, width);}
	}

	/**
	 * Add a shape to the drawingBoard for rendering and add it to the drawnShapes array
	 * @param shape - Shape object for rendering on the canvas
	 */
	public void storeShape(Shape shape){
		if(checkShapeBoundaries(shape)){
			drawnShapes.add(shape);
			drawShapeOnBoard(shape);
			return;
		}
	}
	
	public static void failedToCreateCanvas(){
		++inputCounter;
	}

	/**
	 * Helper method for retrieving the canvasBoard. Used by other utility class inside the drawing_tools package.
	 * @return - return the current canvasBoard 2-dimensional array
	 */
	protected char[][] getCanvasBoard(){
		return canvasBoard;
	}

	/* Helper function for placing the Shape object on the canvas board. 
	 * 
	 * The current implementation only allows for placing a Rectangle and Line shape on the board
	 * This implementation utilizes some checks that prevent crashes if a user specifies a coordinate that is out of the boundaries of the canvas.
	 */
	private void drawShapeOnBoard(Shape shape){
		int[] coordinates = shape.retrieveCoordinates();

		if(shape instanceof Rectangle){

			for(int topBorder = coordinates[0]; topBorder <= coordinates[2]; topBorder++){
				canvasBoard[coordinates[1]][topBorder] = 'x';
			}

			// Check if the coordinate y1 is the same point as y2.
			int sideBorders = ( !(coordinates[1]+1 >= coordinates[3]) ) ? coordinates[1]+1 : coordinates[1];
			for(; sideBorders <= coordinates[3]; sideBorders++){ //from y1 to y2
				canvasBoard[sideBorders][coordinates[0]] = 'x';
				canvasBoard[sideBorders][coordinates[2]] = 'x';
			}

			for(int bottomBorder = coordinates[0]; bottomBorder <= coordinates[2]; bottomBorder++){
				canvasBoard[coordinates[3]][bottomBorder] = 'x';
			}

			//Since the shape successfully passed all validity checks and was drawn on the canvas board, add it to the drawn shapes array.
			drawnShapes.add(shape);
		}
		else if(shape instanceof Line){	

			/*
			 * Ensure that only vertical and horizontal lines are drawn, since this program only supports these two features at the moment.
			 * Checks whether x1,x2 are not equal AND y1,y2 are not equal. If neither is equal, then there was an attempt to draw a non-vertical/horizontal line.
			 * Exit drawing at that point
			 */
			if(coordinates[1] != coordinates[3] && coordinates[0] != coordinates[2]){
				System.err.println("\r\nNot a valid line shape. This version of the program only supports vertical and horizontal line.\r\n"
						+ "Check the following coordinates and make sure create a valid vertical or horizontal line.\r\n"
						+ "(x1, x2): (" + coordinates[0] + ", " + coordinates[2] + "). Same? " + (coordinates[0] == coordinates[2]) +"\r\n"
						+ "(y1, y2): (" + coordinates[1] + ", " + coordinates[3] + "). Same? " + (coordinates[1] == coordinates[3]) +"\r\n"
						+ "Note - both are false. Change either of the above statements to be true in order to draw a line.");
				return;
			}

			for(int horizontalLine = coordinates[0]; horizontalLine <= coordinates[2]; horizontalLine++){
				for(int verticalLine = coordinates[1]; verticalLine <= coordinates[3]; verticalLine++){
					canvasBoard[verticalLine][horizontalLine] = 'x';
				}
			}

			//Since the shape successfully passed all validity checks and was drawn on the canvas board, add it to the drawn shapes array.
			drawnShapes.add(shape);
		}		
	}

	// Helper function for checking if the passed in Shape object is located within the bounds of the canvas board
	private boolean checkShapeBoundaries(Shape shape){

		// Refer to specific Shape subclass to see the order of the coordinates
		int[] coordinates = shape.retrieveCoordinates();

		if(shape instanceof Line || shape instanceof Rectangle){
			//Check if x1 and x2 are within the bounds of the canvas drawing space
			if( coordinates[0] < rightBound &&
					coordinates[0] >= 1 &&
					coordinates[2] < rightBound &&
					coordinates[2] >= 1){}
			else{
				System.err.println("---------");
				System.err.println("Check for x axis boundaries");
				System.err.println("x1: (" + coordinates[0] + " < " + rightBound +") " + (coordinates[0] < rightBound));
				System.err.println("x1: (" + coordinates[0] + " >= " + "1) " + (coordinates[0] >= 1));
				System.err.println("x2: (" + coordinates[2] + " < " + rightBound +") " + (coordinates[2] < rightBound));
				System.err.println("x2: (" + coordinates[2] + " >= " + "1) " + (coordinates[2] >= 1));
				System.err.println("---------");
				return false;
			}


			//Check if y1 and y2 are within the bounds of the canvas drawing space
			if( coordinates[1] < bottomBound && 
					coordinates[1] >= 1 &&
					coordinates[3] < bottomBound && 
					coordinates[3] >= 1){}
			else{
				System.err.println("---------");
				System.err.println("Check for x axis boundaries");
				System.err.println("y1: (" + coordinates[1] + " < " + bottomBound+") " + (coordinates[1] < bottomBound));
				System.err.println("y1: (" + coordinates[1] + " >= " + "1) " + (coordinates[1] >= 1));
				System.err.println("y2: (" + coordinates[3] + " < " + bottomBound +") " + (coordinates[3] < bottomBound));
				System.err.println("y2: (" + coordinates[3] + " >= " + "1) " + (coordinates[3] >= 1));
				System.err.println("---------");
				return false;
			}

			return true;
		}
		return false;
	}

	/**
	 * Instantiates the canvasBoard with a border and empty drawing space
	 * 
	 * @param rowWidth - specifies how wide the canvas will be (horizontal orientation)
	 * @param columnHeight - specifies how long the canvas will be (vertical orientation)
	 */
	@Override
	public void instantiateCanvas(int rowWidth, int columnHeight) {
		/* 
		 * Note that rowWidth + 2 is used because of the borders around the canvas that need to be displayed.
		 * Each row/column needs to additional slots to accommodate that.
		 */
		length = rowWidth+2;
		width = columnHeight+2;

		canvasBoard = new char[width][length]; 

		for(int firstRow = 0; firstRow < canvasBoard[0].length; firstRow++){
			canvasBoard[0][firstRow] = '-';
		}
		for(int row = 1; row < width-1; row++){
			// Targets the first column in the array, which is the border 
			canvasBoard[row][0] = '|'; 
			for(int column = 1; column < length-1; column++){
				canvasBoard[row][column] = ' ';
			}
			canvasBoard[row][length-1] = '|'; // this target the very last column in the array, which is also the border
		}
		for(int lastRow = 0; lastRow < canvasBoard[width-1].length; lastRow++){
			canvasBoard[width-1][lastRow] = '-';
		}

		instantiateCanvasBoundaries();
	}

	// Helper function for setting the borders of the current canvas board.
	private void instantiateCanvasBoundaries(){
		rightBound = length - 1;
		bottomBound= width - 1;
	}

	/**Draws the canvas into the file specified in 'drawingFile'
	 * 
	 */
	@Override
	public void drawCanvas() {
		if(drawingFile == null){
			System.err.println("No drawing file specified.");
			return;
		}
		for(int i = 0; i < canvasBoard.length; i++){
			for(int j = 0; j < canvasBoard[i].length; j++){
				drawingFile.print(canvasBoard[i][j]);
			}
			drawingFile.println();
		}
		drawingFile.close();
	}

	/**
	 * Returns the current dimensions of the canvasBoard array including the borders
	 * @return - Array with length and width dimensions respectively in that order
	 */
	public int[] getCanvasDimensions(){
		int[] dimensions = {this.length, this.width};
		return dimensions;
	}

}
