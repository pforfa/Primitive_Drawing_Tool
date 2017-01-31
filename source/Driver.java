import java.util.ArrayList;

import com.pawelforfa.drawing_tools.*;

public class Driver {

	public static void main(String[] args) {

		if(args.length == 0){
			System.err.println("Specify a single or multiple input files as the arguments for this program.");
		}
		else{
			for(String inputFile : args){
				boolean continueWithSetup= InputFileInterpreter.parseFileCommands(inputFile);
				if(continueWithSetup == true){
					setup(inputFile);
					InputFileInterpreter.clearStoredCommands();
				}
				else{
					InputFileInterpreter.clearStoredCommands();
					continue;
				}
			}
		}
	}

	/* Helper function for setting up all the necessities including
	 * parsing the text file, syntax checking for the commands, running the commands and displaying the canvas.
	 */
	private static void setup(String inputFile){
		ArrayList<String[]> commands = InputFileInterpreter.getDrawingCommands();

		/*
		 * Check whether an actual 'C' command was found for creating a canvas.
		 * If not, then stop execution here since nothing can be drawn without 
		 * an instance of a canvas.
		 */
		DrawingBoard drawingBoard;
		if(InputFileInterpreter.foundCanvasCommand()){
			try{
				int width = Integer.parseInt(commands.get(0)[1]);
				int length = Integer.parseInt(commands.get(0)[2]);

				drawingBoard = new DrawingBoard(width, length);

				for(int commandGroup = 1; commandGroup < commands.size(); commandGroup++){
					String[] singleCommand = commands.get(commandGroup);

					switch(singleCommand[0]){

					// Line Shape command found
					case "L":
						try{
							int x1 = Integer.parseInt(singleCommand[1]);
							int y1 = Integer.parseInt(singleCommand[2]);
							int x2 = Integer.parseInt(singleCommand[3]);
							int y2 = Integer.parseInt(singleCommand[4]);
							Shape line = new Line(x1, y1, x2, y2);
							drawingBoard.storeShape(line);
							break;	
						}
						catch(NumberFormatException nfe){
							//Problem occurred with the parsing of one of the arguments, not running this command
							System.err.println("A problem occured with parsing one of the arguments.\r\nEnsure that " 
									+ singleCommand[1] + " " 
									+ singleCommand[2] + " "
									+ singleCommand[3] + " "
									+ singleCommand[4] + " "
									+ "are all valid integer arguments for creating a line shape.");
							continue;
						}
						// Rectangle Shape command found
					case "R":
						try{
							int x1 = Integer.parseInt(singleCommand[1]);
							int y1 = Integer.parseInt(singleCommand[2]);
							int x2 = Integer.parseInt(singleCommand[3]);
							int y2 = Integer.parseInt(singleCommand[4]);
							Shape rectangle = new Rectangle(x1, y1, x2, y2);
							drawingBoard.storeShape(rectangle);
							break;	
						}
						catch(NumberFormatException nfe){
							//Problem occurred with the parsing of one of the arguments, not running this command
							System.err.println("A problem occured with parsing one of the arguments.\r\nEnsure that " 
									+ singleCommand[1] + " " 
									+ singleCommand[2] + " "
									+ singleCommand[3] + " "
									+ singleCommand[4] + " "
									+ "are all valid integer arguments for creating a rectangle shape.");
							continue;
						}
						// Bucket Fill command found
					case "B":
						try{
							int x = Integer.parseInt(singleCommand[1]);
							int y = Integer.parseInt(singleCommand[2]);
							char c = singleCommand[3].charAt(0);
							BucketFill.fillColor(x, y, c, drawingBoard);
							break;	
						}
						catch(NumberFormatException nfe){
							//Problem occurred with the parsing of one of the arguments, not running this command
							System.err.println("A problem occured with parsing one of the arguments.\r\nEnsure that " 
									+ singleCommand[1] + " " 
									+ singleCommand[2] + " "
									+ singleCommand[3] + " "
									+ singleCommand[4] + " "
									+ "are all valid integer arguments for creating a rectangle shape.");
							continue;
						}
					}
				}
				drawingBoard.drawCanvas();
			}
			catch(NumberFormatException nfe){
				System.err.println("Failed to properly parse the canvas dimensions. Check that the input file canvas creation command has integer values.");
			}
		}
		else{
			System.err.println("No valid command for creating a canvas was found. Insert a canvas creation command into " + inputFile + ". Example: 'C 10 10'.");
			DrawingBoard.failedToCreateCanvas();
		}
	}

}
