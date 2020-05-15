package com.cias.entity;

public class ReportHistory {

	private String startTime = "";
	private String endTime = "";
	private String text = "";
	private String mTime = "";

	public ReportHistory() {
		super();
	}

	public ReportHistory(String startTime, String endTime, String text,
			String mTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.text = text;
		this.mTime = mTime;
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

	public String gettext() {
		return text;
	}

	public void settext(String text) {
		this.text = text;
	}

	public String getMTime() {
		return mTime;
	}

	public void setMTime(String mTime) {
		this.mTime = mTime;
	}

}
