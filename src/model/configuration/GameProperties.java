package model.configuration;

import static model.log.ProjectLogger.LOGGER;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains all game properties stocked in the properties file
 */
public class GameProperties {
	public static Properties PROPERTIES;
	
	public GameProperties(){
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(new FileInputStream("conf/config.properties"));
			LOGGER.info("Properties charged !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}