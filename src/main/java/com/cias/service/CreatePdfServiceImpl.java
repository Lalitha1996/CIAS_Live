package com.cias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.CreatePdfDao;
import com.cias.entity.QueryData;

@Service
public class CreatePdfServiceImpl implements CreatePdfService {

	@Autowired
	CreatePdfDao createPdfDao;;

	public byte[] downloadPdf(QueryData queryData, String bank) {
		byte[] bytes = null;
		bytes = createPdfDao.downloadPdf(queryData,bank);
		return bytes;
	}
}
