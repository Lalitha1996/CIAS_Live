package com.cias.service;

import com.cias.entity.QueryData;

public interface CreateCsvService {
	public byte[] downloadCsv(QueryData queryData,String bank);
	public byte[] downloadCsvPipeSeperator(QueryData queryData, String bank);
	//RabbitMQ
	int csvDownloadQueue(QueryData queryData, String bank);
	public int downloadCsv1(QueryData queryData, String bank);
}
