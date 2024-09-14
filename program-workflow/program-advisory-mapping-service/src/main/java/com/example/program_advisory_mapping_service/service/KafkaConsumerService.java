package com.example.program_advisory_mapping_service.service;

import com.example.program_advisory_mapping_service.model.AdvisoryEntity;
import com.example.program_advisory_mapping_service.model.SimplifiedProgram;
import com.example.program_advisory_mapping_service.model.TransformedProgram;
import com.example.program_advisory_mapping_service.repository.AdvisoryRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KafkaConsumerService {

    private final KafkaTemplate<String, String> kafkaTemplate; // Use String, String for KafkaTemplate
    private final AdvisoryRepository advisoryRepository;
    private final ObjectMapper objectMapper;
    private Map<String, AdvisoryEntity> advisoryValueMapping;

    @Autowired
    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate, AdvisoryRepository advisoryRepository, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.advisoryRepository = advisoryRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.advisoryValueMapping = new HashMap<>();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        loadAdvisoryMapping();
    }

    @KafkaListener(topics = "GENRE_MAPPED_DATA", groupId = "advisory-mapping-group")
    public void consume(String message, @Header("kafka_receivedMessageKey") String key) {
        System.out.println("Received message with key: " + key);
        System.out.println("Received message: " + message);
        try {
            TransformedProgram transformedProgram = objectMapper.readValue(message, TransformedProgram.class);

            SimplifiedProgram simplifiedProgram = createSimplifiedProgram(transformedProgram);

            String json = objectMapper.writeValueAsString(simplifiedProgram);
            System.out.println("Transformed message: " + json);

            kafkaTemplate.send("ADVISORY_MAPPED_DATA", key, json); 
            System.out.println("Message sent to ADVISORY_MAPPED_DATA topic with key: " + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SimplifiedProgram createSimplifiedProgram(TransformedProgram program) {
        SimplifiedProgram simplifiedProgram = new SimplifiedProgram();

        List<String> advisoryIds = (program.getAdvisories() != null ? program.getAdvisories().stream() : List.<TransformedProgram.Advisory>of().stream())
                .map(advisory -> {
                    String advisoryValue = advisory.getValue().trim().toLowerCase();
                    System.out.println("Processing advisory with value: " + advisoryValue);

                    AdvisoryEntity mappedAdvisoryEntity = advisoryValueMapping.get(advisoryValue);

                    if (mappedAdvisoryEntity != null) {
                        return mappedAdvisoryEntity.getId();
                    } else {
                        System.out.println("No mapping found for advisory with value: " + advisoryValue);
                        printMappingDebugInfo(advisoryValue);
                        return null;
                    }
                })
                .filter(advisoryId -> advisoryId != null)
                .collect(Collectors.toList());

        simplifiedProgram.setAdvisories(advisoryIds);

        simplifiedProgram.setTitles(copyTitles(program.getTitles()));
        simplifiedProgram.setDescriptions(copyDescriptions(program.getDescriptions()));
        simplifiedProgram.setGenres(copyGenres(program.getGenres()));
        simplifiedProgram.setOriginalAirDate(program.getOriginalAirDate());
        simplifiedProgram.setColorCode(program.getColorCode());
        simplifiedProgram.setSourceType(program.getSourceType());
        simplifiedProgram.setOriginalNetwork(copyNetwork(program.getOriginalNetwork()));
        simplifiedProgram.setSeriesPremiere(program.getSeriesPremiere());
        simplifiedProgram.setSeasonNumber(program.getSeasonNumber());
        simplifiedProgram.setOriginalAudiolang(program.getOriginalAudiolang());
        simplifiedProgram.setReleases(copyReleases(program.getReleases()));
        simplifiedProgram.setDuration(program.getDuration());
        simplifiedProgram.setExternalRefs(copyExternalRefs(program.getExternalRefs()));
        simplifiedProgram.setProviderInfo(copyProviderInfo(program.getProviderInfo()));
        simplifiedProgram.setCredits(copyCredits(program.getCredits()));
        simplifiedProgram.setCountries(copyCountries(program.getCountries()));
        simplifiedProgram.setProgramType(program.getProgramType());

        return simplifiedProgram;
    }

    private List<SimplifiedProgram.Title> copyTitles(List<TransformedProgram.Title> titles) {
        return titles.stream().map(title -> {
            SimplifiedProgram.Title newTitle = new SimplifiedProgram.Title();
            newTitle.setLang(title.getLang());
            newTitle.setValue(title.getValue());
            newTitle.setType(title.getType());
            newTitle.setLength(title.getLength());
            newTitle.setSubtype(title.getSubtype());
            return newTitle;
        }).collect(Collectors.toList());
    }

    private List<SimplifiedProgram.Description> copyDescriptions(List<TransformedProgram.Description> descriptions) {
        if (descriptions == null) {
            return null;
        }
        return descriptions.stream().map(description -> {
            SimplifiedProgram.Description newDescription = new SimplifiedProgram.Description();
            newDescription.setLang(description.getLang());
            newDescription.setLength(description.getLength());
            newDescription.setType(description.getType());
            newDescription.setValue(description.getValue());
            return newDescription;
        }).collect(Collectors.toList());
    }

    private List<String> copyCountries(List<String> countries) {
        if (countries == null) {
            return null;
        }
        return countries;
    }

    private List<String> copyGenres(List<String> genres) {
        if (genres == null) {
            return null;
        }
        return genres;
    }
    
    private SimplifiedProgram.Network copyNetwork(TransformedProgram.Network network) {
        if (network == null) return null;
        SimplifiedProgram.Network newNetwork = new SimplifiedProgram.Network();
        newNetwork.setId(network.getId());
        newNetwork.setValue(network.getValue());
        return newNetwork;
    }

    private List<SimplifiedProgram.Release> copyReleases(List<TransformedProgram.Release> releases) {
        if (releases == null) return null;
        return releases.stream().map(release -> {
            SimplifiedProgram.Release newRelease = new SimplifiedProgram.Release();
            newRelease.setCountry(release.getCountry());
            newRelease.setType(release.getType());
            newRelease.setDate(release.getDate());
            newRelease.setMedium(release.getMedium());
            newRelease.setChannelId(release.getChannelId());
            return newRelease;
        }).collect(Collectors.toList());
    }

    private List<SimplifiedProgram.ExternalRef> copyExternalRefs(List<TransformedProgram.ExternalRef> externalRefs) {
        if (externalRefs == null) return null;
        return externalRefs.stream().map(ref -> {
            SimplifiedProgram.ExternalRef newRef = new SimplifiedProgram.ExternalRef();
            newRef.setSystem(ref.getSystem());
            newRef.setRefName(ref.getRefName());
            newRef.setId(ref.getId());
            return newRef;
        }).collect(Collectors.toList());
    }

    private List<SimplifiedProgram.ProviderInfo> copyProviderInfo(List<TransformedProgram.ProviderInfo> providerInfo) {
        if (providerInfo == null) return null;
        return providerInfo.stream().map(info -> {
            SimplifiedProgram.ProviderInfo newInfo = new SimplifiedProgram.ProviderInfo();
            newInfo.setSystem(info.getSystem());
            newInfo.setKey(info.getKey());
            newInfo.setValue(info.getValue());
            newInfo.setLang(info.getLang());
            return newInfo;
        }).collect(Collectors.toList());
    }

    private List<SimplifiedProgram.Credit> copyCredits(List<TransformedProgram.Credit> credits) {
        if (credits == null) return null;
        return credits.stream().map(credit -> {
            SimplifiedProgram.Credit newCredit = new SimplifiedProgram.Credit();
            newCredit.setPersonId(credit.getPersonId());
            newCredit.setOrd(credit.getOrd());
            newCredit.setValue(credit.getValue());
            newCredit.setRole(credit.getRole());
            newCredit.setCharacterName(credit.getCharacterName());
            newCredit.setPrefix(credit.getPrefix());
            newCredit.setFirstName(credit.getFirstName());
            newCredit.setMiddleName(credit.getMiddleName());
            newCredit.setLastName(credit.getLastName());
            newCredit.setSuffix(credit.getSuffix());
            newCredit.setNameId(credit.getNameId());
            newCredit.setDeparted(credit.getDeparted());
            newCredit.setType(credit.getType());
            newCredit.setId(credit.getId());
            return newCredit;
        }).collect(Collectors.toList());
    }

    private void loadAdvisoryMapping() {
        try {
            List<AdvisoryEntity> advisoryEntities = advisoryRepository.findAll();
            System.out.println("Advisory entities loaded: " + advisoryEntities);
            for (AdvisoryEntity advisory : advisoryEntities) {
                for (var name : advisory.getName()) {
                    advisoryValueMapping.put(name.getValue().trim().toLowerCase(), advisory);
                    System.out.println("Loaded mapping: " + name.getValue().trim().toLowerCase() + " -> " + advisory.getId());
                }
            }
            System.out.println("Advisory value mapping loaded: " + advisoryValueMapping);
        } catch (Exception e) {
            System.out.println("Error loading advisory mapping: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printMappingDebugInfo(String advisoryValue) {
        System.out.println("Current advisory value: " + advisoryValue);
        System.out.println("Existing mappings:");
        for (String key : advisoryValueMapping.keySet()) {
            System.out.println("Key: " + key + ", Entity ID: " + advisoryValueMapping.get(key).getId());
        }
    }
}
