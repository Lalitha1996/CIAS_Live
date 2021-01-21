package com.cias.test;

import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class RabbitMQTest {

	@Test
	public void RabbitMqTest() throws Exception {

		Queue responseQueue = new Queue(UUID.randomUUID().toString(), false, true, true);

		CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
		RabbitAdmin rabbitAdmin = new RabbitAdmin(factory);
		rabbitAdmin.declareQueue(responseQueue);


		RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
		rabbitTemplate.setRoutingKey(responseQueue.getName());

		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(factory);

		CountDownLatch messagesLatch = new CountDownLatch(3);

		/*listenerContainer.setMessageListener(message -> {
			System.out.println(message);
			messagesLatch.countDown();
		});*/

		listenerContainer.setQueues(responseQueue);
		listenerContainer.start();

		rabbitTemplate.convertAndSend("foo");
		rabbitTemplate.convertAndSend("bar");
		rabbitTemplate.convertAndSend("baz");

		assertTrue(messagesLatch.await(10, TimeUnit.SECONDS));
	}

}
