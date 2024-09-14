package com.example.program_ingestion_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Object message) {
        String key = "GRN-" + Instant.now().toEpochMilli();
        System.out.println("Sending message with key: " + key + " to topic: " + topic);
        kafkaTemplate.send(topic, key, message);
    }
}