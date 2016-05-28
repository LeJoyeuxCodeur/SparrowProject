package model.field;

import static model.configuration.GameProperties.PROPERTIES;

import java.util.Observable;

/**
 * This class represents a cellule of an area
 */
public class Cell extends Observable{
	private FieldType fieldType;
	private int pixelX;
	private int pixelY;
	
	
	private String ileType = PROPERTIES.getProperty("ileType");
	private String seaType = PROPERTIES.getProperty("seaType");
	
	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	
	public String toString(){
		if(fieldType == FieldType.ILE)
			return ileType + "  ";
		return seaType + "  ";
	}

	public int getPixelX() {
		return pixelX;
	}

	public void setPixelX(int pixelX) {
		this.pixelX = pixelX;
	}

	public int getPixelY() {
		return pixelY;
	}

	public void setPixelY(int pixelY) {
		this.pixelY = pixelY;
	}
}