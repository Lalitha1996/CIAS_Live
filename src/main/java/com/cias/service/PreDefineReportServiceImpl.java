package com.cias.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import com.cias.dao.AdminReportDao;
import com.cias.entity.AppMessages;
import com.cias.entity.ApplicationLabel;
import com.cias.entity.ColumnNames;
import com.cias.entity.PreDefineReport;
import com.cias.entity.QueryData;
import com.cias.entity.ReportQueueData;
import com.cias.entity.TableMetaData;
import com.cias.entity.TellerMaster;
import com.cias.service.ApplicationLabelService;
import com.cias.utility.Block;
import com.cias.utility.Board;
import com.cias.utility.CebiConstant;
import com.cias.utility.MappingConstant;
import com.cias.utility.Table;

@Transactional
@Service
public class PreDefineReportServiceImpl implements PredefineReportService {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	CebiConstant cebiConstant;

	@Autowired
	ApplicationLabelService applicationLabelService;

	@Autowired
	AdminReportDao adminReportDao;

	private static final Logger logger = Logger
			.getLogger(PreDefineReportServiceImpl.class);

	@Override
	public String saveDefReportObj(PreDefineReport PreDefineReport) {
		Session session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		int stId = (Integer) session.save(PreDefineReport);
		tx1.commit();
		return "Query Saved Successfully with QryID : [ " + stId + " ]";
	}

	@Override
	public List<PreDefineReport> getQryListByBank(String bankCode) {
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(PreDefineReport.class);
		crit.add(Restrictions.eq("BankCod", bankCode));
		crit.add(Restrictions.eq("sts", "EXIST"));
		@SuppressWarnings("unchecked")
		List<PreDefineReport> results = crit.list();
		return results;

	}

	@Override
	public boolean isQuerySaved(PreDefineReport preDefineReport) {
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(PreDefineReport.class);
		crit.add(Restrictions.eq("BankCod", preDefineReport.getBankCod()));
		crit.add(Restrictions.eq("sts", "EXIST"));
		@SuppressWarnings("unchecked")
		List<PreDefineReport> objList = crit.list();
		List<Boolean> isExistList = new ArrayList<Boolean>();
		for (PreDefineReport pdr : objList) {
			if (pdr.getSaveQuery().trim()
					.equals(preDefineReport.getSaveQuery().trim())) {
				isExistList.add(false);
			}
		}
		int i = isExistList.size();
		if (i == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String deleteThisObject(PreDefineReport preDefineReport) {
		Session session = sessionFactory.openSession();
		PreDefineReport pdfr = (PreDefineReport) session.load(
				PreDefineReport.class, preDefineReport.getId());
		pdfr.setSts(preDefineReport.getSts());
		Transaction tx1 = session.beginTransaction();
		session.saveOrUpdate(pdfr);
		tx1.commit();
		return "| " + preDefineReport.getSaveQuery() + " |"
				+ " : Query is Removed...!";
	}

	@Override
	public List<TableMetaData> getTableDataListByBank(
			PreDefineReport preDefineReport, String bankCode) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		ColumnNames field;
		List<ColumnNames> names = new ArrayList<>();
		List<AppMessages> appMessages = new ArrayList<>();
		List<TableMetaData> data = new ArrayList<>();
		TableMetaData tableMetaData = new TableMetaData();
		List<ApplicationLabel> labels = null;
		try {
			session = cebiConstant.getCurrentSession(bankCode);
			connection = ((SessionImpl) session).connection();
			connection.setAutoCommit(false);
			labels = applicationLabelService.retrieveAllLabels();
			SimpleDateFormat formatter1 = new SimpleDateFormat(
					"ddMMyyyy HH:mm:ss");
			Date date1 = new Date();
			logger.info("start ---" + formatter1.format(date1));
			if (tableMetaData.getAppMessage() == null
					|| tableMetaData.getAppMessage().size() == 0) {
				query = preDefineReport.getSaveQuery().replace(";", "");
				System.out.println(query);
				prepareStatement = connection.prepareStatement(query);
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					tableMetaData = new TableMetaData();
					ResultSetMetaData rsmd = resultSet.getMetaData();
					int columnCount = rsmd.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String label = rsmd.getColumnName(i);
						label = label.contains("(") && label.contains(")") ? label
								.substring(label.indexOf('(') + 1,
										label.indexOf(')')) : label;
						field = new ColumnNames();
						if (resultSet.getString(label) == null
								|| resultSet.getString(label) == "")
							field.setField("");
						else
							field.setField(resultSet.getString(label));
						field.setName(addApplicationLabels(label, labels));
						names.add(field);
						tableMetaData.setNames(names);
					}
				}

				tableMetaData.setAppLabels(labels);
				data.add(tableMetaData);
				validateTableData(data, appMessages);
			}
		} catch (Exception e) {
			TableMetaData tableDataErr = new TableMetaData();
			tableDataErr.setName("Error : " + e.getMessage());
			data.add(tableDataErr);
			logger.info(e.getMessage());
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
		return data;
	}

	protected String addApplicationLabels(String label,
			List<ApplicationLabel> labels) {
		for (ApplicationLabel lbl : labels) {
			if (lbl.getLabelCode().equalsIgnoreCase(label)) {
				label = lbl.getAppLabel();
				break;
			}
		}
		return label;

	}

	private void validateTableData(List<TableMetaData> data,
			List<AppMessages> appMessages) {
		for (TableMetaData tableMetaData : data) {
			if (tableMetaData.getNames() == null
					|| tableMetaData.getNames().isEmpty()) {
				appMessages.add(new AppMessages("NO_RESULTS",
						CebiConstant.NO_RESULT));
				tableMetaData.setAppMessage(appMessages);
			}
		}
	}

	@Override
	public String getQueryString(String qry) {
		Pattern p = Pattern
				.compile(
						"from\\s+(?:\\w+\\.)*(\\w+)($|\\s+[WHERE,JOIN,START\\s+WITH,ORDER\\s+BY,GROUP\\s+BY])",
						Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(qry);
		String tableName = null;
		while (m.find())
			tableName = m.group(1);
		return tableName;
	}

	@Override
	public boolean getTableNameIsExist(List<TableMetaData> table,
			String tableName) {
		List<String> tableList = new ArrayList<String>();
		boolean sts = false;
		for (TableMetaData tableMetaData : table) {
			tableList.add(tableMetaData.getTableName());
		}
		for (String tblName : tableList) {
			if (tblName.equalsIgnoreCase(tableName)) {
				sts = true;
				break;
			}
		}
		return sts;
	}

	public List<TableMetaData> getDataIntoFile(QueryData getTableData,
			String bank) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		List<AppMessages> appMessages = new ArrayList<>();
		List<TableMetaData> data = new ArrayList<>();
		TableMetaData tableMetaData = new TableMetaData();
		List<ApplicationLabel> labels = null;
		String filename = null;
		File fzip = null;
		try {
			session = cebiConstant.getCurrentSession(bank);
			connection = ((SessionImpl) session).connection();
			connection.setAutoCommit(false);
			labels = applicationLabelService.retrieveAllLabels();
			SimpleDateFormat formatter1 = new SimpleDateFormat(
					"ddMMyyyy HH:mm:ss");
			Date date1 = new Date();
			logger.info("start ---" + formatter1.format(date1));
			query = getTableData.getFinalQry();
			getTableData.setTable1(getQueryString(query));
			if ("csvpr".equalsIgnoreCase(getTableData.getReporttype())) {
				statement = (Statement) connection.createStatement(
						ResultSet.TYPE_FORWARD_ONLY, // or
						ResultSet.CONCUR_READ_ONLY);
				statement.setFetchSize(5000);
				resultSet = statement.executeQuery(query);
				StringBuilder buffer = new StringBuilder();
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();
				List<String> colummnName = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					buffer.append(rsmd.getColumnName(i) + " , ");
					colummnName.add(rsmd.getColumnName(i));
				}
				int i = 0;
				int j = 1;
				BufferedWriter bw = null;
				FileWriter fw = null;

				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
				Date date = new Date();
				filename = formatter.format(date) + "_"
						+ getTableData.getTable1() + "_"
						+ getTableData.getReportDataId() + ".csv";
				String csvFileLoc = filename;
				fw = new FileWriter(csvFileLoc, true);
				bw = new BufferedWriter(fw);
				bw.append(buffer.toString());
				while (resultSet.next()) {
					bw.append(CebiConstant.NEW_LINE);
					for (int k = 0; k < colummnName.size(); k++) {
						String label = colummnName.get(k);
						label = label.contains("(") && label.contains(")") ? label
								.substring(label.indexOf('(') + 1,
										label.indexOf(')')) : label;
						if (resultSet.getString(label) == null
								|| resultSet.getString(label).isEmpty()) {
							bw.append(
									StringUtils.rightPad(
											CebiConstant.EMPTY_SPACE,
											label.length()))
									.append("    ,    ");
						} else
							bw.append(
									StringUtils.rightPad(
											resultSet.getString(label).trim(),
											resultSet.getString(label).trim()
													.length()
													- label.length())).append(
									"    ,    ");
						;
					}
					i++;
					if (i % (j * 10000) == 0) {
						j++;
						bw.flush();
					}
				}
				bw.close();
				Date enddate = new Date();
				ReportQueueData reportQueueData = adminReportDao.getReportQueueData(getTableData.getReportDataId());
				reportQueueData.setFileName(filename);
				reportQueueData.setTimecomplete(enddate);
				reportQueueData.setTimetake(enddate.getTime() - date1.getTime() + "");
				reportQueueData.setStatus(CebiConstant.COMPLETED);
				reportQueueData.setTotalCount(i + "");
				adminReportDao.updateReportQueueData(reportQueueData);
				String zipFileName = MappingConstant.BANK_REPORT_LOCATION+ filename + ".zip";
				fzip = new File(zipFileName);
				ZipOutputStream zippedOut = new ZipOutputStream(new FileOutputStream(fzip));
				FileSystemResource resource = new FileSystemResource(csvFileLoc);
				ZipEntry e = new ZipEntry(resource.getFilename());
				e.setSize(resource.contentLength());
				e.setTime(System.currentTimeMillis());
				zippedOut.putNextEntry(e);
				StreamUtils.copy(resource.getInputStream(), zippedOut);
				zippedOut.closeEntry();
				zippedOut.finish();
			} else if ("txtpr".equalsIgnoreCase(getTableData.getReporttype())) {

				int lgth = 0;
				List<String> stringList = new ArrayList<>();
				statement = (Statement) connection.createStatement(
						ResultSet.TYPE_FORWARD_ONLY, // or
						ResultSet.CONCUR_READ_ONLY);
				statement.setFetchSize(5000);
				resultSet = statement.executeQuery(query);
				StringBuilder buffer = new StringBuilder();
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();
				List<String> colummnName = new ArrayList<String>();
				List<Integer> colTitleSize = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					colummnName.add(rsmd.getColumnName(i));
					colTitleSize.add(rsmd.getColumnName(i).length());
				}
				final List<List<String>> rowList = new LinkedList<List<String>>();
				int i = 0;
				List<Integer> arrySize = new ArrayList<>();
				while (resultSet.next()) {
					final List<String> columnList = new LinkedList<String>();
					rowList.add(columnList);
					for (int column = 1; column <= columnCount; ++column) {
						final Object value = resultSet.getObject(column);
						columnList.add(String.valueOf(value).trim());
					}
					i++;
				}

				Map<String, List<Integer>> sizeMap = new HashMap<>();

				for (int column = 0; column < columnCount; ++column) {
					List<Integer> len = new ArrayList<Integer>();
					for (int row = 0; row < rowList.size(); ++row) {
						List<String> al = rowList.get(row);
						String size = al.get(column).trim();
						len.add(size.length());
					}
					sizeMap.put(colummnName.get(column), len);

				}

				for (int column = 0; column < columnCount; ++column) {
					List<Integer> sz = sizeMap.get(colummnName.get(column));
					int maxSizeCol = Collections.max(sz);
					arrySize.add(maxSizeCol);
				}

				List<Integer> colWd1 = new ArrayList<>();
				for (int column = 0; column < columnCount; ++column) {
					if (arrySize.get(column) < colTitleSize.get(column)) {
						colWd1.add(colTitleSize.get(column) + 1);
					} else {
						colWd1.add(arrySize.get(column) + 1);
					}

				}
				Board board = new Board(1600);
				Table table = new Table(board, 1600, colummnName, rowList);
				table.setGridMode(Table.GRID_COLUMN);
				List<Integer> colAl = new ArrayList<>();
				for (int column = 1; column <= columnCount; ++column) {
					colAl.add(Block.DATA_TOP_LEFT);
				}
				// List<Integer> colWidthsList = Arrays.asList(20, 14, 13,
				// 14, 14);
				// List<Integer> colAlignList =
				// Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER,
				// Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER);
				table.setColWidthsList(colWd1);
				table.setColAlignsList(colAl);

				Block tableBlock = table.tableToBlocks();
				board.setInitialBlock(tableBlock);
				board.build();
				String tableString = board.getPreview();
				String tabStr = tableString.replaceAll("null", "----");
				/*
				 * System.out.println(tableString.length());
				 * System.out.println(tableString);
				 */
				String[] lines = tabStr.split("\\s*\\r?\\n\\s*");
				List al = Arrays.asList(lines);

				StringBuilder buff = new StringBuilder();
				for (int s = 0; s < al.size(); s++) {
					buff.append(al.get(s));
					buff.append("\r\n");
				}
				byte[] output = String.valueOf(buff).getBytes();

				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
				Date date = new Date();
				filename = formatter.format(date) + "_"
						+ getTableData.getTable1() + "_"
						+ getTableData.getReportDataId() + ".txt";
				File txtFileLoc = new File(MappingConstant.BANK_REPORT_LOCATION
						+ filename);

				try (FileOutputStream fos = new FileOutputStream(txtFileLoc)) {
					fos.write(output);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				Date enddate = new Date();
				ReportQueueData reportQueueData = adminReportDao
						.getReportQueueData(getTableData.getReportDataId());
				reportQueueData.setFileName(filename);
				reportQueueData.setTimecomplete(enddate);
				reportQueueData.setTimetake(enddate.getTime() - date1.getTime()
						+ "");
				reportQueueData.setStatus(CebiConstant.COMPLETED);
				reportQueueData.setTotalCount(i + "");
				adminReportDao.updateReportQueueData(reportQueueData);

				String zipFileName = MappingConstant.BANK_REPORT_LOCATION
						+ filename + ".zip";
				fzip = new File(zipFileName);
				ZipOutputStream zippedOut = new ZipOutputStream(
						new FileOutputStream(fzip));
				FileSystemResource resource = new FileSystemResource(txtFileLoc);
				ZipEntry e = new ZipEntry(resource.getFilename());
				e.setSize(resource.contentLength());
				e.setTime(System.currentTimeMillis());
				zippedOut.putNextEntry(e);
				StreamUtils.copy(resource.getInputStream(), zippedOut);
				zippedOut.closeEntry();
				zippedOut.finish();

			} else {
				statement = (Statement) connection.createStatement(
						ResultSet.TYPE_FORWARD_ONLY, // or
						ResultSet.CONCUR_READ_ONLY);
				statement.setFetchSize(5000);
				resultSet = statement.executeQuery(query);
				StringBuilder buffer = new StringBuilder();
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();
				List<String> colummnName = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					buffer.append(rsmd.getColumnName(i) + " , ");
					colummnName.add(rsmd.getColumnName(i));
				}
				int i = 0;

				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
				Date date = new Date();
				filename = formatter.format(date) + "_"
						+ getTableData.getTable1() + "_"
						+ getTableData.getReportDataId() + ".xlsx";
				String csvFileLoc = filename;

				List<List<String>> rowList = new LinkedList<List<String>>();
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Report sheet");
				while (resultSet.next()) {
					final List<String> columnList = new LinkedList<String>();
					rowList.add(columnList);

					for (int column = 1; column <= columnCount; ++column) {
						final Object value = resultSet.getObject(column);
						columnList.add(String.valueOf(value).trim());
					}
					i++;
				}
				Object[] c = colummnName.toArray();
				Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();
				int countRow = rowList.size();
				map.put(0, c);
				for (int column = 1; column < countRow; ++column) {

					List<String> a = rowList.get(column);
					Collections.replaceAll(a, "null", "---");
					Object[] o = a.toArray();
					map.put(column, o);
				}

				Set<Integer> keyset = map.keySet();
				int rownum = 0;
				for (Integer key : keyset) {
					Row row = sheet.createRow(rownum++);
					sheet.autoSizeColumn(key);
					Object[] objArr = map.get(key);
					int cellnum = 0;
					for (Object obj : objArr) {
						Cell cell = row.createCell(cellnum++);
						if (obj instanceof Integer)
							cell.setCellValue((Integer) obj);
						else if (obj instanceof String)
							cell.setCellValue((String) obj);
						else if (obj instanceof Double)
							cell.setCellValue((Double) obj);
					}
				}

				try {
					// new excel file created by fileoutput stream object
					FileOutputStream out = new FileOutputStream(new File(
							csvFileLoc));
					workbook.write(out);
					out.close();
					System.out.println("Excel written successfully..");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Date enddate = new Date();
				ReportQueueData reportQueueData = adminReportDao
						.getReportQueueData(getTableData.getReportDataId());
				reportQueueData.setFileName(filename);
				reportQueueData.setTimecomplete(enddate);
				reportQueueData.setTimetake(enddate.getTime() - date1.getTime()
						+ "");
				reportQueueData.setStatus(CebiConstant.COMPLETED);
				reportQueueData.setTotalCount(i + "");
				adminReportDao.updateReportQueueData(reportQueueData);
				String zipFileName = MappingConstant.BANK_REPORT_LOCATION + filename + ".zip";
				fzip = new File(zipFileName);
				ZipOutputStream zippedOut = new ZipOutputStream(new FileOutputStream(fzip));
				FileSystemResource resource = new FileSystemResource(csvFileLoc);
				ZipEntry e = new ZipEntry(resource.getFilename());
				e.setSize(resource.contentLength());
				e.setTime(System.currentTimeMillis());
				zippedOut.putNextEntry(e);
				StreamUtils.copy(resource.getInputStream(), zippedOut);
				zippedOut.closeEntry();
				zippedOut.finish();
			}
			tableMetaData.setAppLabels(labels);
			data.add(tableMetaData);
			validateTableData(data, appMessages);
		} catch (Exception e) {
			logger.info(e.getMessage());
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
		return data;
	}

}
