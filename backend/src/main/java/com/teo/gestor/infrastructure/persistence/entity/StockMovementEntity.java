package com.teo.gestor.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.teo.gestor.domain.model.MovementType;

import lombok.Data;

@Data
@Document(collection = "stock_movements")
public class StockMovementEntity {
  @Id
  private String id;
  private String productId;
  private MovementType type;
  private Integer quantity;
  private String reason;
  private Instant date;
}
