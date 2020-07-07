package com.cias.configuration;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cias.utility.Constants;

@Configuration
public class RabbitMqConfiguration {
	
	@Bean 
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		//connectionFactory.setUsername("cedgeMQ");
		//connectionFactory.setPassword("Cedge@123");
		//connectionFactory.setConnectionCacheSize(25);
		//connectionFactory.setChannelCacheSize(25);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(connectionFactory());
	}

	@Bean
	public Queue myQueue() {
		return new Queue(Constants.queueName,true, false, false, null);
	}

	@Bean(name = "rabbitListenerContainerFactory")
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerlistenerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConcurrentConsumers(4);
		factory.setPrefetchCount(1);
		factory.setDefaultRequeueRejected(false);
		factory.setConnectionFactory(connectionFactory());
		return factory;
	}

}