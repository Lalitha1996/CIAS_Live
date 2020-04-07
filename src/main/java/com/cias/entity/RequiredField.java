package com.cias.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "req_columns_copy")
public class RequiredField {

	@Id
	@Column(name = "req_id")
	private String id;
	@Column(name = "tbl_name")
	private String tabel;
	@Column(name = "field_name")
	private String filed;
	@Column(name = "active")
	private String isActive;
	
	@Column(name = "filter_field")
	private String filterfield;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTabel() {
		return tabel;
	}

	public void setTabel(String tabel) {
		this.tabel = tabel;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getFilterfield() {
		return filterfield;
	}

	public void setFilterfield(String filterfield) {
		this.filterfield = filterfield;
	}

}
