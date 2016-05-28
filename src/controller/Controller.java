package controller;

import org.apache.log4j.Logger;

import model.ModelObjects;
import model.log.ProjectLogger;
import view.MainFrame;

public class Controller {
	private ModelObjects modelObjects;
	private MainFrame view;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	
	public Controller(){
		// Model
		modelObjects = new ModelObjects();
		
		// View
		view = new MainFrame(modelObjects);
		
		modelObjects.getFullMap().initMap();
		
		logger.info("controller initialized");
	}
}
