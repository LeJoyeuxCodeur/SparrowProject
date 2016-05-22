package model.field;

import static model.configuration.GameProperties.PROPERTIES;;

/**
 * This class contains all {@link Area} of the game
 */
public class FullMap {
	/**
	 * The map of the game
	 */
	private Area[][] fullMap;
	
	
	/**
	 * Build a map with width and height stored in the config file
	 */
	public FullMap(){
		Integer width = Integer.parseInt(PROPERTIES.getProperty("fieldWidth"));
		Integer height = Integer.parseInt(PROPERTIES.getProperty("fieldHeight"));

		fullMap = new Area[width][height];
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
}
