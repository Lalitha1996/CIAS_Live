package com.cias.entity;

public class ReportDownload {

	private String branCode = "";
	private String date = "";
	private String id = "";
	private String startTime = "";
	private String endTime = "";
	private String queId = "";
	private String status = "";
	private String fileName = "";
	private String flag = "";

	public ReportDownload() {
		super();
	}

	public ReportDownload(String branCode, String date, String id,
			String startTime, String endTime, String queId, String status,
			String fileName, String flag) {
		super();
		this.branCode = branCode;
		this.date = date;
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.queId = queId;
		this.status = status;
		this.fileName = fileName;
		this.flag = flag;
	}

	public String getBranCode() {
		return branCode;
	}

	public void setBranCode(String branCode) {
		this.branCode = branCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
