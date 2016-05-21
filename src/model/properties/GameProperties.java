package model.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import model.log.ProjectLogger;

/**
 * This class contains all game properties stocked in the properties file
 */
public class GameProperties {
	public static Properties PROPERTIES;
	
	
	public GameProperties(){
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(new FileInputStream("conf/config.properties"));
			ProjectLogger.LOGGER.info("Properties charged !");
			ProjectLogger.LOGGER.warn("error !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ProjectLogger();
		new GameProperties();
	}
}
