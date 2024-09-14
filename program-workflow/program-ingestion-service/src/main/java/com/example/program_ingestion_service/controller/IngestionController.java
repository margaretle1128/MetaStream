package com.example.program_ingestion_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.program_ingestion_service.service.ProgramIngestionService;

@RestController
public class IngestionController {
    private final ProgramIngestionService programIngestionService;

    @Autowired
    public IngestionController(ProgramIngestionService programIngestionService) {
        this.programIngestionService = programIngestionService;
    }

    @GetMapping("/trigger-ingestion")
    public String triggerIngestion() {
        programIngestionService.ingestData();
        return "Ingestion triggered";
    }
}
