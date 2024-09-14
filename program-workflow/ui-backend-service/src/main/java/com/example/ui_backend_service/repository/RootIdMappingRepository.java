package com.example.ui_backend_service.repository;

import com.example.ui_backend_service.model.RootIdMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RootIdMappingRepository extends MongoRepository<RootIdMapping, String> {
    RootIdMapping findBy_id(String id);
}