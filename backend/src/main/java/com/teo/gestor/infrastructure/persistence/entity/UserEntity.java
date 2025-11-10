package com.teo.gestor.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import com.teo.gestor.domain.model.Role;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserEntity {
  private String id;
  private String name;
  private String email;
  private String password;
  private Role role;
  private boolean active;
  private Instant createdAt;
  private Instant modifiedAt;
}
