package com.cias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.CreateCsvDao;
import com.cias.entity.QueryData;

@Service
public class CreateCsvServiceImpl implements CreateCsvService {

	@Autowired
	CreateCsvDao createCsvDao;

	@Override
	public byte[] downloadCsv(QueryData queryData, String bank) {

		return createCsvDao.downloadCsv(queryData, bank);
	}

	@Override
	public byte[] downloadCsvPipeSeperator(QueryData queryData, String bank) {
		return createCsvDao.downloadCsvPipeSeperator(queryData, bank);
	}

}
