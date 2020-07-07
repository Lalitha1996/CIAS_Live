package com.cias.controller;

import java.io.File;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cias.entity.ReportDownload;
import com.cias.entity.ReportHistory;
import com.cias.entity.TellerMaster;
import com.cias.service.DownloadService;
import com.cias.utility.CebiConstant;
import com.cias.utility.MappingConstant;



/**
 * @author deepak.d
 *
 */
@Controller
public class DownloadController  {

	@Autowired
	DownloadService downloadService;

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = MappingConstant.DOWNLOAD_REPORT_PAGE, method = RequestMethod.GET)
	public ModelAndView downloadPage(HttpServletRequest request,HttpServletResponse response,ModelAndView model)
	{
		/*System.out.println(status);
		String urlTail = new AntPathMatcher()
	            .extractPathWithinPattern( "/{status}", request.getRequestURI() );
		System.out.println(urlTail);*/
		/*String param =request.getParameter("status");
		String scheme = request.getScheme();
	        String serverName = request.getServerName();
	        int portNumber = request.getServerPort();
	        String contextPath = request.getContextPath();
	        String servletPath = request.getServletPath();
	        String pathInfo = request.getPathInfo();
	        String query = request.getQueryString();
	        System.out.println(param+scheme+serverName+portNumber+contextPath+servletPath+pathInfo+query);*/
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		String bankCode = (String) session.getAttribute(CebiConstant.BANK_CODE);
		ReportDownload reportDownload = new ReportDownload();
		reportDownload.setBranCode(bankCode);
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
         String currDate= dateFormat.format(date);
         reportDownload.setDate(currDate);
		modelAndView.addObject("MADOWNREPORT", reportDownload);
		TellerMaster tellerMaster = new TellerMaster();
		if (session.getAttribute("user") != null) {
			modelAndView.setViewName("reportDownload");
		} else {
			modelAndView.setViewName(CebiConstant.LOGIN);
			modelAndView.addObject("loginForm", tellerMaster);
			modelAndView.addObject("SESSION_LOGOUT",
					CebiConstant.SESSION_LOGOUT);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/getReportData", method = RequestMethod.POST)
	public @ResponseBody  Map<String, Object> reportStatus(
			@RequestBody ReportDownload reportDownload,
			HttpServletRequest request) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		List<ReportDownload> statusList = null;
		String date = reportDownload.getDate().replaceAll("/", "-");
		reportDownload.setDate(DownloadController.formatDate(date));
		statusList = downloadService.getReportStatus(reportDownload);
		map.put("statusList", statusList);
		return map;
	}

	public static String formatDate(String inDate) {
		SimpleDateFormat inSDF = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");
		String outDate = "";
		if (inDate != null) {
			try {
				Date date = inSDF.parse(inDate);
				outDate = outSDF.format(date);
			} catch (ParseException ex) {
			}
		}
		return outDate;
		
	}
	
	@RequestMapping(value = "/getDeleteSts", method = RequestMethod.POST)
	public @ResponseBody  Map<String, Object> reportStatusDelete(
			@RequestBody ReportDownload reportDownload,
			HttpServletRequest request) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		HttpSession session = request.getSession();
		String bankCode = (String) session.getAttribute(CebiConstant.BANK_CODE);
		reportDownload.setBranCode(bankCode);
		String msg = downloadService.getReportDelete(reportDownload);
		map.put("statusList", msg);
		return map;
	}

	
	
	@RequestMapping("/downloadfile")
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fname= request.getParameter("fileName");
		String loc = MappingConstant.BANK_REPORT_LOCATION+ fname+".zip";
		File file = new File(loc);
		byte[] array = Files.readAllBytes(file.toPath());
		String fileName = "attachment; filename=" + file.getName();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("charset", "utf-8");
		
		if(fname.contains("csv") || fname.contains("txt")) {
			responseHeaders.setContentType(MediaType.valueOf("text/html"));
		}else if(fname.contains("xlsx")) {
			responseHeaders.setContentType(MediaType.valueOf("application/ms-excel"));
		}else {
			responseHeaders.setContentType(MediaType.valueOf("application/pdf"));
			}
		responseHeaders.setContentLength(array.length);
		responseHeaders.set("Content-disposition",fileName);
		File deleteFile = new File(MappingConstant.BANK_REPORT_LOCATION+fname);
		@SuppressWarnings("unused")
		boolean result = Files.deleteIfExists(deleteFile.toPath());
		return new ResponseEntity<byte[]>(array, responseHeaders, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = MappingConstant.REPORTHISTORY, method = RequestMethod.GET)
	public ModelAndView getReportHistory(ModelAndView model,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		TellerMaster tellerMaster = new TellerMaster();
		List<ReportHistory> histList = null;
		ReportDownload reportDownload = new ReportDownload();
		HttpSession session = request.getSession();
		String bankCode = (String) session.getAttribute(CebiConstant.BANK_CODE);
		reportDownload.setBranCode(bankCode);
		LocalDate date1 = LocalDate.now();
		reportDownload.setDate(date1.toString());
		histList = downloadService.getReportHistory(reportDownload);
		if (session.getAttribute("user") != null) {
			modelAndView.addObject("data",histList);
			modelAndView.setViewName("reportHistory");
		} else {
			modelAndView.setViewName(CebiConstant.LOGIN);
			modelAndView.addObject("loginForm", tellerMaster);
			modelAndView.addObject("SESSION_LOGOUT",
					CebiConstant.SESSION_LOGOUT);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/getHistData", method = RequestMethod.POST)
	public @ResponseBody  Map<String, Object> getReportHisData(@RequestBody String date,HttpServletRequest request) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		
		HttpSession session = request.getSession();
		ReportDownload reportDownload = new ReportDownload();
		List<ReportHistory> histList = null;
		String bankCode = (String) session.getAttribute(CebiConstant.BANK_CODE);
		reportDownload.setBranCode(bankCode);
		reportDownload.setDate(date.toString().trim());
		histList = downloadService.getReportHistory(reportDownload);
		map.put("histList", histList);
		return map;
	}
	
}