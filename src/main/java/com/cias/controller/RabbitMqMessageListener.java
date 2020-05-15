package com.cias.controller;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cias.dao.CreateCsvDao;
import com.cias.entity.ReportQueueData;
import com.cias.service.AdminReportService;
import com.cias.service.CreateCsvService;
import com.cias.service.PredefineReportService;
import com.cias.utility.CebiConstant;
import com.cias.utility.Constants;

@EnableRabbit
@Component
public class RabbitMqMessageListener {

	@Autowired
	CreateCsvService createCsvService;
	@Autowired
	AdminReportService adminReportService;
	
	@Autowired
	PredefineReportService predefineReportService; 
	
	@Autowired
	CreateCsvDao createCsvDao;

	@RabbitListener(queues = Constants.queueName)
	public void processQueue(Map<String, Object> message) {
		try {
			
			String reporttype = message.get("event").toString();
			int id = (int) message.get("message");
			ReportQueueData reportQueueData = createCsvDao.getReportQueueData(id);
			adminReportService.updatereportStatus(id, CebiConstant.IN_PROCESS);
			reportQueueData.getQueuedataid().setReporttype(reporttype);
			reportQueueData.getQueuedataid().setReportDataId(id);
				adminReportService.buildSqlQuery(reportQueueData.getQueuedataid(),reportQueueData.getBank(), null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} 