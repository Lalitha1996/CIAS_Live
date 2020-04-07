package com.cias.dao;

import com.cias.entity.QueryData;

public interface CreatePdfDao {
	public byte[] downloadPdf(QueryData queryData,String bank);
}
