package com.cias.dao;

import com.cias.entity.QueryData;

public interface CreateCsvDao {
	public byte[] downloadCsv(QueryData queryData,String bank);

	public byte[] downloadCsvPipeSeperator(QueryData queryData, String bank);
}
