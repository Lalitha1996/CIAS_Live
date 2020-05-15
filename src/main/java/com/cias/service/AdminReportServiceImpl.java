package com.cias.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.dao.AdminReportDao;
import com.cias.entity.Banks;
import com.cias.entity.Chart;
import com.cias.entity.QueryData;
import com.cias.entity.ReportQueueData;
import com.cias.entity.TableMetaData;
import com.cias.entity.TellerMaster;
import com.cias.rabbitqueue.Events;
import com.cias.utility.ApplicationLabelCache;
import com.cias.utility.CebiConstant;

@Service
public class AdminReportServiceImpl implements AdminReportService {

	@Autowired
	AdminReportDao adminReportDao;
	
	@Autowired
	AdminTableMetaDataService adminTableMetaDataService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private EventPublisherService eventPublisherService;

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
	
	
	@Override
	public List<TableMetaData> buildSqlQuery(QueryData getTableData, String bank, TellerMaster tellerMaster) {
			return adminReportDao.populateDataTable(getTableData, bank, tellerMaster);
	
	}

	@Override
	public Map<String, Object> checkQueueIsExist(QueryData queryData,
			String bank) {
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdfDate.format(new Date());
		Date currentDate1 = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1));
		String beforeDate = sdfDate.format(currentDate1);
		String TBL_CHK_STS_Q = "SELECT reportqueuetable.id ,reportqueuetable.timeadded,cesys003.parameter,cesys003.qry FROM reportqueuetable INNER JOIN cesys003 ON reportqueuetable.queuedataid = cesys003.id WHERE reportqueuetable.timeadded >= ? AND reportqueuetable.timeadded <= ? AND reportqueuetable.status= ? AND reportqueuetable.bank= ? AND cesys003.tbl= ? ";
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		List<QueryData> objList = new ArrayList<QueryData>();
		List<String> qryArr = new ArrayList<>();
		
		try {
			Session session = sessionFactory.openSession();
			connection = ((SessionImpl) session).connection();
			prepareStatement = connection.prepareStatement(TBL_CHK_STS_Q);
			prepareStatement.setString(1, beforeDate);
			prepareStatement.setString(2, currDate);
			prepareStatement.setString(3, "COMPLETED");
			prepareStatement.setString(4, bank);
			prepareStatement.setString(5, queryData.getTable1());
			resultSet = prepareStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					QueryData data = new QueryData();
					data.setTimeAdd(resultSet.getString("timeadded"));
					data.setParameter(resultSet.getString("parameter"));
					data.setId(Integer.parseInt(resultSet.getString("id")));
					qryArr.add(resultSet.getString("qry"));
					objList.add(data);

				}

				List<String> qryParaList = Arrays.asList(queryData
						.getParameter().split(","));
				List<List<String>> paraList = new ArrayList<>();
				for (int i = 0; i < objList.size(); i++) {
					if (queryData.getParameter().length() == objList.get(i)
							.getParameter().length()) {
						List<String> dbParaList = new ArrayList<>();
						dbParaList = Arrays.asList(objList.get(i)
								.getParameter().split(","));
						paraList.add(dbParaList);

					}
				}
				List<Integer> add = new ArrayList<Integer>();

				Collection<String> listOne = qryParaList;
				for (List strlist : paraList) {
					Collection<String> listTwo = strlist;
					listOne.retainAll(listTwo);
					add.add(listOne.size());
				}

				for (int i = 0; i < objList.size(); i++) {
					if (add.get(0) == add.get(1)
							&& (queryData.getQuery().equalsIgnoreCase(qryArr
									.get(0)))
							&& (queryData.getQuery().equalsIgnoreCase(qryArr
									.get(1)))) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String currt = sdf.format(new Date());
						String newTime = objList.get(i).getTimeAdd();
						Date d1 = sdfDate.parse(newTime);
						Date d2 = sdfDate.parse(currt);
						long diff = d2.getTime() - d1.getTime();
						long diffMinutes = 60 - diff / (60 * 1000) % 60;
						String msg = " The Queue is Already Exist With QueueID : "
								+ objList.get(i).getId()
								+ "."
								+ "\n Added at : "
								+ objList.get(i).getTimeAdd()
								+ ". \n Please Try After : "
								+ diffMinutes
								+ " Min.";
						String ss = Integer.toString(i);
						map.put(ss, msg);
					} else {
						map.put("k1", "NOTEXIST");
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) { /* ignored */
				}
			}
			if (prepareStatement != null) {
				try {
					prepareStatement.close();
				} catch (SQLException e) { /* ignored */
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) { /* ignored */
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> csvDownloadQueue(QueryData queryData, String bank) {
		Map<String, Object> map = new HashMap<>();
		ReportQueueData datQueueData = new ReportQueueData();
		datQueueData.setQueuedataid(queryData);
		datQueueData.setStatus("IN QUEUE");
		datQueueData.setTimeadded(new Date());
		datQueueData.setBank(bank.trim());
		int i = adminReportDao.addReportQueueData(datQueueData);
		String msg = "File is Added in Queue Succesfully with  ID :" + i;
		
		try {
			if (queryData.getReporttype().equalsIgnoreCase("csv")) {
				eventPublisherService.publishEvent(Events.CSV, i);
			} else if (queryData.getReporttype().equalsIgnoreCase("csvpipe")) {
				eventPublisherService.publishEvent(Events.CSVPIPE, i);
			} else if (queryData.getReporttype().equalsIgnoreCase("xls")) {
				eventPublisherService.publishEvent(Events.EXCEL, i);
			} else if(queryData.getReporttype().equalsIgnoreCase("pdf")){
				eventPublisherService.publishEvent(Events.PDF, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("msg", msg);
		return map;
	}

	@Override
	public void updatereportStatus(int id, String inProcess) {
		adminReportDao.updatereportStatus(id, inProcess);
	}
	
}
