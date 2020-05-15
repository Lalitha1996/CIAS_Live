package com.cias.service;

import java.util.List;
import java.util.Map;

import com.cias.entity.Banks;
import com.cias.entity.Chart;
import com.cias.entity.QueryData;
import com.cias.entity.TableMetaData;
import com.cias.entity.TellerMaster;


public interface AdminReportService {
	public List<TableMetaData> getTableData(QueryData getTableData,String bank,TellerMaster tellerMaster);
	public List<TableMetaData> populateDbTables(String bank);//
	public List<Banks> retreiveBankNames(); //
	public Map<String,List<String>> populateBankDbDetails(List<Banks> banks);
	public Banks populateBankDbDetail(String bank);
	public List<Chart> showchartPage(String bank);
	public List<List<Chart>> showDepositchart(String bank);
	public List<String> retriveisCriteriaTables(String bankname);
	//RabbitMQ
	List<TableMetaData> buildSqlQuery(QueryData getTableData, String bank, TellerMaster tellerMaster);
	public Map<String,Object> csvDownloadQueue(QueryData queryData, String bank);
	public void updatereportStatus(int id, String inProcess);
	public Map<String,Object> checkQueueIsExist(QueryData queryData, String bank);
	
}
