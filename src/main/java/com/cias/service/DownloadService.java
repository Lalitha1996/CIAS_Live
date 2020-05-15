package com.cias.service;

import java.util.List;

import com.cias.entity.ReportDownload;
import com.cias.entity.ReportHistory;



public interface DownloadService {
	
	public List<ReportDownload>getReportStatus(ReportDownload reportDownload);
	public String getReportDelete(ReportDownload reportDownload);
	public List<ReportHistory> getReportHistory(ReportDownload reportDownload);

}
