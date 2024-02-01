package com.jsn.adevent.reducer.kafka;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.jsn.adevent.reducer.model.AdClickCount;


@Component
public class MessagePublisher {
	Logger logger = LogManager.getLogger(MessagePublisher.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	ObjectMapper mapper = new ObjectMapper();
	
	public void sendMessage(AdClickCount count) {
		String countJson;
		try {
			countJson = mapper.writeValueAsString(count);
			kafkaTemplate.send(KafkaTopicConfig.TOPIC_AD_COUNT, countJson);
		} catch (JsonProcessingException e) {
			logger.error("[sendMessage] Unable to send message to kafka =[" + count + "] due to : " + e.getMessage());
		}
	    
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
