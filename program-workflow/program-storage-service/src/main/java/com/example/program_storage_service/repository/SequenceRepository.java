package com.example.program_storage_service.repository;

import com.example.program_storage_service.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {
    Sequence findBy_id(String id);
}
