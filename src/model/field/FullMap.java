package model.field;

import static model.configuration.GameProperties.PROPERTIES;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import model.log.ProjectLogger;;

/**
 * This class contains all {@link Area} of the game
 */
public class FullMap extends Observable implements Observer{
	/**
	 * The map of the game
	 */
	private Area[][] areas;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	
	
	/**
	 * Init a map with width and height stored in the config file
	 */
	public void initMap(){
		Integer width = Integer.parseInt(PROPERTIES.getProperty("fieldWidth"));
		Integer height = Integer.parseInt(PROPERTIES.getProperty("fieldHeight"));

		areas = new Area[width][height];
		
		File mapTestPath = new File(PROPERTIES.getProperty("mapTestPath"));
		
		for(int i = 0; i < areas.length; i++){
			for(int j = 0; j < areas[0].length; j++){
				areas[i][j] = new Area();
				areas[i][j].addObserver(this);
				areas[i][j].setArea(mapTestPath);				
			}
		}
		
		setChanged();
		notifyObservers("MAP_CHARGED");
		
		logger.info("model initialized --> Number of areas : " + areas.length * areas[0].length);
	}

	/**
	 * Accessor for the map
	 * @return {@link Area [][]}
	 */
	public Area[][] getAreas() {
		return areas;
	}


	/**
	 * Mutator for the map
	 * param fullMap {@link Area [][]}
	 */
	public void setAreas(Area[][] fullMap) {
		this.areas = fullMap;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	public Area getAreaMatchingClick(int realLocX, int realLocY){
		return areas[0][0];
		
		//TODO
	}	
}
