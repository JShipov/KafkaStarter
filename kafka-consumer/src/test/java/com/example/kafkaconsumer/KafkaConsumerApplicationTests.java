package com.example.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "test-topic" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class KafkaConsumerApplicationTests {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private final BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();

	@KafkaListener(topics = "test-topic", groupId = "test-group")
	public void listen(ConsumerRecord<String, String> record) {
		records.add(record);
	}

	@Test
	void testConsumeMessage() throws InterruptedException {
		kafkaTemplate.send("test-topic", "Test message");

		ConsumerRecord<String, String> received = records.poll(5, TimeUnit.SECONDS);

		assertThat(received).isNotNull();
		assertThat(received.value()).isEqualTo("Test message");
	}
}
