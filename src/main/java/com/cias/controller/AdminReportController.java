package com.cias.controller;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cias.dao.AdminReportDao;
import com.cias.entity.Banks;
import com.cias.entity.Chart;
import com.cias.entity.ColumnNames;
import com.cias.entity.QueryData;
import com.cias.entity.TableMetaData;
import com.cias.entity.TellerMaster;
import com.cias.service.AdminReportService;
import com.cias.service.AdminTableMetaDataService;
import com.cias.service.ApplicationLabelService;
import com.cias.service.CreateCsvService;
import com.cias.service.CreateExcelService;
import com.cias.service.CreatePdfService;
import com.cias.service.LoginService;
import com.cias.utility.ApplicationLabelCache;
import com.cias.utility.CebiConstant;
import com.cias.utility.ConnectionException;
import com.cias.utility.MappingConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AdminReportController {
	
	private static final Logger logger = Logger.getLogger(AdminReportController.class);

	@Autowired
	AdminReportService adminReportService;

	@Autowired
	LoginService loginService;

	@Autowired
	AdminTableMetaDataService adminTableMetaDataService;

	@Autowired
	ApplicationLabelService applicationLabelService;
	
	@Autowired
	CreatePdfService createPdfService;

	@Autowired
	CreateExcelService createExcelService;
	
    @Autowired
	CreateCsvService createCsvService;

	

	
	@Autowired
	AdminReportDao adminReportDao;
	
	@GetMapping(value=MappingConstant.DEFAULT_PAGE)
	//@RequestMapping(value = MappingConstant.DEFAULT_PAGE, method = RequestMethod.GET)
	public ModelAndView loginPage(ModelAndView model, HttpServletRequest request,@ModelAttribute("INVALID_USER") String msg) {
		System.out.println("Sys");
		HttpSession httpSession = request.getSession();
		httpSession.setMaxInactiveInterval(30*60); // Session Time to logout 
		if (httpSession.getAttribute("user") != null) {
			httpSession.invalidate();
		}
		List<Banks> banks = null;
		TellerMaster tellerMaster = new TellerMaster();
		model.addObject("loginForm", tellerMaster);
		model.setViewName(CebiConstant.LOGIN);

		banks = adminReportService.retreiveBankNames();
		if (banks != null && !banks.isEmpty())
			model.addObject("banks", banks);
		if (msg != "" || msg != null || !msg.isEmpty()) {
			model.addObject("INVALID_USER", msg);
		}
		    return model;
	}

	@RequestMapping(value = MappingConstant.LANDING_PAGE, method = RequestMethod.POST)
	public String callLandingPage(@ModelAttribute("loginForm") TellerMaster tellerMaster, Model model, HttpServletRequest request,RedirectAttributes redir,@ModelAttribute("LOGIN_ERROR") String logerr) {
		String page = null;
		boolean ipAddress = false;
		List<Object[]> master = null;
		HttpSession session=request.getSession();
		Map<String, List<TableMetaData>> map = ApplicationLabelCache.getViewsInstance();
		if (tellerMaster.getTellerid() != null && tellerMaster.getPwd() != null) {
			if (tellerMaster.getBankName().equalsIgnoreCase("Please Select Bank Name")) {
				master = loginService.validateLoginUser(tellerMaster);
			} else {
				tellerMaster.setBankCode(tellerMaster.getBankName().substring(0, 4));
				CebiConstant.BANKNAME=tellerMaster.getBankName();
				master = loginService.validateSuperLoginUser(tellerMaster);
			}
			session.setAttribute("auditHistory", master);
			if (master.isEmpty()) {
				/*model.addAttribute("INVALID_USER", CebiConstant.INVALID_USER);
				page = CebiConstant.LOGIN;*/
				page = "redirect:"+MappingConstant.DEFAULT_PAGE;
				redir.addFlashAttribute("INVALID_USER", CebiConstant.INVALID_USER);
			} else {
				String remoteAddr = request.getRemoteAddr();
				if (remoteAddr.equals("0:0:0:0:0:0:0:1") || "127.0.0.1".equalsIgnoreCase(remoteAddr)) {
					InetAddress localip = null;
					try {
						localip = java.net.InetAddress.getLocalHost();
						remoteAddr = localip.getHostAddress();
					} catch (UnknownHostException e) {
						logger.info(e.getMessage());
					}
				}

			//	remoteAddr = remoteAddr.substring(0, remoteAddr.lastIndexOf('.')); BY SHAIK
				for (Object[] obj : master) {
					String branchIp = (String) obj[1];
					String bankCode = (String) obj[3];
					boolean ccdp = (Boolean)obj[4];
					tellerMaster.setBankCode(bankCode);
					tellerMaster.setCcdp(ccdp);
					
					if (branchIp.contains(".")) {
						//branchIp = branchIp.substring(0, branchIp.lastIndexOf('.')); BY SHAIK
						if (branchIp.equalsIgnoreCase(remoteAddr)) {
							System.out.println(remoteAddr);
							ipAddress = true;
							break;
						}
					} else {
						session.setAttribute("bank", tellerMaster.getBankCode());
						session.setAttribute("user", tellerMaster.getTellerid());
						session.setAttribute(CebiConstant.BANK_CODE, tellerMaster.getBankCode());
						session.setAttribute("isCCdp", tellerMaster.isCcdp());
						page = "ciaspage";
					}
				}
				if (ipAddress) {
					try {
						session.setAttribute("bank", tellerMaster.getBankCode());
						session.setAttribute("user", tellerMaster.getTellerid());
						session.setAttribute(CebiConstant.BANK_CODE, tellerMaster.getBankCode());
						session.setAttribute("isCCdp", tellerMaster.isCcdp());
						session.setAttribute("bnkname", tellerMaster.getBankName());
						List<TableMetaData> tables = adminTableMetaDataService.retrieveDbTables(tellerMaster.getBankCode());
						if (!tables.isEmpty()) {
							map.put("views", tables);
							page = "ciaspage";
						} else {
							model.addAttribute("NO_PRVLG", "Sorry tables not found..!");
							page = CebiConstant.LOGIN; // by shaik
						}
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("NO_PRVLG", e.getMessage());
						page = CebiConstant.LOGIN;
					}
				} else {
					/*model.addAttribute("LOGIN_ERROR", CebiConstant.IP_ADDRESS);
					page = CebiConstant.LOGIN;*/
					
					redir.addFlashAttribute("LOGIN_ERROR", CebiConstant.IP_ADDRESS);
					model.addAttribute("LOGIN_ERROR", logerr);
					page = "redirect:"+MappingConstant.DEFAULT_PAGE;
				}
			}
			applicationLabelService.retrieveAllLabels();

		} else {
			page = CebiConstant.LOGIN;
		}
		return page;
	}
	
	@ExceptionHandler(ConnectionException.class)
	public  ModelAndView connectionException(ConnectionException errorMessage) {
		ModelAndView responseObject1 = new ModelAndView("login2");
		responseObject1.addObject("loginForm", new TellerMaster());
		responseObject1.addObject(CebiConstant.ERROR, errorMessage.getMessage());
		return responseObject1;
	}

	@RequestMapping(value = MappingConstant.TABLES, method = RequestMethod.GET)
	public @ResponseBody List<TableMetaData> createTableMetaData(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<TableMetaData> tables = null;
		String bank = (String) session.getAttribute("bank");
		tables = adminReportService.populateDbTables(bank);
		return tables;
	}

	@RequestMapping(value = MappingConstant.REPORT_PAGE, method = RequestMethod.GET)
	public ModelAndView callReportPage(HttpServletRequest request,RedirectAttributes redir) {

		List<TableMetaData> tables = null;
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60);
		if (session.getAttribute("user") != null) {
			String bank = (String) session.getAttribute("bank");
			tables = adminReportService.populateDbTables(bank);
			if (!tables.isEmpty()) {
				modelAndView.addObject("tables", tables);
				modelAndView.setViewName("report");
			}
		} else {
			
			modelAndView.setViewName("redirect:"+MappingConstant.DEFAULT_PAGE);
			redir.addFlashAttribute("SESSION_LOGOUT",CebiConstant.SESSION_LOGOUT);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = MappingConstant.RETRIVE_COLUMNS, method = RequestMethod.POST)
	public @ResponseBody List<ColumnNames> retriveColumnMetaData(@RequestBody String table, HttpServletRequest request,RedirectAttributes redir) {
		HttpSession session = request.getSession();
		String bank = (String) session.getAttribute("bank");
		return adminTableMetaDataService.retrieveTableColumns(table, bank);
	}
	
	@RequestMapping(value = MappingConstant.GET_TABLEDATA, method = RequestMethod.POST)
	public @ResponseBody List<TableMetaData> getTableData(@RequestBody QueryData getTableData, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String bank = (String) session.getAttribute("bank");
		@SuppressWarnings("unchecked")
		List<Object[]> master = (List<Object[]>) session.getAttribute("auditHistory");
		TellerMaster tellerMaster = new TellerMaster();
		for (Object[] object : master) {
			tellerMaster.setTellerid(object[0].toString());
			tellerMaster.setBranchid(Integer.parseInt(object[2].toString()));
			tellerMaster.setBankCode(object[3].toString());
			break;
		}
		return adminReportService.getTableData(getTableData, bank, tellerMaster);
	}
	
	@RequestMapping(value = MappingConstant.FAVOURITE, method = RequestMethod.POST)
	public @ResponseBody Map<String, String> savefavouriteQuery(@RequestBody QueryData data, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();
		HttpSession session = request.getSession();
		String bankCode = (String) session.getAttribute(CebiConstant.BANK_CODE);
		ModelAndView model = new ModelAndView();
		data.setBankCode(bankCode);
		adminReportDao.saveFavouriteQuery(data);
		map.put("saved", "Added to favourite list..");
		model.addObject("saved", "Added to favourite list..");
		return map;

	}

	@RequestMapping(value = MappingConstant.SHOW_FAVOURITE_LIST, method = RequestMethod.GET)
	public ModelAndView savefavouriteQuery(HttpServletRequest request,RedirectAttributes redir,@ModelAttribute("delete_msg") String msg,@ModelAttribute("checkedhisto") QueryData data) {
		
		HttpSession session = request.getSession();
		String bankCode = (String) session.getAttribute(CebiConstant.BANK_CODE);
		
		ModelAndView model = new ModelAndView();
		//QueryData data=new QueryData();
		model.setViewName("favouritelist");
		
		if (msg!=""|| msg!=null){
			model.addObject("delete_msg",msg);
		}

		if (session.getAttribute("user") != null) {
			//model.addObject("checkedhisto", data);
			List<QueryData> list = adminReportDao.retrieveFavouriteList(bankCode);
			
			if (list != null && !list.isEmpty())
				model.addObject("favouriteLists", list);
			else
				model.addObject("nocontent", "No Content..!!!");
	
		}else {
			
			model.setViewName("redirect:"+MappingConstant.DEFAULT_PAGE);
			redir.addFlashAttribute("SESSION_LOGOUT",CebiConstant.SESSION_LOGOUT);
			
		}
		return model;
	}
	
	@RequestMapping(value = MappingConstant.GET_REPORT_DETAILS, method = RequestMethod.POST)
	public ModelAndView getReportDetails(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		QueryData data=new QueryData();
		//model.addObject("checkedhisto", data);
		model.setViewName("report");
		int id = Integer.parseInt(request.getParameter("id"));
		List<QueryData> list = adminReportDao.getReportDetails(id);
		if (list != null && !list.isEmpty())
			model.addObject("favouriteLists", list.get(0));
		else
			model.addObject("nocontent", "No Content..!");
		return model;

	}
	
	@RequestMapping(value = MappingConstant.HELP, method = RequestMethod.GET)
	public ModelAndView faqQuestions(HttpServletRequest request,RedirectAttributes redir) {
		ModelAndView  mv=new ModelAndView();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60);
		if (session.getAttribute("user") != null) {
		mv.setViewName("/help");
		}else{
			mv.setViewName("redirect:"+MappingConstant.DEFAULT_PAGE);
			redir.addFlashAttribute("SESSION_LOGOUT",CebiConstant.SESSION_LOGOUT);
		}
		return mv;
	}
	@RequestMapping(value = "/cias", method = RequestMethod.GET)
	public ModelAndView brimspage(HttpServletRequest request,RedirectAttributes redir) {
		ModelAndView  mv=new ModelAndView();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60);
		if (session.getAttribute("user") != null) {
		mv.setViewName("/ciaspage");
		}else{
			mv.setViewName("redirect:"+MappingConstant.DEFAULT_PAGE);
			redir.addFlashAttribute("SESSION_LOGOUT",CebiConstant.SESSION_LOGOUT);
		}
		return mv;
	}
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView landingpage(HttpServletRequest request,RedirectAttributes redir) {
		ModelAndView  mv = new ModelAndView();
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
		mv.setViewName("/dashboard");
		}else {
			
			mv.setViewName("redirect:"+MappingConstant.DEFAULT_PAGE);
			redir.addFlashAttribute("SESSION_LOGOUT",CebiConstant.SESSION_LOGOUT);
		}
		return mv;
	}
	
	@RequestMapping(value = MappingConstant.SHOW_CHART_RESULT, method = RequestMethod.POST)
    public @ResponseBody List<Chart> retrivechartResult(HttpServletRequest request){
            ModelAndView model = new ModelAndView();
            HttpSession session = request.getSession();
            String bank = (String) session.getAttribute("bank");
            List<Chart> list = adminReportService.showchartPage(bank);
            model.addObject("chatData", list);
            return list;

    }
	

@RequestMapping(value = MappingConstant.DOWNLOAD_PDF, method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportDataToPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] pdfBytes = null;
		ResponseEntity<byte[]> pdfResponse = null;
		ObjectMapper mapper = new ObjectMapper();
		String param = request.getParameter("pdfJson");
		QueryData queryData = mapper.readValue(param, QueryData.class);
		HttpSession session = request.getSession();
		String bank = (String) session.getAttribute("bank");
		pdfBytes = createPdfService.downloadPdf(queryData, bank);
		HttpHeaders headers = new HttpHeaders();
		response.setContentType("application/pdf");
		headers.setContentDispositionFormData(CebiConstant.INLINE, "cias.pdf");
		pdfResponse = new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		return pdfResponse;
	}

	@RequestMapping(value = MappingConstant.DOWNLOAD_EXCEL, method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportDataToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] pdfBytes = null;
		ResponseEntity<byte[]> pdfResponse = null;
		ObjectMapper mapper = new ObjectMapper();
		String param = request.getParameter("excelJson");
		QueryData queryData = mapper.readValue(param, QueryData.class);
		HttpSession session = request.getSession();
		String bank = (String) session.getAttribute("bank");
		pdfBytes = createExcelService.downloadExcel(queryData, bank);
		HttpHeaders headers = new HttpHeaders();
		response.setContentType("application/ms-excel");
		headers.setContentDispositionFormData(CebiConstant.INLINE, "cias.xlsx");
		pdfResponse = new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		return pdfResponse;
	}

@RequestMapping(value = MappingConstant.DOWNLOAD_CSV_PIPE, method = RequestMethod.POST)
	public ResponseEntity<byte[]> downloadCsvPipeSeperator(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] pdfBytes = null;
		ResponseEntity<byte[]> pdfResponse = null;
		ObjectMapper mapper = new ObjectMapper();
		String param = request.getParameter("csvJson");
		QueryData queryData = mapper.readValue(param, QueryData.class);
		HttpSession session = request.getSession();
		String bank = (String) session.getAttribute("bank");
		pdfBytes = createCsvService.downloadCsvPipeSeperator(queryData, bank);
		HttpHeaders headers = new HttpHeaders();
		response.setContentType("text/csv");
		headers.setContentDispositionFormData(CebiConstant.INLINE, "cias.csv");
		pdfResponse = new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		return pdfResponse;
	}
	
	
	// alimoula queue downloads...
	
	@RequestMapping(value = MappingConstant.DOWNLOAD_CSV_QUEUE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> exportDataToCsv(
			@RequestBody QueryData queryData, HttpServletRequest request)
			throws IOException {
		Map<String, Object> map = new HashMap<>();
		queryData.setReporttype("csv");
		HttpSession session = request.getSession();
		String bank = (String) session.getAttribute("bank");
		map = adminReportService.checkQueueIsExist(queryData, bank);
		if (map.size() >= 2) {
			if (map.containsKey("0")) {
				String str = map.get("0").toString();
				map.put("msg", str);
			}else if(map.containsKey("2")){
				String str = map.get("2").toString();
				map.put("msg", str);
			}else {
				String str = map.get("1").toString();
				map.put("msg", str);
			}
			return map;
			// return adminReportService.csvDownloadQueue(queryData, bank);
		} else {
			return adminReportService.csvDownloadQueue(queryData, bank);
		}

	}

	@RequestMapping(value = MappingConstant.DOWNLOAD_CSV_PIPE_QUEUE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> exportDataToTxt(
			@RequestBody QueryData queryData, HttpServletRequest request)
			throws IOException {
		HttpSession session = request.getSession();
		queryData.setReporttype("csvpipe");
		String bank = (String) session.getAttribute("bank");
		return adminReportService.csvDownloadQueue(queryData, bank);
	}

	@RequestMapping(value = MappingConstant.DOWNLOAD_XLS_QUEUE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> exportDataToXls(
			@RequestBody QueryData queryData, HttpServletRequest request)
			throws IOException {
		HttpSession session = request.getSession();
		queryData.setReporttype("xls");
		String bank = (String) session.getAttribute("bank");
		return adminReportService.csvDownloadQueue(queryData, bank);
	}
	
	@RequestMapping(value = MappingConstant.DOWNLOAD_PDF_QUEUE, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> exportDataToPdf(
			@RequestBody QueryData queryData, HttpServletRequest request)
			throws IOException {
		HttpSession session = request.getSession();
		queryData.setReporttype("pdf");
		String bank = (String) session.getAttribute("bank");
		return adminReportService.csvDownloadQueue(queryData, bank);
	}
	
	
	@RequestMapping(value = "/deleteview", method = RequestMethod.POST)
	public String deleteViews(@ModelAttribute("checkedhisto") QueryData qrydata,HttpServletRequest request,RedirectAttributes redir){
		boolean flag=false;
		flag=adminReportDao.deleteviews(qrydata);
		if (flag) {
			redir.addFlashAttribute("delete_msg", "Favarite deleted successfully !!!");
		}
		return "redirect:/showFavouriteList";
	}
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settingspage(ModelAndView model,HttpServletRequest request,RedirectAttributes redir) {
        HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
        model.setViewName("/settings");
		}else {
			model.setViewName("redirect:"+MappingConstant.DEFAULT_PAGE);
			redir.addFlashAttribute("SESSION_LOGOUT",CebiConstant.SESSION_LOGOUT);
		}
        return model;
    }
	//DashBoard CHARTS
	@RequestMapping(value = MappingConstant.SHOW_CHART_REPORTS, method = RequestMethod.POST)
    public @ResponseBody List<List<Chart>> retriveDepositReports(HttpServletRequest request) {
            HttpSession session = request.getSession();
            String bank = (String) session.getAttribute("bank");
            List<List<Chart>> showDepositchart = adminReportService.showDepositchart(bank);
            return showDepositchart;
    }
	
	@RequestMapping(value = MappingConstant.ISCRITERIA_TABLES, method = RequestMethod.POST)
    public @ResponseBody  List<String> retriveIsCriteriaTables(HttpServletRequest request) {
            HttpSession session = request.getSession();
            String bank = (String) session.getAttribute("bank");
            List<String> retriveisCriteriaTables = adminReportService.retriveisCriteriaTables(bank);
            return retriveisCriteriaTables;
    }
	//StaticReport
	@RequestMapping(value = "/staticReport", method = RequestMethod.GET)
	public ModelAndView staticReportPage(HttpServletRequest request,RedirectAttributes redir) {
		ModelAndView  mv=new ModelAndView();
		mv.setViewName("staticReport");
		return mv;
		
	}
	
}