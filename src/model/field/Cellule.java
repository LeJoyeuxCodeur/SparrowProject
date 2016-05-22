package model.field;

/**
 * This class represents a cellule of an area
 */
public class Cellule {
	private FieldType fieldType;
	
	public Cellule(FieldType fieldType){
		this.fieldType = fieldType;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
}
