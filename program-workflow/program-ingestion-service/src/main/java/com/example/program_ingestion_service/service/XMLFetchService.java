package com.example.program_ingestion_service.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class XMLFetchService {
    private static final String XML_DIRECTORY = "/app/src/main/resources/xml/";

    public List<File> fetchXMLFiles() {
        File directory = new File(XML_DIRECTORY);
        System.out.println("Try fetching data");
        
        if (!directory.exists()) {
            System.out.println("Directory does not exist: " + XML_DIRECTORY);
            return new ArrayList<>();
        }
        
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".xml"));
        if (files == null) {
            System.out.println("No XML files found in the directory: " + XML_DIRECTORY);
            return new ArrayList<>();
        }
        
        return List.of(files);
    }
    public String readXMLFile(File file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file.getPath())));
    }
}
