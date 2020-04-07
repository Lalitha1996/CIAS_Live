package com.cias.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "cesys002_copy")
public class ApplicationLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "label_id")
	private Long id;

	@Column(name = "label_code")
	private String labelCode;
	
	@Column(name = "label_desc")
	private String appLabel;

	public ApplicationLabel() {

	}

	public ApplicationLabel(Long id, String labelCode, String appLabel) {
		this.id = id;
		this.labelCode = labelCode;
		this.appLabel = appLabel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public String getAppLabel() {
		return appLabel;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}

	@Override
	public String toString() {
		return "ApplicationLabel [id=" + id + ", labelCode=" + labelCode + ", appLabel=" + appLabel + "]";
	}

}
