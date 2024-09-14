package com.example.ui_backend_service.repository;

import com.example.ui_backend_service.model.CommonSchemaProgram;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngestionDBRepository extends MongoRepository<CommonSchemaProgram, String> {
    List<CommonSchemaProgram> findByTitles_value(String value);
}
