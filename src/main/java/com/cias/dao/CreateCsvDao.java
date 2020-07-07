package com.cias.dao;

import com.cias.entity.ReportQueueData;
import com.cias.entity.QueryData;

public interface CreateCsvDao {
	
	public int addReportQueueData(ReportQueueData datQueueData);

	ReportQueueData getReportQueueData(int id);
	
	public void downloadCsv(QueryData queryData,String bank,String csvFileLoc);

	public void downloadCsvPipeSeperator(QueryData queryData, String bank,String csvFileLoc);
}
