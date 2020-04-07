package com.cias.entity;

public class BranchInfo {
/*	private boolean range = false;
	private boolean all = false;
	private boolean consolidated=false;*/
	private String optionvalue;
	private String toBranch;
	private String brcdBankCd;
	private String brcdBranchCd;
	private String endbrcdBranchCd;
	private String fileType;


	public String getToBranch() {
		return toBranch;
	}

	public String getBrcdBankCd() {
		return brcdBankCd;
	}

	public String getBrcdBranchCd() {
		return brcdBranchCd;
	}


	public void setToBranch(String toBranch) {
		this.toBranch = toBranch;
	}

	public void setBrcdBankCd(String brcdBankCd) {
		this.brcdBankCd = brcdBankCd;
	}

	public void setBrcdBranchCd(String brcdBranchCd) {
		this.brcdBranchCd = brcdBranchCd;
	}

	public String getEndbrcdBranchCd() {
		return endbrcdBranchCd;
	}

	public void setEndbrcdBranchCd(String endbrcdBranchCd) {
		this.endbrcdBranchCd = endbrcdBranchCd;
	}


	public String getOptionvalue() {
		return optionvalue;
	}

	public void setOptionvalue(String optionvalue) {
		this.optionvalue = optionvalue;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
