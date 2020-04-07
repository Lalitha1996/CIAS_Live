package com.cias.dao;

import com.cias.entity.QueryData;

public interface CreateExcelDao {
	public byte[] downloadExcel(QueryData queryData,String bank);
}
