package model.log;

import org.apache.log4j.Logger;

/**
 * This class enable logs in the console
 */
public class ProjectLogger {
	private Logger logger;
	
	public ProjectLogger(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}
	
	public Logger getLogger(){
		return logger;
	}
}