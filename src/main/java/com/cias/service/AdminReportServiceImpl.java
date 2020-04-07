package com.cias.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.AdminReportDao;
import com.cias.entity.Banks;
import com.cias.entity.Chart;
import com.cias.entity.QueryData;
import com.cias.entity.TableMetaData;
import com.cias.entity.TellerMaster;
import com.cias.utility.ApplicationLabelCache;

@Service
public class AdminReportServiceImpl implements AdminReportService {

	@Autowired
	AdminReportDao adminReportDao;
	
	@Autowired
	AdminTableMetaDataService adminTableMetaDataService;

	public List<TableMetaData> getTableData(QueryData getTableData, String bank,TellerMaster master) {
		return adminReportDao.populateDataTable(getTableData,bank,master);
	}

	public List<TableMetaData> populateDbTables(String bank) {
		List<TableMetaData> tables = null;
		Map<String, List<TableMetaData>> cache = ApplicationLabelCache.getViewsInstance();
		if (cache.get("views") == null) {
			tables = adminTableMetaDataService.retrieveDbTables(bank);
			cache.put("views", tables);
		} else {
			for (Map.Entry<String, List<TableMetaData>> entry : cache.entrySet()) {
				tables = entry.getValue();
			}
		}
		return tables;
	}

	@Override
	public List<Banks> retreiveBankNames() {
		List<Banks> banks=null;
		banks=adminReportDao.retreiveBankNames();
		return banks;
	}

	@Override
	public Map<String, List<String>> populateBankDbDetails(List<Banks> banks) {
		List<String> list =null;
		Map<String, List<String>> bankDetails = ApplicationLabelCache.getBankDbDetailsInstance();
		if (banks != null && banks.size() > 0) {
			for (Banks bank : banks) {
				list= new ArrayList<String>();
				list.add(bank.getDriverClass());
				list.add(bank.getDatabaseUrl());
				list.add(bank.getUsername());
				list.add(bank.getPassword());
				bankDetails.put(bank.getBankCode(), list);
			}
		}
		return bankDetails;
	}
	@Override
	public Banks populateBankDbDetail(String bank) {
		return adminReportDao.retreiveDbConnection(bank);
	}
	
	@Override
    public List<Chart> showchartPage(String bankname) {
            return adminReportDao.createShowChart(bankname).stream()
                            .map(data -> new Chart((String) data[1], (BigDecimal) data[0]))
                            .collect(Collectors.toList());        
    }
	
	//Landing charts
	public List<List<Chart>> showDepositchart(String bankname){
		List<List<Object[]>> createChartReport = adminReportDao.createChartReport(bankname);
		List<List<Chart>> chart =new ArrayList<List<Chart>>();
		
		createChartReport.forEach(list->{
			 List<Chart> collect = list.stream().map(data -> new Chart((String) data[0], 
		    		 (BigDecimal) data[1])).collect(Collectors.toList());
		     chart.add(collect); 
		     });
		return chart;
		}

	@Override
	public List<String> retriveisCriteriaTables(String bankname) {
		return adminReportDao.retriveisCriteriaTables(bankname);
	}
	
}
