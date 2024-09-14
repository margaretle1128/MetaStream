package com.example.ui_backend_service.service;

import com.example.ui_backend_service.model.CommonSchemaProgram;
import com.example.ui_backend_service.model.RootIdMapping;
import com.example.ui_backend_service.model.Sequence;
import com.example.ui_backend_service.repository.IngestionDBRepository;
import com.example.ui_backend_service.repository.RootIdMappingRepository;
import com.example.ui_backend_service.repository.SequenceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private RootIdMappingRepository rootIdMappingRepository;

    @Autowired
    private IngestionDBService ingestionService;

    @Autowired
    private IngestionDBRepository ingestionDBRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public CommonSchemaProgram processAndSaveProgram(CommonSchemaProgram program, String programType) {
        System.out.println("processAndSaveProgram called");
        String programId = determineProgramId(program, programType);
        System.out.println("Generated Program ID: " + programId);
        program.setProgramId(programId);
        updateTimestamps(program);
        ingestionService.save(program);
        sendProgramDataToKafka(program); 
        return program;
    }

    public CommonSchemaProgram updateProgram(CommonSchemaProgram program) {
        updateTimestamps(program);
        CommonSchemaProgram updatedProgram = ingestionDBRepository.save(program);
        sendProgramDataToKafka(updatedProgram); 
        return updatedProgram;
    }

    public List<CommonSchemaProgram> findAllPrograms() {
        return ingestionDBRepository.findAll();
    }

    public Optional<CommonSchemaProgram> findProgramById(String programId) {
        return ingestionDBRepository.findById(programId);
    }

    public List<CommonSchemaProgram> findProgramsByTitle(String value) {
        return ingestionDBRepository.findByTitles_value(value);
    }
    
    private String determineProgramId(CommonSchemaProgram program, String programType) {
        System.out.println("determineProgramId called with programType fixed: " + programType);
        
        String programId = program.getProgramId();
        System.out.println("testtt");
        if (programId == null) {
            System.out.println("NULL PROGRAM ID");
        } else if (programId.trim().isEmpty()) {
            System.out.println("EMPTY");
        }
        if (programId == null || programId.trim().isEmpty()) {
            return program.getExternalRefs().stream()
                .filter(ref -> "rootId".equals(ref.getRefName()))
                .findFirst()
                .map(ref -> handleExistingRootId(ref.getId(), programType))
                .orElseGet(() -> generateProgramIdBasedOnType(programType));
        }
        
        return programId;
    }

    private String handleExistingRootId(String rootId, String programType) {
        System.out.println("handleExistingRootId called with rootId: " + rootId);
        return rootIdMappingRepository.findById(rootId)
            .map(RootIdMapping::getProgramId)
            .orElseGet(() -> createAndSaveRootIdMapping(rootId, programType));
    }
    
    private String generateProgramIdBasedOnType(String programType) {
        System.out.println("generateProgramIdBasedOnType called with programType: " + programType);
        String legacyType = mapProgTypeToLegacyType(programType);
        return generateProgramId(legacyType);
    }

    private String createAndSaveRootIdMapping(String rootId, String programType) {
        String legacyType = mapProgTypeToLegacyType(programType);
        String programId = generateProgramId(legacyType);
        RootIdMapping newMapping = new RootIdMapping();
        rootIdMappingRepository.save(newMapping);
        return programId;
    }

    private String mapProgTypeToLegacyType(String progType) {
        switch (progType) {
            case "MOVIE":
                return "MV";
            case "SERIES", "SHOW", "SPORT":
                return "SH";
            case "EPISODE":
                return "EP";
            default:
                throw new IllegalArgumentException("Unknown progType: " + progType);
        }
    }

    private String generateProgramId(String legacyType) {
        System.out.println("Generating Program ID for legacyType: " + legacyType);
        Sequence sequence = sequenceRepository.findBy_id(legacyType);
        System.out.println("------SEQUENCE----");
        if (sequence == null) {
            sequence = new Sequence();
            sequence.set_id(legacyType);
            sequence.setSeq(0);
        }
        System.out.println("Sequence is: " + sequence.getSeq());
        int seq = sequence.getSeq() + 1;
        sequence.setSeq(seq);
        System.out.println("Sequence no: " + seq);
        sequenceRepository.save(sequence);
        String generatedProgramId = String.format("%s01%06d0000", legacyType, seq);
        System.out.println("Generated Program ID: " + generatedProgramId);
        return generatedProgramId;
    }    

    private void updateTimestamps(CommonSchemaProgram program) {
        Date now = new Date();
        if (program.getCreatedDate() == null) {
            program.setCreatedDate(now);
        }
        program.setUpdatedDate(now);
    }

    private void sendProgramDataToKafka(CommonSchemaProgram program) {
        String tmsId = program.getExternalRefs().stream()
            .filter(ref -> "gracenote".equalsIgnoreCase(ref.getSystem()) && "TMSId".equalsIgnoreCase(ref.getRefName()))
            .map(ref -> ref.getId())
            .findFirst()
            .orElse(program.getProgramId()); 
    
        String key = "GRN-" + tmsId;
    
        try {
            String json = objectMapper.writeValueAsString(program);
            kafkaTemplate.send("PROGRAM_DATA", key, json);
            System.out.println("Message sent to PROGRAM_DATA topic with key: " + key);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing program data: " + e.getMessage());
        }
    }
    
}
