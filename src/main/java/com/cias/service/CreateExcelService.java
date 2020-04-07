package com.cias.service;

import com.cias.entity.QueryData;

public interface CreateExcelService {
	public byte[] downloadExcel(QueryData queryData,String bank);
}
