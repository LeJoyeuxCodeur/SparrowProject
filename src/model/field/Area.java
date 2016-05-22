package model.field;

import static model.configuration.GameProperties.PROPERTIES;
import static model.log.ProjectLogger.LOGGER;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * This class represents a area, it will be included in the full map. It may contains earth and/or sea
 */
public class Area {
	private Cell[][] cells;
	private final Character earth = Character.valueOf(PROPERTIES.getProperty("earthType").charAt(0));
	private final Character sea = Character.valueOf(PROPERTIES.getProperty("seaType").charAt(0));
	private File areaFile;
	
	public Area(File areaFile){
		this.areaFile = areaFile;
		final Integer width = Integer.parseInt(PROPERTIES.getProperty("cellWidth"));
		final Integer height = Integer.parseInt(PROPERTIES.getProperty("cellHeight"));
		
		cells = new Cell[width][height];
		initArea();
	}

	private void initArea(){
		String area = "";
		try {
			area = IOUtils.toString(new FileInputStream(areaFile), "UTF-8").replaceAll(" ", "");
			LOGGER.info("Map test loaded");
		
			initAreaWithValues(area);
		} catch (IOException e) {
			LOGGER.error("Map test not loaded -- " + e.getMessage());
		}
	}
	
	private void initAreaWithValues(String area) {
		int counter = 0;

		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[0].length; j++){
				Character type = Character.valueOf(area.charAt(counter));
				
				while(Character.isWhitespace(type))
					counter++;
				
				cells[i][j] = new Cell(convertCharacterToCelluleType(type));
			}
			counter++;
		}
		LOGGER.info("Area test initialized");
	}

	private FieldType convertCharacterToCelluleType(Character type){
		if(type.compareTo(earth) == 0)
			return FieldType.EARTH;
		else if(type.compareTo(sea) == 0)
			return FieldType.SEA;
		return null;
	}
	
	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
}