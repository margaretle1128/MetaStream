package com.example.program_storage_service.repository;

import com.example.program_storage_service.model.CommonSchemaProgram;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngestionDBRepository extends MongoRepository<CommonSchemaProgram, String> {
}
