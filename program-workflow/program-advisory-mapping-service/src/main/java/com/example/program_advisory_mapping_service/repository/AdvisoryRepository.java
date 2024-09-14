package com.example.program_advisory_mapping_service.repository;

import com.example.program_advisory_mapping_service.model.AdvisoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdvisoryRepository extends MongoRepository<AdvisoryEntity, String> {
}
