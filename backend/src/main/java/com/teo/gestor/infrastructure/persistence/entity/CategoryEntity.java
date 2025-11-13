package com.teo.gestor.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "categories")
public class CategoryEntity {
  @Id
  private String id;
  private String name;
  private String description;
}
