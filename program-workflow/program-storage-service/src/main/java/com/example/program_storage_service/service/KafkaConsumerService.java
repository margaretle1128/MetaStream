package com.example.program_storage_service.service;

import com.example.program_storage_service.model.CommonSchemaProgram;
import com.example.program_storage_service.model.InputProgram;
import com.example.program_storage_service.model.RootIdMapping;
import com.example.program_storage_service.model.Sequence;
import com.example.program_storage_service.repository.RootIdMappingRepository;
import com.example.program_storage_service.repository.SequenceRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KafkaConsumerService {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private RootIdMappingRepository rootIdMappingRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private IngestionDBService ingestionService;

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @KafkaListener(topics = "PROGRAM_ID_MAPPED_DATA", groupId = "program-group")
    public void consume(String message, @Header("kafka_receivedMessageKey") String key) {
        System.out.println("Received message with key: " + key);
        System.out.println("Received message: " + message);
        try {
            InputProgram program = objectMapper.readValue(message, InputProgram.class);

            CommonSchemaProgram commonSchemaProgram = createCommonSchemaProgram(program);

            if (program.getProgramId() == null) {
                Optional<String> rootIdOpt = program.getExternalRefs().stream()
                        .filter(ref -> "rootId".equals(ref.getRefName()))
                        .map(ref -> ref.getId())
                        .findFirst();

                if (rootIdOpt.isPresent()) {
                    String rootId = rootIdOpt.get();
                    RootIdMapping rootIdMapping = rootIdMappingRepository.findBy_id(rootId);

                    if (rootIdMapping != null) {
                        commonSchemaProgram.setProgramId(rootIdMapping.getProgramId());
                    } else {
                        String progType = program.getProgramType();

                        if (progType != null) {
                            String legacyType = mapProgTypeToLegacyType(progType);
                            String newProgramId = generateProgramId(legacyType);
                            commonSchemaProgram.setProgramId(newProgramId);
                            
                            RootIdMapping newMapping = new RootIdMapping();
                            newMapping.set_id(rootId);
                            newMapping.setProgramId(newProgramId);
                            rootIdMappingRepository.save(newMapping);
                        }
                    }
                } else {
                    String progType = program.getProgramType();

                    if (progType != null) {
                        String legacyType = mapProgTypeToLegacyType(progType);
                        String newProgramId = generateProgramId(legacyType);
                        commonSchemaProgram.setProgramId(newProgramId);
                    }
                }
            } else {
                commonSchemaProgram.setProgramId(program.getProgramId());
            }

            commonSchemaProgram.setUpdatedDate(convertToDate(LocalDateTime.now()));

            String json = objectMapper.writeValueAsString(commonSchemaProgram);
            System.out.println("Transformed JSON: " + json);

            kafkaTemplate.send("PROGRAM_DATA", key, json);
            System.out.println("Message sent to PROGRAM_DATA topic with key: " + key);

            saveToIngestionCollection(commonSchemaProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Sequence sequence = sequenceRepository.findBy_id(legacyType);
        if (sequence == null) {
            sequence = new Sequence();
            sequence.set_id(legacyType);
            sequence.setSeq(0);
        }
        int seq = sequence.getSeq() + 1;
        sequence.setSeq(seq);
        sequenceRepository.save(sequence);

        return String.format("%s01%06d0000", legacyType, seq);
    }

    private CommonSchemaProgram createCommonSchemaProgram(InputProgram program) {
        CommonSchemaProgram commonSchemaProgram = new CommonSchemaProgram();

        commonSchemaProgram.setAdvisories(program.getAdvisories());
        commonSchemaProgram.setTitles(copyTitles(program.getTitles()));
        commonSchemaProgram.setDescriptions(copyDescriptions(program.getDescriptions()));
        commonSchemaProgram.setGenres(program.getGenres());
        commonSchemaProgram.setOriginalAirDate(program.getOriginalAirDate());
        commonSchemaProgram.setColorCode(program.getColorCode());
        commonSchemaProgram.setSourceType(program.getSourceType());
        commonSchemaProgram.setOriginalNetwork(copyNetwork(program.getOriginalNetwork()));
        commonSchemaProgram.setSeriesPremiere(program.getSeriesPremiere());
        commonSchemaProgram.setSeasonNumber(program.getSeasonNumber());
        commonSchemaProgram.setOriginalAudiolang(program.getOriginalAudiolang());
        commonSchemaProgram.setReleases(copyReleases(program.getReleases()));
        commonSchemaProgram.setDuration(program.getDuration());
        commonSchemaProgram.setExternalRefs(copyExternalRefs(program.getExternalRefs()));
        commonSchemaProgram.setProviderInfo(copyProviderInfo(program.getProviderInfo()));
        commonSchemaProgram.setCredits(copyCredits(program.getCredits()));
        commonSchemaProgram.setPublished(true);
        commonSchemaProgram.setCountries(copyCountries(program.getCountries()));
        commonSchemaProgram.setProgramType(program.getProgramType());

        return commonSchemaProgram;
    }

    private List<String> copyCountries(List<String> countries) {
        if (countries == null) {
            return null;
        }
        return countries;
    }

    private List<CommonSchemaProgram.Title> copyTitles(List<InputProgram.Title> titles) {
        return titles.stream().map(title -> {
            CommonSchemaProgram.Title newTitle = new CommonSchemaProgram.Title();
            newTitle.setLang(title.getLang());
            newTitle.setValue(title.getValue());
            newTitle.setType(title.getType());
            newTitle.setLength(title.getLength());
            newTitle.setSubtype(title.getSubtype());
            return newTitle;
        }).collect(Collectors.toList());
    }

    private List<CommonSchemaProgram.Description> copyDescriptions(List<InputProgram.Description> descriptions) {
        if (descriptions == null) return null;
        return descriptions.stream().map(description -> {
            CommonSchemaProgram.Description newDescription = new CommonSchemaProgram.Description();
            newDescription.setLang(description.getLang());
            newDescription.setLength(description.getLength());
            newDescription.setType(description.getType());
            newDescription.setValue(description.getValue());
            return newDescription;
        }).collect(Collectors.toList());
    }

    private CommonSchemaProgram.Network copyNetwork(InputProgram.Network network) {
        if (network == null) return null;
        CommonSchemaProgram.Network newNetwork = new CommonSchemaProgram.Network();
        newNetwork.setId(network.getId());
        newNetwork.setValue(network.getValue());
        return newNetwork;
    }

    private List<CommonSchemaProgram.Release> copyReleases(List<InputProgram.Release> releases) {
        if (releases == null) return null;
        return releases.stream().map(release -> {
            CommonSchemaProgram.Release newRelease = new CommonSchemaProgram.Release();
            newRelease.setCountry(release.getCountry());
            newRelease.setType(release.getType());
            newRelease.setDate(release.getDate());
            newRelease.setMedium(release.getMedium());
            newRelease.setChannelId(release.getChannelId());
            return newRelease;
        }).collect(Collectors.toList());
    }

    private List<CommonSchemaProgram.ExternalRef> copyExternalRefs(List<InputProgram.ExternalRef> externalRefs) {
        if (externalRefs == null) return null;
        return externalRefs.stream().map(ref -> {
            CommonSchemaProgram.ExternalRef newRef = new CommonSchemaProgram.ExternalRef();
            newRef.setSystem(ref.getSystem());
            newRef.setRefName(ref.getRefName());
            newRef.setId(ref.getId());
            return newRef;
        }).collect(Collectors.toList());
    }

    private List<CommonSchemaProgram.ProviderInfo> copyProviderInfo(List<InputProgram.ProviderInfo> providerInfo) {
        if (providerInfo == null) return null;
        return providerInfo.stream().map(info -> {
            CommonSchemaProgram.ProviderInfo newInfo = new CommonSchemaProgram.ProviderInfo();
            newInfo.setSystem(info.getSystem());
            newInfo.setKey(info.getKey());
            newInfo.setValue(info.getValue());
            newInfo.setLang(info.getLang());
            return newInfo;
        }).collect(Collectors.toList());
    }

    private List<CommonSchemaProgram.Credit> copyCredits(List<InputProgram.Credit> credits) {
        if (credits == null) return null;
        return credits.stream().map(credit -> {
            CommonSchemaProgram.Credit newCredit = new CommonSchemaProgram.Credit();
            newCredit.setPersonId(credit.getPersonId());
            newCredit.setOrd(credit.getOrd());
            newCredit.setRole(credit.getRole());
            newCredit.setCharacterName(credit.getCharacterName());
            newCredit.setDeparted(credit.getDeparted());
            return newCredit;
        }).collect(Collectors.toList());
    }

    private void saveToIngestionCollection(CommonSchemaProgram commonSchemaProgram) {
        Optional<CommonSchemaProgram> existingProgram = ingestionService.findByProgramId(commonSchemaProgram.getProgramId());
        if (existingProgram.isPresent()) {
            commonSchemaProgram.setCreatedDate(existingProgram.get().getCreatedDate());
            commonSchemaProgram.setUpdatedDate(convertToDate(LocalDateTime.now()));
            ingestionService.update(commonSchemaProgram);
            System.out.println("Update existing program");
        } else {
            commonSchemaProgram.setCreatedDate(convertToDate(LocalDateTime.now()));
            commonSchemaProgram.setUpdatedDate(convertToDate(LocalDateTime.now()));
            ingestionService.save(commonSchemaProgram);
            System.out.println("Create new program");
        }
    }

    private Date convertToDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
