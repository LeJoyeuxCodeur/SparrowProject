package model.field;

import java.util.Observable;

/**
 * This class represents a cellule of an area
 */
public class Cell extends Observable{
	private FieldType fieldType;
	
	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
		setChanged();
		notifyObservers(this);
	}
	
	public String toString(){
		if(fieldType == FieldType.EARTH)
			return "X  ";
		return ".  "; // FieldType.SEA
	}
}