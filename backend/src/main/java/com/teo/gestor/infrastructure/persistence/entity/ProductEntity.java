package com.teo.gestor.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "products")
public class ProductEntity {
  @Id
  private String id;
  private String name;
  private String description;
  private String categoryId;
  private String supplierId;
  private Number quantity;
  private BigDecimal buyPrice;
  private BigDecimal salePrice;
  private Instant validDate;
}
