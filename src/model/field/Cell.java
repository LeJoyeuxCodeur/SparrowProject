package model.field;

/**
 * This class represents a cellule of an area
 */
public class Cell {
	private FieldType fieldType;
	
	public Cell(FieldType fieldType){
		this.fieldType = fieldType;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
}