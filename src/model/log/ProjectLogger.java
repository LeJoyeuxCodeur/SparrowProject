package model.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * This class enable logs in the console
 */
public class ProjectLogger {
	public static Logger LOGGER;
	
	public ProjectLogger() {
		BasicConfigurator.configure();
		LOGGER = Logger.getLogger(ProjectLogger.class);
	}
}