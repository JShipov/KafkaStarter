package com.example.kafkaconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void consume(String message) {
        logger.info("Received message: {}", message);
    }
}
