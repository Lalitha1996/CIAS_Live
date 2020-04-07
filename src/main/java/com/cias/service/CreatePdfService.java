package com.cias.service;

import com.cias.entity.QueryData;

public interface CreatePdfService {
	public byte[] downloadPdf(QueryData queryData,String bank);
}
