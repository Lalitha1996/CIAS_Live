package com.cias.dao;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.entity.QueryData;
import com.cias.utility.CebiConstant;
import com.cias.utility.ConnectionException;
import com.cias.utility.PdfUtils;

@Repository
@Transactional
public class CreateExcelDaoImpl extends PdfUtils implements CreateExcelDao {

	private static final Logger logger = Logger.getLogger(CreateExcelDaoImpl.class);
	
	@Autowired
	CebiConstant cebiConstant;

	@Override
	public byte[] downloadExcel(QueryData queryData, String bank) {
		String parameter = "";
		String columns = ""; 
		String criteria = "";
		String query=null;
		int colNum = 0;
		int rowcnt = 1;
		byte[] outArray = null;
		Session session = cebiConstant.getCurrentSession(bank);
		ResultSet resultSet = null;
		Connection connection=null;
		PreparedStatement prepareStatement=null;
		//HSSFWorkbook wb = new HSSFWorkbook();   // supports 65k records only
		//HSSFSheet sheet = wb.createSheet();
		
		SXSSFWorkbook wb = new SXSSFWorkbook(100);     // SUPPORTS 10lack records up to ..       
		Sheet sheet = wb.createSheet();

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);

		Font font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		columns = queryData.getColumnNames().trim().length() > 0 ? queryData.getColumnNames() : "";
		parameter = queryData.getParameter().trim().length() > 0 ? queryData.getParameter() : "";
		criteria = queryData.getQuery().trim().length() > 0 ? queryData.getQuery() : "";
		if(queryData.getTable2()==""||queryData.getTable2()==null){
			 query = populateQuery(queryData, parameter, criteria);
		}else{
			 query = populateJoinQuery(queryData, parameter, criteria);
		}
		
		try {
			 connection = ((SessionImpl) session).connection();
			 prepareStatement = connection.prepareStatement(query);
			 resultSet = prepareStatement.executeQuery();

			String lstparam = parameter.substring(0, (parameter.length() - 1));
			List<String> dbColumns = Arrays.asList(lstparam.split(","));
			List<String> columnLables = Arrays.asList(columns.split(","));

			for (String lbl : columnLables) {
				cell = row.createCell(colNum);
				cell.setCellStyle(cellStyle);
				sheet.autoSizeColumn((short)(colNum));
				cell.setCellValue(lbl);
				++colNum;
			}
			
			
			while (resultSet.next()) {
				colNum = 0;
				row = sheet.createRow(rowcnt);
				for (String label : dbColumns) {
					label = label.contains("(") && label.contains(")") ? label.substring(label.indexOf("As") + 3, label.length()) : label;
					label = label.contains("AS") && !label.contains(")") ? label.substring(label.indexOf("AS") + 3, label.length()) : label;
					label.trim();
					cell = row.createCell(colNum);
					if (resultSet.getString(label)==null || resultSet.getString(label).isEmpty())
						cell.setCellValue("");
					else
						//sheet.autoSizeColumn(colNum);      // this automatically adjust the size of the cell (drawback takes too long time)
					cell.setCellValue(resultSet.getString(label));
					++colNum;
				}
				++rowcnt;
			}
			wb.write(outByteStream);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}catch (OutOfMemoryError error) {
			throw new ConnectionException("Failed to allocate Max memory...!");
		}finally {
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
		outArray = outByteStream.toByteArray();
		return outArray;
	}

}
