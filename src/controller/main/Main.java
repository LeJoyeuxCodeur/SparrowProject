package controller.main;

import controller.Controller;
import model.configuration.Configuration;

/**
 * This class is the main entry point
 */
public class Main {
	public static void main(String[] args) {
		// Run the default configuration for the program
		Configuration.setDefaultConfiguration();
		
		// Controller
		new Controller();
	}
}