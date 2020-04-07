package com.cias.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableMetaData {

	private String tableName;
	private String name;
	List<AppMessages> appMessage;
	private List<ColumnNames> names;
	private List<ApplicationLabel> appLabels;
	private String chartsData;
	private List<String> chartsDataFields;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnNames> getNames() {
		return names;
	}

	public void setNames(List<ColumnNames> names) {
		this.names = names;
	}

	public  List<AppMessages> getAppMessage() {
		return appMessage;
	}

	public void setAppMessage (List<AppMessages> appMessage) {
		this.appMessage = appMessage;
	}

	public List<ApplicationLabel> getAppLabels() {
		return appLabels;
	}

	public void setAppLabels(List<ApplicationLabel> appLabels) {
		this.appLabels = appLabels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChartsData() {
		return chartsData;
	}

	public void setChartsData(String chartsData) {
		this.chartsData = chartsData;
	}

	public List<String> getChartsDataFields() {
		return chartsDataFields;
	}

	public void setChartsDataFields(List<String> chartsDataFields) {
		this.chartsDataFields = chartsDataFields;
	}

	

	



}
