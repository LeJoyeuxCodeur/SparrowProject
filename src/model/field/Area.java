package model.field;

import static model.configuration.GameProperties.PROPERTIES;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import model.log.ProjectLogger;

/**
 * This class represents a area, it will be included in the full map. It may contains earth and/or sea
 */
public class Area extends Observable implements Observer{
	private Cell[][] cells;
	private File areaFile;
	private Logger logger = new ProjectLogger(this.getClass()).getLogger();
	private Map<String, Character> mapWithCharactersUsed;
	private int middleX, middleY;
	
	private void initArea(){
		String area = "";
		try {
			area = IOUtils.toString(new FileInputStream(areaFile), "UTF-8").replaceAll(",", "");
			logger.info("Map loaded");
		
			initListWithUsedValues();
			initAreaWithValues(area);
		} catch (IOException e) {
			logger.error("Map not loaded -- " + e.getMessage());
		}
	}
	
	private void initListWithUsedValues() {
		mapWithCharactersUsed = new HashMap<>();
		
		mapWithCharactersUsed.put("seaType", Character.valueOf(PROPERTIES.getProperty("seaType").charAt(0)));
		mapWithCharactersUsed.put("ileType", Character.valueOf(PROPERTIES.getProperty("ileType").charAt(0)));
	}

	private void initAreaWithValues(String area) {
		int counter = 0;

		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[0].length; j++){
				char type = area.charAt(counter);
				
				while(!mapWithCharactersUsed.containsValue(type)){
					type = area.charAt(++counter);
				}
				
				cells[i][j] = new Cell();
				cells[i][j].addObserver(this);
				cells[i][j].setFieldType(convertCharacterToCelluleType(type));
		
				counter++;
			}
		}
		
		logger.info("Area initialized");
	}

	private FieldType convertCharacterToCelluleType(Character type){
		if(type.compareTo(mapWithCharactersUsed.get("ileType")) == 0)
			return FieldType.ILE;
		else if(type.compareTo(mapWithCharactersUsed.get("seaType")) == 0)
			return FieldType.SEA;
		return null;
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public void setArea(File areaFile){
		this.areaFile = areaFile;
		final Integer cellNumberOnWidth = Integer.parseInt(PROPERTIES.getProperty("cellNumberOnWidth"));
		final Integer cellNumberOnHeight = Integer.parseInt(PROPERTIES.getProperty("cellNumberOnHeight"));
		
		cells = new Cell[cellNumberOnWidth][cellNumberOnHeight];
		initArea();
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}
	
	public String toString(){
		String s = "";
		
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[0].length; j++){
				if(cells[i][j] != null)
					s += cells[i][j];
				else
					s += "   ";
			}
			s += "\n";
		}
		
		return s;
	}

	public int getMiddleX() {
		return middleX;
	}

	public void setMiddleX(int middleX) {
		this.middleX = middleX;
	}

	public int getMiddleY() {
		return middleY;
	}

	public void setMiddleY(int middleY) {
		this.middleY = middleY;
	}

	public Cell getCellMatchingClick(int realLocX, int realLocY, int widthTexture, int heightTexture) {
		int drawCellX, drawCellY;
		
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[0].length; j++){
				drawCellX = cells[i][j].getMiddleX() - widthTexture/2;
				drawCellY = cells[i][j].getMiddleY() - heightTexture/2;
				
				if(drawCellX < realLocX && realLocX < (drawCellX + widthTexture) 
						&& drawCellY < realLocY && realLocY < (drawCellY + heightTexture))
					return cells[i][j];
			}
		}
		return null;
	}	
}