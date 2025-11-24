package com.teo.gestor.presentation.dto;

import java.time.Instant;

import com.teo.gestor.domain.model.ReplenishmentStatus;

public record ReplenishmentResponseDTO(
    String id,
    String productId,
    String productName,
    String supplierName,
    Integer quantity,
    ReplenishmentStatus status,
    Instant createdAt) {

}
