package com.example.program_genre_mapping_service.repository;

import com.example.program_genre_mapping_service.model.GenreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<GenreEntity, String> {
}
