package com.teo.gestor.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teo.gestor.infrastructure.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

  public Optional<UserEntity> findByName(String name);

  public Optional<UserEntity> findByEmail(String email);

  public boolean existsByEmail(String email);

}
