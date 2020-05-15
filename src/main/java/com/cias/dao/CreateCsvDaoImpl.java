package com.cias.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.opencsv.CSVWriter;

@Repository
@Transactional
public class CreateCsvDaoImpl extends PdfUtils implements CreateCsvDao {

	private static final Logger logger = Logger.getLogger(PdfUtils.class);

	@Autowired
	CebiConstant cebiConstant;
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public byte[] downloadCsv(QueryData queryData, String bank) {
		byte[] outArray = null;
		 byte[] bytesArray = null;
		Session session = cebiConstant.getCurrentSession(bank);
		ResultSet resultSet = null;
		String parameter = "";
		String columns = ""; 
		String criteria = "";
		PreparedStatement prepareStatement = null;
		Connection connection = null;

		parameter = queryData.getParameter().trim().length() > 0 ? queryData.getParameter() : "";
		criteria = queryData.getQuery().trim().length() > 0 ? queryData.getQuery() : "";
		columns = queryData.getColumnNames().trim().length() > 0 ? queryData.getColumnNames() : "";
		String query = populateQuery(queryData, parameter, criteria);
		StringBuilder buffer = new StringBuilder();
		try {

			connection = ((SessionImpl) session).connection();
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();

			String lstparam = parameter.substring(0, (parameter.length() - 1));
			List<String> dbColumns = Arrays.asList(lstparam.split(","));
			List<String> columnLables = Arrays.asList(columns.split(","));

			for (String lbl : columnLables) {
				buffer.append(lbl + CebiConstant.COMMA);
			}
			File file = new File("cebi.csv");
			CSVWriter writer = new CSVWriter(new FileWriter(file));
			writer.writeNext(columns.split(","));
			writer.writeAll(resultSet, false);
			writer.close();
			bytesArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();
			/*BufferedReader bufferedReader =new BufferedReader(reader);
			
			byte[] bs =bufferedReader.*/
			/*while (resultSet.next()) {
				buffer.append(CebiConstant.NEW_LINE);
				for (String label : dbColumns) {
					label = label.contains("(") && label.contains(")") ? label.substring(label.indexOf('(') + 1, label.indexOf(')')) : label;
					if (resultSet.getString(label) == null || resultSet.getString(label).isEmpty())
						buffer.append(StringUtils.rightPad(CebiConstant.EMPTY_SPACE,label.length()) + CebiConstant.COMMA);
					else
						buffer.append(StringUtils.rightPad(resultSet.getString(label).trim(),resultSet.getString(label).trim().length()-label.length())+ CebiConstant.COMMA);
				}
			}*/
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}catch (OutOfMemoryError error) {
			throw new ConnectionException("Failed to allocate Max memory...!");
		}finally {
			closeConnection(resultSet,connection,prepareStatement);
		}
		outArray = buffer.toString().getBytes();

		return bytesArray;
	}

	@Override
	public byte[] downloadCsvPipeSeperator(QueryData queryData, String bank) {
		byte[] outArray = null;
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
		 if(queryData.getTable2().isEmpty()){ 
			 
			 query = populateQuery(queryData, parameter, criteria);
		}else{
			 
			query = populateJoinQuery(queryData, parameter, criteria);
		}
		
		StringBuilder buffer = new StringBuilder();
		try {

			connection = ((SessionImpl) session).connection();
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();

			String lstparam = parameter.substring(0, (parameter.length() - 1));
			List<String> dbColumns = Arrays.asList(lstparam.split(","));
			List<String> columnLables = Arrays.asList(columns.split(","));

			for (String lbl : columnLables) {
				buffer.append(lbl + CebiConstant.PIPELINE);
			}
			while (resultSet.next()) {
				buffer.append(CebiConstant.NEW_LINE);
				for (String label : dbColumns) {
					label = label.contains("(") && label.contains(")") ? label.substring(label.indexOf("As") + 3, label.length()) : label;
					label = label.contains("AS") && !label.contains(")") ? label.substring(label.indexOf("AS")+3, label.length()) : label;
					label.trim();
					if (resultSet.getString(label) == null || resultSet.getString(label).isEmpty())
						buffer.append(StringUtils.rightPad(CebiConstant.EMPTY_SPACE,label.length()) + CebiConstant.PIPELINE);
					else
						buffer.append(StringUtils.rightPad(resultSet.getString(label).trim(),resultSet.getString(label).trim().length()-label.length())+ CebiConstant.PIPELINE);

				}
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} finally {
			closeConnection(resultSet,connection,prepareStatement);
		}
		outArray = buffer.toString().getBytes();

		return outArray;
	}

	
	//RabbitMQ
	
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
