package com.cias.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnNames  {

	private String name;
	private String dataType;
	private String fieldName;
	
	@Transient
	private String required;
	@Transient
	private String keyField;
	List<ApplicationLabel>appLabels;
	List<TableMetaData> joinTables;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField() {
		return fieldName;
	}

	public void setField(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<ApplicationLabel> getAppLabels() {
		return appLabels;
	}

	public void setAppLabels(List<ApplicationLabel> appLabels) {
		this.appLabels = appLabels;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public List<TableMetaData> getJoinTables() {
		return joinTables;
	}

	public void setJoinTables(List<TableMetaData> joinTables) {
		this.joinTables = joinTables;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	


}
