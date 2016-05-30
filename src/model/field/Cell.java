package model.field;

import static model.configuration.GameProperties.PROPERTIES;

import java.util.Observable;

/**
 * This class represents a cellule of an area
 */
public class Cell extends Observable{
	private FieldType fieldType;
	private int middleX, middleY;
	
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

	public void setMiddlePositions(int x, int y, int midWidthTexture, int midHeightTexture) {
		setMiddleX(x + midWidthTexture);
		setMiddleY(y + midHeightTexture);
	}
}