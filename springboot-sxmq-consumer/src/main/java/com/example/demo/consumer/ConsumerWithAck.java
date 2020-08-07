package com.example.demo.consumer;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import com.rabbitmq.client.Channel;



@Controller
public class ConsumerWithAck {

	@RabbitListener(queues = "TestDeadQueue")
	@RabbitHandler
	public void process(Map<String, Object> map,Channel channel,Message message) throws IOException {
		try {
			System.out.println("消费者接受到的信息为："+map);
			int a=6/0;
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
			
//			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);//为true的时候就会重试 
		} catch (Exception e) {
			e.printStackTrace();
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

		}
	}
	
	@RabbitListener(queues = "DeadQueue")
	@RabbitHandler
	public void processDead(Map<String, Object> map,Channel channel,Message message) throws IOException {
		try {
			System.out.println("死信队列接受到的信息为："+map);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

		}
	}
	
	
}
