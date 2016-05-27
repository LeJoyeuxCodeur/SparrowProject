package model.configuration;

import org.apache.log4j.BasicConfigurator;

/**
 * This class loads the default configuration for the program
 */
public class Configuration {
	public static void setDefaultConfiguration(){
		// Configure the logger
		BasicConfigurator.configure();
		
		// Initialize properties
		new GameProperties();
	}
}