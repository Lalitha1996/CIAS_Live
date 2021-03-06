package com.cias.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cesys004 generated by hbm2java
 */
@Entity
@Table(name = "cesys004_copy")
public class Banks implements java.io.Serializable {

	private static final long serialVersionUID = 493632348407929544L;
	
	private Integer id;
	private String bankName;
	private String bankCode;
	private String driverClass;
	private String databaseUrl;
	private String username;
	private String password;
	private String banktype;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "bank_name", length = 200)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "bank_code", length = 100)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "driver_class", length = 100)
	public String getDriverClass() {
		return this.driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	@Column(name = "database_url", length = 100)
	public String getDatabaseUrl() {
		return this.databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	@Column(name = "username", length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "bank_type", length = 200)
	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}
}