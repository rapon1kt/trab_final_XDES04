package com.teo.gestor.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teo.gestor.infrastructure.persistence.entity.CategoryEntity;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

  Optional<CategoryEntity> findByName(String name);

}
