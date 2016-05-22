package model.field;

import static model.configuration.GameProperties.PROPERTIES;;

/**
 * This class represents a area, it will be included in the full map. It may contains earth and/or sea
 */
public class Area {
	private Cellule[][] cells;
	
	public Area(){
		final Integer width = Integer.parseInt(PROPERTIES.getProperty("cellWidth"));
		final Integer height = Integer.parseInt(PROPERTIES.getProperty("cellHeight"));
		
		cells = new Cellule[width][height];
	}

	public Cellule[][] getCells() {
		return cells;
	}

	public void setCells(Cellule[][] cells) {
		this.cells = cells;
	}
}