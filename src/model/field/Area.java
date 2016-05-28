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
	private int pixelX, pixelY;
	
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

	public int getPixelX() {
		return pixelX;
	}

	public void setPixeLX(int pixelX) {
		this.pixelX = pixelX;
	}

	public int getPixelY() {
		return pixelY;
	}

	public void setPixelY(int pixelY) {
		this.pixelY = pixelY;
	}

	public Cell getCellMatchingClick(int boatXCliqued, int boatYCliqued, int posX, int posY) {
		Cell tmp = cells[0][0];
		int diff;
		int lastMinorDIffFound = Math.abs((boatXCliqued + posX) - cells[0][0].getPixelX());
		int bestIFound = 0;
		
		for(int i = 0; i < cells.length; i++){
			diff = Math.abs((boatXCliqued + posX) - cells[i][i].getPixelX());
		
			if(diff < lastMinorDIffFound){
				lastMinorDIffFound = diff;
				bestIFound = i;
			}
		}
		
		//TODO
		/*
		for(int j = cells.length - bestIFound; j < cells[0].length; j++){
			diff = Math.abs(boatYCliqued - cells[bestIFound][j].getPixelY() - posY);
				
			if(diff < lastMinorDIffFound){
				lastMinorDIffFound = diff;
				tmp = cells[bestIFound][j];
			}
		}*/
		return tmp;
	}	
}