package model.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import model.log.ProjectLogger;

/**
 * This class contains all game properties stocked in the properties file
 */
public class GameProperties {
	public static Properties PROPERTIES;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	
	public GameProperties(){
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(new FileInputStream("conf/config.properties"));
			logger.info("Properties charged !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}