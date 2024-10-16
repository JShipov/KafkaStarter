package com.example.kafkaproducer;

import com.example.kafkaproducer.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class KafkaProducerApplication {

    private final KafkaProducer kafkaProducer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Bean
    public CommandLineRunner sendInitialMessage() {
        return args -> {
            kafkaProducer.sendMessage("my-topic", "Initial message from producer");
            System.out.println("Initial message sent");
        };
    }

    // Отправляем сообщение каждые 5 секунд
    @Scheduled(fixedRate = 5000)
    public void sendPeriodicMessages() {
        String message = "Periodic message at " + System.currentTimeMillis();
        kafkaProducer.sendMessage("my-topic", message);
        System.out.println("Sent: " + message);
    }
}
