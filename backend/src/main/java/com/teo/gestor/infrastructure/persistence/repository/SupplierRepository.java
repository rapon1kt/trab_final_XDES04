package com.teo.gestor.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;

public interface SupplierRepository extends MongoRepository<SupplierEntity, String> {

  public boolean existsByCnpj(String cnpj);

  public List<SupplierEntity> findByEnterpriseName(String enterpriseName);

  public List<SupplierEntity> findByCnpj(String cnpj);

}