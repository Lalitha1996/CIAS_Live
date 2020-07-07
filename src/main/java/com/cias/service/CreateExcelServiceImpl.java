package com.cias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.CreateExcelDao;
import com.cias.entity.QueryData;

@Service
public class CreateExcelServiceImpl implements CreateExcelService {

	@Autowired
	CreateExcelDao excelDao;

	@Override
	public byte[] downloadExcel(QueryData queryData, String bank) {
		//return excelDao.downloadExcel(queryData,bank);
		return null;
	}

}
