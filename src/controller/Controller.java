package controller;

import org.apache.log4j.Logger;

import model.field.FullMap;
import model.log.ProjectLogger;
import view.MainFrame;

public class Controller {
	private FullMap model;
	private MainFrame view;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	
	public Controller(){
		// Model
		model = new FullMap();
		
		// View
		view = new MainFrame(model);
		
		model.initMap();
		
		logger.info("controller initialized");
	}
}
