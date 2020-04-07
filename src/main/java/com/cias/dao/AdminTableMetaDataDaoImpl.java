package com.cias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.entity.ApplicationLabel;
import com.cias.entity.ColumnNames;
import com.cias.entity.RequiredField;
import com.cias.entity.TableMetaData;
import com.cias.utility.ApplicationLabelCache;
import com.cias.utility.CebiConstant;

@Repository
@Transactional
public class AdminTableMetaDataDaoImpl implements AdminTableMetaDataDao {

	private static final Logger logger = Logger.getLogger(AdminTableMetaDataDaoImpl.class);

	@Autowired
	ApplicationLabelDao applicationLabelDao;

	@Autowired
	CebiConstant cebiConstant;

	@Autowired
	AdminReportDao adminReportDao;
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	
	@Override
	public List<TableMetaData> retrieveDbTables(String bank) {
		List<TableMetaData> tableNames = new ArrayList<TableMetaData>();
		Connection connection = null;
		ResultSet resultSet = null;
		TableMetaData tableMetaData = null;
		PreparedStatement prepareStatement=null;
		Session session = cebiConstant.getCurrentSession(bank);

		connection = ((SessionImpl) session).connection();
		List<ApplicationLabel> labels=applicationLabelDao.retrieveAllLabels();
		try {
		    prepareStatement = connection.prepareStatement("SELECT tl_tab FROM public.table_list");
			resultSet = prepareStatement.executeQuery();
			tableMetaData = new TableMetaData();
			while (resultSet.next()) {
				tableMetaData = new TableMetaData();
				if(!resultSet.getString("tl_tab").equalsIgnoreCase("NPA_CUSTOMERS")){
				tableMetaData.setTableName(resultSet.getString("tl_tab").trim());
				setTableLabel(labels,resultSet.getString("tl_tab").trim(),tableMetaData);
				tableNames.add(tableMetaData);
				}
			}
		} catch (SQLException e) {
			logger.info("Exception in retrieveDbTables() Method:: " + e.getMessage());
			e.getMessage();
		}finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (prepareStatement != null) {
		        try {
		        	prepareStatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		if(tableNames.size()>0){
		tableNames.get(0).setAppLabels(labels);
		}
		return tableNames;
	}
	
	@Override
	public Map<String, String> retrieveDbTable(String bank) {
		Map<String, String> map = new HashMap<>();
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement prepareStatement = null;
		Session session = cebiConstant.getCurrentSession(bank);

		connection = ((SessionImpl) session).connection();
		try {
			prepareStatement = connection.prepareStatement("SELECT tl_tab FROM public.table_list");
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				/*if (!resultSet.getString("view_name").equalsIgnoreCase("NPA_CUSTOMERS")) {*/
					map.put(resultSet.getString("tl_tab"), resultSet.getString("tl_tab"));
				/*}*/
 
			}
		} catch (SQLException e) {
			logger.info("Exception in retrieveDbTables() Method:: " + e.getMessage());
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
	
	private void setTableLabel(List<ApplicationLabel> labels, String string, TableMetaData tableMetaData) {
		if (addLabels(labels, string) != null) {
			tableMetaData.setName(addLabels(labels, string));
		} else {
			tableMetaData.setName(string);
		}

	}


	public String addLabels(List<ApplicationLabel> labels, String tableName) {
		String label = null;
		for (ApplicationLabel lbl : labels) {
			if (lbl.getLabelCode().equalsIgnoreCase(tableName)) {
				label = lbl.getAppLabel();
				break;
			}
		}
		return label;

	}

	@Override
	public List<ColumnNames> retrieveTableColumns(String table, String bank) {
		String TBL_GET_CLM_QRY = "SELECT COLUMN_NAME,DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = " + "'" + table + "' order by COLUMN_NAME";
		ColumnNames columnName = null;
		Connection connection = null;
		PreparedStatement prepareStatement=null;
		ResultSet resultSet = null;
		Session session = cebiConstant.getCurrentSession(bank);
		List<ColumnNames> names = new ArrayList<>();
		try {
			connection = ((SessionImpl) session).connection();
		    prepareStatement = connection.prepareStatement(TBL_GET_CLM_QRY);
			resultSet = prepareStatement.executeQuery();
			List<ApplicationLabel> labels = applicationLabelDao.retrieveAllLabels();
			while (resultSet.next()) {
				if (!resultSet.getString("COLUMN_NAME").equalsIgnoreCase("VAR_AREA") && !resultSet.getString("COLUMN_NAME").equalsIgnoreCase("OD_VISA_AREA")) {
					columnName = new ColumnNames();
					columnName.setDataType(resultSet.getString("DATA_TYPE"));
					columnName.setName(table+"."+resultSet.getString("COLUMN_NAME"));
					for (ApplicationLabel lbl : labels) {
						if (lbl.getLabelCode().equalsIgnoreCase(columnName.getName().trim())) {
							names.add(columnName);
					   }
					}
				}
			}
			//List<TableMetaData> joinTables=retriveJoinTables(table);
			//names.get(0).setJoinTables(joinTables);
			populateRequiredFiels(table, names);
			populatekeyFiels(table, names);
			names.get(0).setAppLabels(labels);
		} catch (Exception e) {
			logger.info("Exception in retrieveTableColumns() Method :: " + e.getMessage());
			e.printStackTrace();
		}finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (prepareStatement != null) {
		        try {
		        	prepareStatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		return names;
	}

	protected void populateRequiredFiels(String table, List<ColumnNames> names) {
		RequiredField fields = adminReportDao.populateFields(table);
		if (fields.getFilterfield() != null) {
			for (String str : fields.getFilterfield().split(",")) {
				for (ColumnNames columnName : names) {
					if (columnName.getName().equalsIgnoreCase(str.trim())){
						columnName.setRequired("Y");
						break;
					}
				}
			}
		}
	}
	
	protected void populatekeyFiels(String table, List<ColumnNames> names) {
		RequiredField fields = adminReportDao.populateFields(table);
		if (fields.getFiled() != null) {
			for (String str : fields.getFiled().split(",")) {
				for (ColumnNames columnName : names) {
					if (columnName.getName().equalsIgnoreCase(str.trim())){
						columnName.setKeyField("Y");
						break;
					}
				}
			}
		}
	}
	
	/*public List<TableMetaData> retriveJoinTables(String table){
		logger.info("retrieveAllLabels start time:: " + System.currentTimeMillis());
		List<String> joins = null;
		TableMetaData tbl=null;
		List<ApplicationLabel> labels=applicationLabelDao.retrieveAllLabels();
		Map<String, List<String>> cache = ApplicationLabelCache.getJoinViewInstance();
		List<TableMetaData> tables = new ArrayList<TableMetaData>();
		
		joins=adminReportDao.joinsList();
		
		
		for(String tbj:joins){
			String tab=null;
			tbl = new TableMetaData();
			if(tbj.trim().contains(table)){
				tab=tbj.substring(tbj.indexOf("=")+1,tbj.lastIndexOf("."));
				if(tab.equals(table)) {
					tab = tbj.substring(0, tbj.indexOf("."));
				}
				tbl.setTableName(tab);
				setTableLabel(labels, tab, tbl);
				tables.add(tbl);
			}
		}
			
		return tables;
	}*/
}
