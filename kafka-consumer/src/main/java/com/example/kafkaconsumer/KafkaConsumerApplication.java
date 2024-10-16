package com.example.kafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class KafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}
}

@Service
@Slf4j
class KafkaMessageConsumer {

	private final AtomicInteger messageCount = new AtomicInteger(0);

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
	public void consume(String message) {
		int count = messageCount.incrementAndGet();
		log.info("Received message: {} | Total messages received: {}", message, count);
	}
}
