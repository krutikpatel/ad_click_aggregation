package com.jsn.adevent.mapper.kafka;

import java.util.Properties;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsn.adevent.mapper.mapper.EventMapper;
import com.jsn.adevent.mapper.model.AdClickEvent;

import jakarta.annotation.PostConstruct;

@Component
public class EventConsumer {
	Logger logger = LogManager.getLogger(EventConsumer.class);
	
    public static final String TOPIC_AD_EVENT = "adclickevent";
	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

	ObjectMapper objMapper = new ObjectMapper();

    @Autowired
    EventMapper eventMapper;

	@PostConstruct
	public void init() {
		logger.info("EventConsumer init");

		Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapAddress);
        props.put("group.id", "gp1");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_AD_EVENT));

		//create async thread to poll from kafka and process
		ExecutorService executor = Executors.newFixedThreadPool(1);
		CompletableFuture.runAsync(() -> {
			try {
				int count = 0;
				while (true) {
					//poll from kafka
					ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
					
					for (ConsumerRecord<String, String> record : records) {
						String msg = String.format("[kafka poll] offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
						logger.info(msg);

						//process the message
						try {
							AdClickEvent event = objMapper.readValue(record.value(), AdClickEvent.class);
							logger.info("Received event Message adId = : " + event.getAdId());
							
							//send it to mapper
							eventMapper.sendEvent(event);
						} catch (JsonMappingException e) {
							logger.error("[listenOrdersTopic] exception in parsing order"+e);
						} catch (JsonProcessingException e) {
							logger.error("[listenOrdersTopic] exception in parsing order"+e);
						}

						//commit the offset every 10th message
						if (count % 10 == 0) {
							// Commit specific offset
							Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();
							commitMessage.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
							consumer.commitSync(commitMessage);
							logger.info("committed offset = "+record.offset()+" for topic = "+record.topic()+" partition = "+record.partition()+" value = "+record.value());
						}
						count++;
					}
					//process the message
					
				}
			} finally {
				consumer.close();
			}
		}, executor);
		//polling one at a time and periodically commit the offset

	}

	/*
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
	*/


    /*
     * message fetching way:
     * 
     * Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("my-topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
     * 
     * 
     * committing speicific offsets:
     *  try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

                    // Commit specific offset
                    Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();
                    commitMessage.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                    consumer.commitSync(commitMessage);
                }
            }
        } finally {
            consumer.close();
        }


        ----
        polling one at a time and periodically commit the offset

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("my-topic"));

        try {
            int count = 0;
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

                    count++;
                    if (count % 100 == 0) {
                        // Commit specific offset
                        Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();
                        commitMessage.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                        consumer.commitSync(commitMessage);
                    }
                }
            }
        } finally {
            consumer.close();
        }
        
     */
}
