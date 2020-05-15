package com.cias.dao;

import com.cias.entity.QueryData;
import com.cias.entity.ReportQueueData;

public interface CreateCsvDao {
	public byte[] downloadCsv(QueryData queryData,String bank);
	public byte[] downloadCsvPipeSeperator(QueryData queryData, String bank);
	//RabbitMQ
	public int addReportQueueData(ReportQueueData datQueueData);
	ReportQueueData getReportQueueData(int id);

}
