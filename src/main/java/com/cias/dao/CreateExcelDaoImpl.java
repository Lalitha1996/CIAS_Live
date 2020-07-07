package com.cias.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cias.entity.QueryData;
import com.cias.entity.ReportQueueData;
import com.cias.utility.CebiConstant;
import com.cias.utility.ConnectionException;
import com.cias.utility.MappingConstant;
import com.cias.utility.PdfUtils;

@Repository
@Transactional
public class CreateExcelDaoImpl extends PdfUtils implements CreateExcelDao {

	private static final Logger logger = Logger.getLogger(CreateExcelDaoImpl.class);
	
	@Autowired
	CebiConstant cebiConstant;
	
	@Autowired
	AdminReportDao adminreportdao;

	@Override
	public void downloadExcel(QueryData queryData, String bank,String filename) {
		String parameter = "";
		String columns = ""; 
		String criteria = "";
		String query=null;
		int colNum = 0;
		int rowcnt = 4;
		int recordCount =0;
		int count=0;
		Session session = cebiConstant.getCurrentSession(bank);
		ResultSet resultSet = null;
		Connection connection=null;
		PreparedStatement prepareStatement=null;
		//HSSFWorkbook wb = new HSSFWorkbook();   // supports 65k records only
		//HSSFSheet sheet = wb.createSheet();
		
		SXSSFWorkbook wb = new SXSSFWorkbook();     // SUPPORTS 10lack records up to ..  with xlsx format     
		Sheet sheet = wb.createSheet();

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		//sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)  
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10)); 
		
		Font font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		
		//Set bankName
		cell.setCellStyle(cellStyle);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cell.setCellValue(getBankName(bank));
		
		//Set tableName
		row = sheet.createRow(1);
		cell = row.createCell(0);
		//sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)  
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,10));
		cell.setCellStyle(cellStyle);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cell.setCellValue(getTableNames(queryData));
		
		//ReportId,ReportDate,Time
		row = sheet.createRow(2);
		cell = row.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,10));
		cell.setCellStyle(cellStyle);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		    SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	        String format = sdf.format(new Date());
	        String date = format.substring(0, 10);
	        String time = format.substring(11, 19);
            cell.setCellValue("ReportId: "+queryData.getReportDataId()+CebiConstant.SPACE+"ReportDate:"+date+CebiConstant.SPACE+"Time:"+time);
		
		columns = queryData.getColumnNames().trim().length() > 0 ? queryData.getColumnNames() : "";
		parameter = queryData.getParameter().trim().length() > 0 ? queryData.getParameter() : "";
		criteria = queryData.getQuery().trim().length() > 0 ? queryData.getQuery() : "";
		
		query = queryData.getTable2().isEmpty()?populateQuery(queryData, parameter, criteria):populateJoinQuery(queryData, parameter, criteria);
		 logger.info("Query Generated During Excel Download  downloadExcel():: " + query);
		 System.out.println(query);
		try {
			 connection = ((SessionImpl) session).connection();
			 connection.setAutoCommit(false);
			 prepareStatement = connection.prepareStatement(query,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			 prepareStatement.setFetchSize(5000);
			 resultSet = prepareStatement.executeQuery();

			
			BufferedOutputStream bos = null;

		    String zipFileLoc = MappingConstant.BANK_REPORT_LOCATION +filename+".xlsx";
		   
		    Path path = Files.createDirectories(Paths.get(MappingConstant.EXCEL_TEMP_LOCATION +filename+"/"));
	          
			String lstparam = parameter.substring(0, (parameter.length() - 1));
			List<String> dbColumns = Arrays.asList(lstparam.split(","));
			List<String> columnLables = Arrays.asList(columns.split(","));
			
			 //table fields
             row = sheet.createRow(3);
			      
			for (String lbl : columnLables) {
				cell = row.createCell(colNum);
				cell.setCellStyle(cellStyle);
				sheet.autoSizeColumn((short) (colNum));
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
				recordCount++;
				
				//spreadsheet
				int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
				
				if (physicalNumberOfRows > 1048570) {
					
					++count;
					
				    bos = new BufferedOutputStream(new FileOutputStream(path.toString()+"/"+filename+"("+count+")"+".xlsx"));
					wb.write(bos);
					wb=new SXSSFWorkbook(); 
				    sheet = wb.createSheet();
				    cellStyle = wb.createCellStyle();
					cellStyle.setWrapText(true);
					font = wb.createFont();
					font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					cellStyle.setFont(font);
					
					physicalNumberOfRows = 0;
					rowcnt = 1;
					colNum = 0;
					row = sheet.createRow(0);
					for (String lbl : columnLables) {
						cell = row.createCell(colNum);
						cell.setCellStyle(cellStyle);
						sheet.autoSizeColumn((short) (colNum));
						cell.setCellValue(lbl);
						++colNum;
						}
					
					}

			}
		++count;
		bos = new BufferedOutputStream(new FileOutputStream(path.toString()+"/"+filename+"("+count+")"+".xlsx"));
     	wb.write(bos);

		
     	    byte buffer[] = new byte[2048];
		    FileOutputStream fos = new FileOutputStream(zipFileLoc+".zip");
			ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fos));
			File inputDir = new File(path.toString());
			String listOfFiles[] = inputDir.list();
			BufferedInputStream bufferedInputStream = null;
			FileInputStream fileInputStream=null;
			for (String fileName : listOfFiles) {
				System.out.println("Adding xlsx Files to zip: " + fileName);
				fileInputStream = new FileInputStream(new File(inputDir, fileName));
				bufferedInputStream = new BufferedInputStream(fileInputStream);
				ZipEntry entry = new ZipEntry(fileName);
				zipOutputStream.putNextEntry(entry);
				int count1;
				while ((count1 = bufferedInputStream.read(buffer)) != -1) {
					zipOutputStream.write(buffer, 0, count1);
				}bufferedInputStream.close();
			}
			zipOutputStream.close();
			System.out.println("File Zipped!!!!!");
			   
			   //delete temp xlsx files from the path
			   Files.walk(path)
		      .sorted(Comparator.reverseOrder())
		      .map(Path::toFile)
		      .forEach(File::delete);
	    
	    fileInputStream.close();
		fos.close();
		bos.close();
     	
     	 ReportQueueData reportQueueData=adminreportdao.getReportQueueData(queryData.getReportDataId());
		 reportQueueData.setTotalCount(recordCount+"");
		 adminreportdao.updateReportQueueData(reportQueueData);
			
		} catch (IOException e) {
			logger.info(e.getMessage());
		} catch (OutOfMemoryError error) {
			throw new ConnectionException("Failed to allocate Max memory...!");
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

	}

}
