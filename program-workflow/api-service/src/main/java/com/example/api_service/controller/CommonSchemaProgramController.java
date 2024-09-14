package com.example.api_service.controller;

import com.example.api_service.model.CommonSchemaProgram;
import com.example.api_service.service.CommonSchemaProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/metadata")
public class CommonSchemaProgramController {

    @Autowired
    private CommonSchemaProgramService service;

    @GetMapping("/full-sync")
    public List<CommonSchemaProgram> getFullSyncData(@RequestParam("month") String month) {
        return service.getFullSyncData(month);
    }

    @GetMapping("/delta-sync-between")
    public List<CommonSchemaProgram> getDeltaSyncDataBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return service.getDeltaSyncDataBetween(startDate, endDate);
    }
}
