package com.teo.gestor.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.teo.gestor.domain.model.ReplenishmentStatus;

import lombok.Data;

@Data
@Document(collection = "replenishment_requests")
public class ReplenishmentRequest {
  @Id
  private String id;
  private String productId;
  private Integer quantity;
  private ReplenishmentStatus status;
  private Instant createdAt;
  private Instant updatedAt;

  public ReplenishmentRequest(String productId, Integer quantity) {
    this.productId = productId;
    this.quantity = quantity;
    this.status = ReplenishmentStatus.OPEN;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }
}
