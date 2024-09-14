package com.example.check_and_publish_program_service.repository;

import com.example.check_and_publish_program_service.model.CommonSchemaProgram;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PublisherDBRepository extends MongoRepository<CommonSchemaProgram, String> {
    Optional<CommonSchemaProgram> findByProgramId(String programId);
}