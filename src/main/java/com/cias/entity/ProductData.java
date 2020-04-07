package com.cias.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "cesys003")
public class ProductData implements Serializable {

	/**
	 * 
	 */
	//private static final long serialVersionUID = -4155788594048164116L;

	/*@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;*/
	
	@Column(name = "Product_Code")
	private String Productcode;
	
	
	@Column(name = "Acct_Type")
	private String AcctType;
	
	@Column(name = "Header")
	private String header;

	public String getProductcode() {
		return Productcode;
	}

	public void setProductcode(String productcode) {
		Productcode = productcode;
	}

	public String getAcctType() {
		return AcctType;
	}

	public void setAcctType(String acctType) {
		AcctType = acctType;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
	

	
	