package com.cias.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class ReportQueueDataDTO {

	@Column(name = "id")
	private int reportQueueId;

	@Column(name = "timeadded")
	private Date timeadded;

	@Column(name = "timetake")
	private String timetake;
	@Column(name = "timecomplete")
	private Date timecomplete;
	
	public ReportQueueDataDTO() {
		super();
	}

	public ReportQueueDataDTO(Object object) {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "queuedataid", unique = true)
	private QueryData queuedataid;
	
	@Column(name = "status")
	private String status;
	@Column(name = "bank")
	private String bank;
	@Column(name = "totalrecord")
	private String totalCount;
	@Column(name = "fileName")
	private String fileName;

	public int getReportQueueId() {
		return reportQueueId;
	}

	public void setReportQueueId(int reportQueueId) {
		this.reportQueueId = reportQueueId;
	}

	public Date getTimeadded() {
		return timeadded;
	}

	public void setTimeadded(Date timeadded) {
		this.timeadded = timeadded;
	}

	public String getTimetake() {
		return timetake;
	}

	public void setTimetake(String timetake) {
		this.timetake = timetake;
	}

	public Date getTimecomplete() {
		return timecomplete;
	}

	public void setTimecomplete(Date timecomplete) {
		this.timecomplete = timecomplete;
	}

	public QueryData getQueuedataid() {
		return queuedataid;
	}

	public void setQueuedataid(QueryData queuedataid) {
		this.queuedataid = queuedataid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
