package com.jsn.adevent.generator.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.jsn.adevent.generator.model.AdClickEvent;

@Component
public class MessagePublisher {
	Logger logger = LogManager.getLogger(MessagePublisher.class);
	
	@Autowired
	private KafkaTemplate<String, AdClickEvent> kafkaTemplate;

	//ObjectMapper mapper = new ObjectMapper(); //knote: use org.springframework.kafka.support.serializer.JsonSerializer; for message value
	
	public void sendMessage(AdClickEvent event) {
		//String orderJson = mapper.writeValueAsString(order);
	    kafkaTemplate.send(KafkaTopicConfig.TOPIC_AD_EVENT, event);
	}
	
    /*
	public void sendOrderMessageWithCallback(Order order) {
		//String orderJson = mapper.writeValueAsString(order);
	     CompletableFuture<SendResult<String, Order>> future = kafkaTemplate.send(KafkaTopicConfig.TOPIC_AD_EVENT, order);
	     future.whenComplete((result, ex) -> {
	         if (ex == null) {
	        	 logger.info("Sent message=[" + order + "] with offset=[" + result.getRecordMetadata().offset() + "]");
	         } else {
	        	 logger.error("Unable to send message=[" + 
	        			 order + "] due to : " + ex.getMessage());
	         }
	     });
	}
    */
}
