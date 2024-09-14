package com.example.api_service.repository;

import com.example.api_service.model.CommonSchemaProgram;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommonSchemaProgramRepository extends MongoRepository<CommonSchemaProgram, String> {
    List<CommonSchemaProgram> findByCreatedDateAfter(Date date);
    List<CommonSchemaProgram> findByUpdatedDateAfter(Date date);
    List<CommonSchemaProgram> findByUpdatedDateBetween(Date startDate, Date endDate);
}
