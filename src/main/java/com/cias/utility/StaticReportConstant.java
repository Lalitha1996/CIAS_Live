package com.cias.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.cias.entity.BranchInformation;

public class StaticReportConstant {
	public static final String LINE = "line.separator";
	public static final String SA_5 = "SELECT SEGMENT, ASSET_SUBCLASS, STAT_DESC, SUM(tot_os) AS TOT_OS, COUNT(*) AS NO_OF_AC FROM ccdp010 a, lookup_master b WHERE  branch_code = ? AND main_lookup = 1 AND ALPHA_DESC = ASSET_SUBCLASS GROUP BY SEGMENT,ASSET_SUBCLASS ,STAT_DESC ORDER BY 2,1";
	public static final String SA_5_II = " SELECT CASE WHEN (SECTOR ='NA' OR SECTOR =' ') THEN 'OTHERS' ELSE SECTOR END AS SECTOR , ASSET_SUBCLASS, STAT_DESC, SUM(tot_os) AS TOT_OS, COUNT(*) AS NO_OF_AC FROM ccdp010 a, lookup_master b WHERE branch_code = ? AND main_lookup = 1 AND ALPHA_DESC = ASSET_SUBCLASS GROUP BY SECTOR,ASSET_SUBCLASS ,STAT_DESC ORDER BY 2,1";
	public static final String SA_5_III = " SELECT FACILITY_TYPE ,ASSET_SUBCLASS, STAT_DESC, SUM(tot_os) AS TOT_OS, COUNT(*) AS NO_OF_AC FROM ccdp010 a, lookup_master b WHERE branch_code = ? AND main_lookup = 1 AND ALPHA_DESC = ASSET_SUBCLASS GROUP BY FACILITY_TYPE,ASSET_SUBCLASS ,STAT_DESC ORDER BY 2,1";
	public static final String SA_5_IV = "SELECT ASSET_SUBCLASS, STAT_DESC, SUM(TOTAL_TANG) AS TOTAL_TANG, SUM(DICGC) AS DICGC, SUM(BANK_SECU + SECURE_ECGC + SECU_CENT_GOVT+ SECU_STATE_GOVT) AS BNK_GOV_ECG, SUM(UNSECURED) AS UNSECURED , COUNT(*) AS NO_OF_AC FROM lookup_master b LEFT OUTER JOIN ccdp0020 a ON ALPHA_DESC = ASSET_SUBCLASS WHERE branch_code = ? AND main_lookup = 1 GROUP BY ASSET_SUBCLASS ,STAT_DESC ORDER BY 1,2";
	public static final String TOTAL = "TOTAL";
	public static final String TOT_OS = "TOT_OS"; 
	public static final String NO_OF_AC = "NO_OF_AC";
	public static final String STAT_DESC = "STAT_DESC";
	public static  String ASSET_DEC = "ASSET_DEC";
	public static final String ASSET_SUBCLASS = "ASSET_SUBCLASS";
	public static final String PUBLIC = "PUBLIC";
	public static final String PRIORITY = "PRIORITY";
	public static  String SEGMENT = "SEGMENT";
	public static final String SECTOR = "SECTOR";
	public static final String FACILITY_TYPE = "FACILITY_TYPE";

	Calendar cal = Calendar.getInstance();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public void addHeader(BranchInformation branchInformation, int size, String[] str, StringBuilder buffer, String mainheading, String tableheading) {
		String branchname = branchInformation.getBrcdBranchName() != null ? branchInformation.getBrcdBranchName() : " ";
		String region = branchInformation.getBrcdRegionalId() != null ? branchInformation.getBrcdRegionalId() : " ";
		String zone = branchInformation.getBrcdZonalId() != null ? branchInformation.getBrcdZonalId() : " ";

		buffer.append(System.getProperty(LINE));
		buffer.append(System.getProperty(LINE));
		buffer.append(StringUtils.rightPad(CebiConstant.BANKNAME, 20) + StringUtils.leftPad("RUNDATE  ", 34) + simpleDateFormat.format(new Date()) + "  Time:  " + cal.getTime());
		buffer.append(System.getProperty(LINE));
		buffer.append(StringUtils.rightPad("SA - 5", 45) + StringUtils.leftPad("BRANCH NAME: " + branchname.trim() + " REGION:  " + region + "   ZONE: " + zone + " CIRCLE: XXXXXX ", 40));
		buffer.append(System.getProperty(LINE));
		buffer.append(StringUtils.rightPad("Branch : " + branchInformation.getId().getBrcdBranchCd()!= null ? branchInformation.getId().getBrcdBranchCd():" ", 45));
		addTableHeading(size, str, buffer, mainheading, tableheading);
	}

	public void addTableHeading(int size, String[] str, StringBuilder buffer, String mainheading, String tableheading) {
		String line = new String(new char[size]).replace('\0', '=');
		buffer.append(System.getProperty(LINE));
		buffer.append(System.getProperty(LINE));
		buffer.append(StringUtils.center(mainheading, size));
		buffer.append(System.getProperty(LINE));
		buffer.append(System.getProperty(LINE) + "|");
		buffer.append(line + "|");
		buffer.append(System.getProperty(LINE) + "|");
		buffer.append(StringUtils.center(tableheading, size) + "|");
		buffer.append(System.getProperty(LINE) + "|");
		buffer.append(line + "|");
		buffer.append(System.getProperty(LINE) + "|");
		
		if("PART - V SEGMENTWISE/AGEWISE/SECURITYWISE CLASSIFICATION OF DOUBTFUL ASSETS".equalsIgnoreCase(tableheading)){
			buffer.append(StringUtils.rightPad("ASSETS", 18) + " |");
			buffer.append(StringUtils.rightPad("STAT DESC", 40)+"|");
		}else if("FINAL REPORT".equalsIgnoreCase(tableheading)){
			
		}else{
			buffer.append(StringUtils.rightPad("ASSETS", 38) + " |");
		}
		for (String columns : str) {
			if ("ASSET_DESC".equals(columns)) {
				buffer.append(StringUtils.center(columns, 35) + "|");
			} else {
				buffer.append(StringUtils.center(columns, 20) + "|");
			}
		}
	}

	public void addFooter(int size, StringBuilder buffer) {
		buffer.append(System.getProperty(LINE));
		buffer.append(StringUtils.rightPad("STATUTORY AUDITOR ", 10) + StringUtils.center("DY. GENERAL MANAGER / ASST. GENERAL MANAGER / BRANCH MANAGER", size));
		buffer.append(System.getProperty(LINE));
	}
}
