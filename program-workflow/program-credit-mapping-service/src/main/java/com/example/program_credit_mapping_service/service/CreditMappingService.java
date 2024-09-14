package com.example.program_credit_mapping_service.service;

import com.example.program_credit_mapping_service.model.TransformedProgram;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditMappingService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CreditMappingService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @KafkaListener(topics = "ADVISORY_MAPPED_DATA", groupId = "credit-mapping-group")
    public void consume(String message, @Header("kafka_receivedMessageKey") String key) {
        System.out.println("Received message with key: " + key);
        System.out.println("Received message: " + message);
        try {
            TransformedProgram transformedProgram = objectMapper.readValue(message, TransformedProgram.class);
            mapCredits(transformedProgram);

            String json = objectMapper.writeValueAsString(transformedProgram);
            System.out.println("Transformed message: " + json);

            kafkaTemplate.send("CREDIT_MAPPED_DATA", key, json);
            System.out.println("Message sent to CREDIT_MAPPED_DATA topic with key: " + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapCredits(TransformedProgram program) {
        if (program.getCredits() == null || program.getCredits().isEmpty()) {
            System.out.println("No credits to map.");
            return;
        }

        var updatedCredits = program.getCredits().stream().map(credit -> {
            TransformedProgram.Credit mappedCredit = new TransformedProgram.Credit();
            mappedCredit.setPersonId(credit.getPersonId());
            mappedCredit.setType(credit.getType());
            mappedCredit.setOrd(credit.getOrd());
            mappedCredit.setRole(credit.getRole());
            mappedCredit.setCharacterName(credit.getCharacterName());
            mappedCredit.setDeparted(credit.getDeparted());
            return mappedCredit;
        }).collect(Collectors.toList());

        program.setCredits(updatedCredits);
    }
}
