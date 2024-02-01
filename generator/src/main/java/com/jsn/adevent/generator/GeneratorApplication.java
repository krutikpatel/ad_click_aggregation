package com.jsn.adevent.generator;

import java.time.Instant;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.jsn.adevent.generator.kafka.MessagePublisher;
import com.jsn.adevent.generator.model.AdClickEvent;

@SpringBootApplication
public class GeneratorApplication {
	static Logger logger = LogManager.getLogger(GeneratorApplication.class);

	public static void main(String[] args) {
		//SpringApplication.run(GeneratorApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(GeneratorApplication.class, args);

        MessagePublisher producer = context.getBean(MessagePublisher.class);
        Random rand = new Random();
		AdClickEvent event = new AdClickEvent();

		while(true) {
			try {
				//random value between 0 and 100
				int adId = rand.nextInt(3);
				long minutesSinceEpoch = Instant.now().getEpochSecond() / 60;
			
				event.setAdId(adId);
				event.setTimestamp(minutesSinceEpoch);

				producer.sendMessage(event);
				logger.info("Sent message= adId = [" + adId + "] and timestamp = " + minutesSinceEpoch);
				
				Thread.sleep(1000); // Sleep for 5 seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
