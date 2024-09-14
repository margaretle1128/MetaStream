package com.example.check_and_publish_program_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.check_and_publish_program_service.model.CommonSchemaProgram;
import java.util.Optional;

public interface IngestionDBRepository extends MongoRepository<CommonSchemaProgram, String> {
    Optional<CommonSchemaProgram> findByProgramId(String programId);
}
