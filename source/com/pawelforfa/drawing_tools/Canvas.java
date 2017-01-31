package com.pawelforfa.drawing_tools;

public interface Canvas {

	/**Unimplemented method for canvasDimensions.
	 * Due to the possibility of extending the requirements of the project
	 * in the future, this method can be implemented to meet the new requirements.
	 * Canvas dimensions are a necessary requirements, therefore any class (most likely the main class)
	 * that will contain the actual canvas
	 */
	public void instantiateCanvas(int length, int width);

	/**Implementation for drawing the canvas. In case the requirements change, and the canvas
	 * needs to be drawn in a GUI window (swing, JavaFX) then the implementation can be changed.
	 * This method forces any class that implements this interface to implement a draw method.	 * 
	 */
	public void drawCanvas();

}
