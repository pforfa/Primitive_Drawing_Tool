package com.pawelforfa.drawing_tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class InputFileInterpreter {
	
	private static HashSet<String> allowableCommands = new HashSet<String>();
	static{
		allowableCommands.add("C");
		allowableCommands.add("L");
		allowableCommands.add("R");
		allowableCommands.add("B");
	}
	private static boolean foundValidCanvasCommand;
	private static ArrayList<String[]> commands = new ArrayList<>(8);
	
	// Parse through the file and store valid commands into an ArrayList
	public static boolean parseFileCommands(String fileName){
		if(fileName == null || fileName.equals("")){
			System.err.println("Invalid file was passed.");
			return false;
		}
		BufferedReader inputReader;
		try {
			inputReader = new BufferedReader(new FileReader(fileName));
			String singleUnparsedCommand;
			
			while( (singleUnparsedCommand = inputReader.readLine()) != null){
				parseSingleCommand(singleUnparsedCommand);
			}
			inputReader.close();			
			return true;			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("The file with the specified name " + fileName + " was not found.");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("A problem occured when reading a line from the file.");
			return false;
		}
		finally{
			
		}
	}
	
	public static boolean foundCanvasCommand(){
		return foundValidCanvasCommand;
	}
	
	public static int getCommandsLength(){
		return commands.size();
	}
	
	public static ArrayList<String[]> getDrawingCommands(){
		return commands;
	}
	
	public static void clearStoredCommands(){
		foundValidCanvasCommand = false;
		commands = new ArrayList<>(10);
	}
	
	private static String returnErrorString(String[] errorString){
		StringBuilder errorStringBuilder = new StringBuilder();
		for(int index = 0; index < errorString.length-1; index++){
			errorStringBuilder.append(errorString[index] + " ");
		}
		errorStringBuilder.append(errorString[errorString.length-1]);
		return errorStringBuilder.toString();
	}
	
	private static void parseSingleCommand(String singleUnparsedCommand) throws IllegalArgumentException {
		//Gives leniency in parsing in case that there is more than one space between commands
		String[] singleParsedCommand = singleUnparsedCommand.trim().split("\\s+", -1);
		if(!singleParsedCommand.equals(null)){
			if(checkForValidCommand(singleParsedCommand[0])){
				
				// Checks if the parsed commands contain a valid amount of arguments and if any parameters are negative numbers
				switch(singleParsedCommand[0]){
					case "L":
						if(singleParsedCommand.length != 5){
							System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
							System.err.println("Line command did not contain 4 arguments.");
							return;
						}
						for(int commandIndex = 1; commandIndex < singleParsedCommand.length; commandIndex++){
							int parameter = Integer.parseInt(singleParsedCommand[commandIndex]);
							if( parameter < 0 ){
								System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
								System.err.println("Cannot have a negative parameter.");
								return;
							}
						}
						break;	
					case "B":
						if(singleParsedCommand.length != 4){
							System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
							System.err.println("Line command did not contain 3 arguments.");
							return;
						}
						for(int commandIndex = 1; commandIndex < singleParsedCommand.length-1; commandIndex++){
							int parameter = Integer.parseInt(singleParsedCommand[commandIndex]);
							if( parameter < 0 ){
								System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
								System.err.println("Cannot have a negative parameter.");
								return;
							}
						}
						String colorParameter = singleParsedCommand[singleParsedCommand.length-1];
						if(colorParameter.length() != 1){
							System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
							System.err.println("Color parameter should be a single letter value such as 'o', 'r' from the allowed set values.");
							return;
						}
						break;
						
					case "R":
						if(singleParsedCommand.length != 5){
							System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
							System.err.println("Rectangle command did not contain 4 arguments.");
							return;
						}
						for(int commandIndex = 1; commandIndex < singleParsedCommand.length; commandIndex++){
							int parameter = Integer.parseInt(singleParsedCommand[commandIndex]);
							if( parameter < 0 ){
								System.err.println("Error found in the command '" + returnErrorString(singleParsedCommand) + "'.");
								System.err.println("Cannot have a negative parameter.");
								return;
							}
						}
						break;
				}
				
				commands.add(singleParsedCommand);
				
				/* If the command found is canvas, and if there were previous commands found
				 * before the canvas command, then ensure that the canvas command will be the first command
				 * in the commands ArrayList to ensure proper execution.
				 * 
				 * This check gives leniency to users who do not put the canvas creation command at the top of the list. 	
				 */
				if(singleParsedCommand[0].equals("C") && commands.size() > 2){
					Collections.swap(commands, 0, commands.size() - 1);
				}
				return;
			}
			System.out.println("The command " + singleParsedCommand[0] + " was not recognized by the drawing tool's set of commands.");
			return;
		}
		return;
	}
	
	/**
	 * Helper function for checking whether the parsed command was a valid command.
	 * @param parsedCommandForCheck - First entry passed in from the String[] of parsed commands
	 * @return True if the command is a valid command, else returns false
	 */
	private static boolean checkForValidCommand(String parsedCommandForCheck){
		if( allowableCommands.contains(parsedCommandForCheck) ){
			if(parsedCommandForCheck.equals("C")){
				foundValidCanvasCommand = true;
			}
			return true;
		}
		return false;
	}
	
	
}
