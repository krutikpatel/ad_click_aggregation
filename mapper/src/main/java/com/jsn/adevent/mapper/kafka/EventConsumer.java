package com.jsn.adevent.mapper.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsn.adevent.mapper.mapper.EventMapper;
import com.jsn.adevent.mapper.model.AdClickEvent;

@Component
public class EventConsumer {
	Logger logger = LogManager.getLogger(EventConsumer.class);
	
    public static final String TOPIC_AD_EVENT = "adclickevent";

	ObjectMapper objMapper = new ObjectMapper();

    @Autowired
    EventMapper eventMapper;

	@KafkaListener(topics = TOPIC_AD_EVENT, groupId = "gp1")
	public void listenOrdersTopic(String message) {
	    logger.info("Received Message in group foo: " + message);
	    //make json to pojo
	    
		try {
		    AdClickEvent event = objMapper.readValue(message, AdClickEvent.class);
            logger.info("Received event Message adId = : " + event.getAdId());
		    
            //send it to mapper
            eventMapper.sendEvent(event);
		} catch (JsonMappingException e) {
			logger.error("[listenOrdersTopic] exception in parsing order"+e);
		} catch (JsonProcessingException e) {
			logger.error("[listenOrdersTopic] exception in parsing order"+e);
		}
	    
	}
}
