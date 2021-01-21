package com.cias.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "predefine_repo_qry")
public class PreDefineReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name = "SAVE_QUERY")
	private String saveQuery;

	@Column(name = "QUERY_TITLE")
	private String title;

	@Column(name = "DESCRIPTION") 
	private String descp;

	@Column(name = "PURPOSE")
	private String purpose;


	@Column(name = "BANK_CODE")
	private String BankCod;

	@Column(name = "STS")
	private String sts;

	public PreDefineReport() {
		super();
	}

	public PreDefineReport(Integer id, String title, String descp,
			String purpose, String saveQuery, String bankCod, String sts) {
		super();
		this.id = id;
		this.title = title;
		this.descp = descp;
		this.purpose = purpose;
		this.saveQuery = saveQuery;
		BankCod = bankCod;
		this.sts = sts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSaveQuery() {
		return saveQuery;
	}

	public void setSaveQuery(String saveQuery) {
		this.saveQuery = saveQuery;
	}

	public String getBankCod() {
		return BankCod;
	}

	public void setBankCod(String bankCod) {
		BankCod = bankCod;
	}

}
