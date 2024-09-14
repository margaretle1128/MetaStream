package com.example.check_and_publish_program_service.service;

import com.example.check_and_publish_program_service.model.CommonSchemaProgram;
import com.example.check_and_publish_program_service.repository.PublisherDBRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CheckAndPublishProgramService {

    @Autowired
    private PublisherDBRepository publisherDBRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public CheckAndPublishProgramService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @KafkaListener(topics = "PROGRAM_DATA", groupId = "publish-group")
    public void consume(String message, @Header("kafka_receivedMessageKey") String key) {
        System.out.println("Received message with key: " + key);
        System.out.println("Received message: " + message);
        try {
            CommonSchemaProgram program = objectMapper.readValue(message, CommonSchemaProgram.class);

            if (shouldPublish(program)) {
                saveToPublisherDB(program);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldPublish(CommonSchemaProgram program) {
        return program.isPublished();
    }

    private void saveToPublisherDB(CommonSchemaProgram program) {
        Optional<CommonSchemaProgram> existingProgram = publisherDBRepository.findByProgramId(program.getProgramId());
        if (existingProgram.isPresent()) {
            CommonSchemaProgram existing = existingProgram.get();
            program.setCreatedDate(existing.getCreatedDate());
            program.setUpdatedDate(new Date());
            publisherDBRepository.save(program);
            System.out.println("Update existing program in PublisherDB");
        } else {
            program.setCreatedDate(new Date());
            program.setUpdatedDate(new Date());
            publisherDBRepository.save(program);
            System.out.println("Create new program in PublisherDB");
        }
    }
}
