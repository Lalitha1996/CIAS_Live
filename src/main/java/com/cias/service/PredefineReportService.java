package com.cias.service;

import java.util.List;

import com.cias.entity.PreDefineReport;
import com.cias.entity.QueryData;
import com.cias.entity.TableMetaData;

public interface PredefineReportService {
	public String saveDefReportObj(PreDefineReport PreDefineReport);

	public List<PreDefineReport> getQryListByBank(String bankCode);

	public boolean isQuerySaved(PreDefineReport preDefineReport);

	public String deleteThisObject(PreDefineReport PreDefineReport);

	public List<TableMetaData> getTableDataListByBank(PreDefineReport preDefineReport, String bankCode);
	
	List<TableMetaData> getDataIntoFile(QueryData getTableData, String bank);
	
	public 	String  getQueryString(String qry);
	
	public boolean getTableNameIsExist(List<TableMetaData> table ,String tableName);

}
