package controller;

import static model.log.ProjectLogger.LOGGER;

import model.field.FullMap;
import view.MainFrame;

public class Controller {
	private FullMap model;
	private MainFrame view;
	
	public Controller(){
		// Model
		model = new FullMap();
		
		// View
		view = new MainFrame(model);
		
		model.initMap();
		
		LOGGER.info("controller initialized");
	}
}
