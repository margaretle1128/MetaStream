package com.example.program_transformation_service.service;

import com.example.program_transformation_service.model.CommonSchemaProgram;
import com.example.program_transformation_service.model.InputProgram;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerService {

    private final CommonSchemaTransformer transformer;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(CommonSchemaTransformer transformer, KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.transformer = transformer;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @KafkaListener(topics = "RAW_PROGRAM_DATA", groupId = "program-group")
    public void consume(String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);

            JsonNode programsNode = rootNode.path("programs");
            if (programsNode.isMissingNode()) {
                System.err.println("No 'programs' node found in the message.");
                return;
            }

            JsonNode programArrayNode = programsNode.path("program");
            if (programArrayNode.isMissingNode()) {
                System.err.println("No 'program' array found in the 'programs' node.");
                return;
            }

            List<InputProgram.Program> programList = new ArrayList<>();

            if (programArrayNode.isArray()) {
                // Handle array of programs
                programList = objectMapper.readValue(
                        programArrayNode.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, InputProgram.Program.class)
                );
            } else if (programArrayNode.isObject()) {
                // Handle single program object
                InputProgram.Program singleProgram = objectMapper.treeToValue(programArrayNode, InputProgram.Program.class);
                programList.add(singleProgram);
            } else {
                System.err.println("Unexpected 'program' node type: " + programArrayNode.getNodeType());
                return;
            }

            InputProgram inputProgram = new InputProgram();
            InputProgram.Programs programs = new InputProgram.Programs();
            programs.setProgram(programList);
            inputProgram.setPrograms(programs);

            // Process each program
            for (InputProgram.Program program : inputProgram.getPrograms().getProgram()) {
                CommonSchemaProgram commonSchemaProgram = transformer.transform(program);

                String tmsId = program.getTMSId();
                String key = "GRN-" + tmsId;

                String json = objectMapper.writeValueAsString(commonSchemaProgram);
                System.out.println("Transformed JSON: " + json);

                kafkaTemplate.send("TRANSFORMED_PROGRAM_DATA", key, json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
