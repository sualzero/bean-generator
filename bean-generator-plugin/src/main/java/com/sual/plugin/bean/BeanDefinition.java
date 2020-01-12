package com.sual.plugin.bean;

import java.util.List;

import com.sual.plugin.bean.field.FieldDefinition;

public class BeanDefinition {

	private String apiName;
	
	private String httpMethod;
	
	private String dtoType;
	
	private List<FieldDefinition> fields;

	public List<FieldDefinition> getFields() {
		return fields;
	}

	public void setFields(List<FieldDefinition> fields) {
		this.fields = fields;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getDtoType() {
		return dtoType;
	}

	public void setDtoType(String dtoType) {
		this.dtoType = dtoType;
	}

	
}
