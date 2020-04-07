package com.cias.dao;

import java.util.List;
import java.util.Map;

import com.cias.entity.ColumnNames;
import com.cias.entity.TableMetaData;

public interface AdminTableMetaDataDao {

	public List<TableMetaData> retrieveDbTables(String bank);
	public List<ColumnNames> retrieveTableColumns(String table,String bank);
	public Map<String, String> retrieveDbTable(String bank);
}
