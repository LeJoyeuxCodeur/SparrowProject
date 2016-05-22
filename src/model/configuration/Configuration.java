package model.configuration;

import model.log.ProjectLogger;

/**
 * This class loads the default configuration for the program
 */
public class Configuration {
	public static void setDefaultConfiguration(){
		// Initialize the logger
		new ProjectLogger();
		
		// Initialize properties
		new GameProperties();
	}
}