package controller.main;

import java.io.File;

import model.configuration.Configuration;
import model.field.Area;

/**
 * This class is the main entry point
 */
public class Main {
	public static void main(String[] args) {
		// Run the default configuration for the program
		Configuration.setDefaultConfiguration();
		
		new Area(new File("data/maps/mapTest.txt"));
	}
}