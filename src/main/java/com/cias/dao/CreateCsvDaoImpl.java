package com.cias.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.entity.ReportQueueData;
import com.cias.entity.QueryData;
import com.cias.utility.CebiConstant;
import com.cias.utility.ConnectionException;
import com.cias.utility.PdfUtils;

@Repository
@Transactional
public class CreateCsvDaoImpl extends PdfUtils implements CreateCsvDao {

	private static final Logger logger = Logger.getLogger(PdfUtils.class);

	@Autowired
	CebiConstant cebiConstant;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	AdminReportDao adminreportdao;

	@Override
	public void downloadCsv(QueryData queryData, String bank,String csvFileLoc) {
		
		Session session = cebiConstant.getCurrentSession(bank);
		ResultSet resultSet = null;
		String parameter = "";
		String columns = ""; 
		String criteria = "";
		PreparedStatement prepareStatement = null;
		Connection connection = null;
		String query=null;

		parameter = queryData.getParameter().trim().length() > 0 ? queryData.getParameter() : "";
		criteria = queryData.getQuery().trim().length() > 0 ? queryData.getQuery() : "";
		columns = queryData.getColumnNames().trim().length() > 0 ? queryData.getColumnNames() : "";
		  query=queryData.getTable2().isEmpty()?populateQuery(queryData, parameter, criteria):populateJoinQuery(queryData, parameter, criteria);
		  System.out.println(query);
		  logger.info("Query Generated During CSV DOWNLOAD :: " + query);
		
		try {
			connection = ((SessionImpl) session).connection();
			connection.setAutoCommit(false);
			prepareStatement = connection.prepareStatement(query,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			prepareStatement.setFetchSize(5000);
			resultSet = prepareStatement.executeQuery();
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			StringBuilder buffer = new StringBuilder();
			
			List<String> ColumnLables = Arrays.asList(columns.split(","));
			List<String> dbColumns = new ArrayList<String>();
			
			for (int i = 1; i <=columnCount; i++) {
				buffer.append(ColumnLables.get(i-1) + CebiConstant.COMMA);
				dbColumns.add(rsmd.getColumnName(i));
			}

			int i = 0;
			int j = 1;
			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter(csvFileLoc, true);
			bw = new BufferedWriter(fw);
			bw.append(buffer.toString());
			
			while (resultSet.next()) {
				
				bw.append(CebiConstant.NEW_LINE);
				for (String label:dbColumns) {
					label = label.contains("(") && label.contains(")") ? label.substring(label.indexOf('(') + 1, label.indexOf(')')) : label;
					label.trim();
					if (resultSet.getString(label) == null || resultSet.getString(label).isEmpty()) {
						bw.append(StringUtils.rightPad(CebiConstant.EMPTY_SPACE, label.length())).append("    ,    ");
					} else
						bw.append(StringUtils.rightPad(resultSet.getString(label).trim(), resultSet.getString(label).trim().length() - label.length())).append("    ,    ");
				}
				i++;
				
				if (i % (j * 10000) == 0) {
					j++;
					bw.flush();
				}
			}
			ReportQueueData reportQueueData=adminreportdao.getReportQueueData(queryData.getReportDataId());
			reportQueueData.setTotalCount(i+"");
			adminreportdao.updateReportQueueData(reportQueueData);
			bw.close();
			fw.close();
		
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (OutOfMemoryError error) {
			throw new ConnectionException("Failed to allocate Max memory...!");
		} finally {
			closeConnection(resultSet, connection, prepareStatement);
		}

	}

	@Override
	public void downloadCsvPipeSeperator(QueryData queryData, String bank,String csvFileLoc) {
		
		Session session = cebiConstant.getCurrentSession(bank);
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String parameter = "";
		String columns = "";
		String criteria = "";
		String query=null;
		parameter = queryData.getParameter().trim().length() > 0 ? queryData.getParameter() : "";
		criteria = queryData.getQuery().trim().length() > 0 ? queryData.getQuery() : "";
		columns = queryData.getColumnNames().trim().length() > 0 ? queryData.getColumnNames() : "";
		
		// by Mskh
		query = queryData.getTable2().isEmpty() ? populateQuery(queryData, parameter, criteria):populateJoinQuery(queryData, parameter, criteria);
		logger.info("Query Generated During CSV PIPE DOWNLOAD :: " + query);
		System.out.println(query);
		
		
		try {
			connection = ((SessionImpl) session).connection();
			connection.setAutoCommit(false);
			prepareStatement = connection.prepareStatement(query,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			prepareStatement.setFetchSize(5000);
			resultSet = prepareStatement.executeQuery();

			String lstparam = parameter.substring(0, (parameter.length() - 1));
			List<String> dbColumns = new ArrayList<String>();
			List<String> columnLables = Arrays.asList(columns.split(","));

			StringBuilder buffer = new StringBuilder();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			for (int i=1;i<=columnCount;i++) {
				buffer.append(columnLables.get(i-1) + CebiConstant.PIPELINE);
				dbColumns.add(rsmd.getColumnName(i));
			}
			
			int i = 0;
			int j = 1;
			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter(csvFileLoc, true);
			bw = new BufferedWriter(fw);
			bw.append(buffer.toString());
			
			while (resultSet.next()) {
				buffer.append(CebiConstant.NEW_LINE);
				for (String label : dbColumns) {
					label = label.contains("(") && label.contains(")") ? label.substring(label.indexOf('(') + 1, label.indexOf(')')) : label;
					label.trim();
					if (resultSet.getString(label) == null || resultSet.getString(label).isEmpty())
						bw.append(StringUtils.rightPad(CebiConstant.EMPTY_SPACE,label.length()) + CebiConstant.PIPELINE);
					else
						bw.append(StringUtils.rightPad(resultSet.getString(label).trim(),resultSet.getString(label).trim().length()-label.length())+ CebiConstant.PIPELINE);

				}
				i++;
				
				if (i % (j * 10000) == 0) {
					j++;
					bw.flush();
				}
			}
			
			ReportQueueData reportQueueData=adminreportdao.getReportQueueData(queryData.getReportDataId());
			reportQueueData.setTotalCount(i+"");
			adminreportdao.updateReportQueueData(reportQueueData);
			bw.close();
			fw.close();
			
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			closeConnection(resultSet, connection, prepareStatement);
		}
	}

	@Override
	public int addReportQueueData(ReportQueueData reportQueueData) {
		return (int) sessionFactory.getCurrentSession().save(reportQueueData);
	}

	@Override
	@Transactional
	public ReportQueueData getReportQueueData(int id) {
		return (ReportQueueData) sessionFactory.getCurrentSession().get(ReportQueueData.class, id);
	}
	
	
	protected void closeConnection(ResultSet resultSet, Connection connection,
			PreparedStatement prepareStatement) {
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
}
