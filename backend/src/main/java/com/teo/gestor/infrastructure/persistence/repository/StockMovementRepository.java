package com.teo.gestor.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teo.gestor.domain.model.MovementType;
import com.teo.gestor.infrastructure.persistence.entity.StockMovementEntity;

@Repository
public interface StockMovementRepository extends MongoRepository<StockMovementEntity, String> {

  List<StockMovementEntity> findByType(MovementType type);

  List<StockMovementEntity> findByProductId(String productId);
}
