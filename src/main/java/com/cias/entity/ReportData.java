package com.cias.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "cerep001")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportData {

	@Id
	@Column(name = "acct_no")
	private String accountNo;
	@Column(name = "cust_no")
	private String customerNo;

	@Column(name = "cust_name")
	private String CustomerName;
	@Column(name = "curr_bal")
	private BigDecimal customerBalance;
	@Column(name = "curr_status")
	private String currStatus;
	@Column(name = "acct_type")
	private String accounttype;
	@Column(name = "int_cat")
	private String intCap;
	@Column(name = "acct_open_dt")
	private String accountOpenDate;
	private List<ColumnNames> columns;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}

	public String getCurrStatus() {
		return currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getIntCap() {
		return intCap;
	}

	public void setIntCap(String intCap) {
		this.intCap = intCap;
	}

	public String getAccountOpenDate() {
		return accountOpenDate;
	}

	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	public List<ColumnNames> getColumns() {
		return columns;
	}

	public void setColumn(List<ColumnNames> columns) {
		this.columns = columns;
	}

}
