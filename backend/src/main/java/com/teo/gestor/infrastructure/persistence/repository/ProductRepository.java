package com.teo.gestor.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

  Optional<ProductEntity> findByName(String name);

  List<ProductEntity> findByCategoryId(String categoryId);

}
