package com.cias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.AdminTableMetaDataDao;
import com.cias.dao.ApplicationLabelDao;
import com.cias.entity.ColumnNames;
import com.cias.entity.TableMetaData;

@Service
public class AdminTableMetaDataServiceImpl implements AdminTableMetaDataService {

	@Autowired
	AdminTableMetaDataDao adminTableMetaDataDao;
	

	@Autowired
	ApplicationLabelDao applicationLabelDao;

	@Override
	public List<TableMetaData> retrieveDbTables(String bank) {
		return adminTableMetaDataDao.retrieveDbTables(bank);
	}

	@Override
	public List<ColumnNames> retrieveTableColumns(String table,String bank) {
		return adminTableMetaDataDao.retrieveTableColumns(table,bank);
	}

}
