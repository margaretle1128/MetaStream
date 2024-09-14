package com.example.program_id_mapping_service.repository;

import com.example.program_id_mapping_service.model.RootIdMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RootIdMappingRepository extends MongoRepository<RootIdMapping, String> {
    RootIdMapping findBy_id(String id);
}