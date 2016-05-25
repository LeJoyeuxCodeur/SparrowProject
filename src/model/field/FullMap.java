package model.field;

import static model.configuration.GameProperties.PROPERTIES;
import static model.log.ProjectLogger.LOGGER;

import java.io.File;
import java.util.Observable;
import java.util.Observer;;

/**
 * This class contains all {@link Area} of the game
 */
public class FullMap extends Observable implements Observer{
	/**
	 * The map of the game
	 */
	private Area[][] fullMap;
	
	
	/**
	 * Init a map with width and height stored in the config file
	 */
	public void initMap(){
		Integer width = Integer.parseInt(PROPERTIES.getProperty("fieldWidth"));
		Integer height = Integer.parseInt(PROPERTIES.getProperty("fieldHeight"));

		fullMap = new Area[width][height];
		
		for(int i = 0; i < fullMap.length; i++){
			for(int j = 0; j < fullMap[0].length; j++){
				fullMap[i][j] = new Area();
				fullMap[i][j].addObserver(this);
				fullMap[i][j].setArea(new File("data/maps/mapTest.txt"));				
			}
		}
		
		setChanged();
		notifyObservers("MAP_CHARGED");
		
		LOGGER.info("model initialized --> Number of areas : " + fullMap.length * fullMap[0].length);
	}

	/**
	 * Accessor for the map
	 * @return {@link Area [][]}
	 */
	public Area[][] getFullMap() {
		return fullMap;
	}


	/**
	 * Mutator for the map
	 * param fullMap {@link Area [][]}
	 */
	public void setFullMap(Area[][] fullMap) {
		this.fullMap = fullMap;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}
}
