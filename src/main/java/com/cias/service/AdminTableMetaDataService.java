package com.cias.service;

import java.util.List;

import com.cias.entity.ColumnNames;
import com.cias.entity.TableMetaData;

public interface AdminTableMetaDataService {
	public List<TableMetaData> retrieveDbTables(String bank); //
	public List<ColumnNames> retrieveTableColumns(String table,String bank);
}
