package com.sual.plugin.bean.field;

import java.util.List;

public class FieldDefinition {

	private String fieldName;
	
	private String camelCaseFieldName;
	
	private String comment;
	
	private String type;
	
	private List<String> anotations;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getAnotations() {
		return anotations;
	}

	public void setAnotations(List<String> anotations) {
		this.anotations = anotations;
	}

	public String getCamelCaseFieldName() {
		return camelCaseFieldName;
	}

	public void setCamelCaseFieldName(String camelCaseFieldName) {
		this.camelCaseFieldName = camelCaseFieldName;
	}
}
