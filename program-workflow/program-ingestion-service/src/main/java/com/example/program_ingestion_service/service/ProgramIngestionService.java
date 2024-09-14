package com.example.program_ingestion_service.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ProgramIngestionService {

    private final XMLFetchService xmlFetchService;
    private final XMLTransformService xmlTransformService;
    private final KafkaProducerService kafkaProducerService;

    public ProgramIngestionService(XMLFetchService xmlFetchService, XMLTransformService xmlTransformService, KafkaProducerService kafkaProducerService) {
        this.xmlFetchService = xmlFetchService;
        this.xmlTransformService = xmlTransformService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStartup() {
        ingestData();
  
    }

    @Scheduled(fixedRate = 900000)
    public void ingestData() {
        try {
            List<File> xmlFiles = xmlFetchService.fetchXMLFiles();
            for (File xmlFile : xmlFiles) {
                String xmlContent = xmlFetchService.readXMLFile(xmlFile);
                String jsonContent = xmlTransformService.transformXMLToJSON(xmlContent);
                
                System.out.println("JSON File: " + jsonContent);

                kafkaProducerService.sendMessage("RAW_PROGRAM_DATA", jsonContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}