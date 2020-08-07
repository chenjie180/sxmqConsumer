package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DeadConfig {
	@Bean
	public Queue testDirectQueue() {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("x-dead-letter-exchange", "TestDeadExchange");
		params.put("x-dead-letter-routing-key", "TestDeadRouting");
		return new Queue("TestDeadQueue",true,false,false,params);
	}
	
	
	@Bean
	public Queue testDeadQueue() {
		return new Queue("DeadQueue",true);
				
	}
	
	

}
