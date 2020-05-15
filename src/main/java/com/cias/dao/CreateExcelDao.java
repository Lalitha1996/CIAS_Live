package com.cias.dao;

import com.cias.entity.QueryData;

public interface CreateExcelDao {
	public void downloadExcel(QueryData queryData,String bank,String filename);
}
