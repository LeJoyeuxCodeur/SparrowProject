package model.field;

import static model.configuration.GameProperties.PROPERTIES;

import java.util.Observable;

/**
 * This class represents a cellule of an area
 */
public class Cell extends Observable{
	private FieldType fieldType;
	private String ileType = PROPERTIES.getProperty("ileType");
	private String seaType = PROPERTIES.getProperty("ileType");
	
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
}