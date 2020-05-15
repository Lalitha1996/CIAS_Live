package com.cias.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cias.utility.Constants;

@Service
public class EventPublisherService {
	@Autowired
	AmqpTemplate template;

	public void publishEvent(com.cias.rabbitqueue.Events event, Object messages) throws IOException, TimeoutException {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("event", event);
		message.put("message", messages);
		template.convertAndSend(Constants.queueName, message);
	}

}