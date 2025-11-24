package com.teo.gestor.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teo.gestor.domain.model.ReplenishmentStatus;
import com.teo.gestor.infrastructure.persistence.entity.ReplenishmentRequest;

@Repository
public interface ReplenishmentRepository extends MongoRepository<ReplenishmentRequest, String> {
  List<ReplenishmentRequest> findByStatus(ReplenishmentStatus status);
}
