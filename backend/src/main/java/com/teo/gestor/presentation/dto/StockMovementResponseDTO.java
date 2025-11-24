package com.teo.gestor.presentation.dto;

import java.time.Instant;

import com.teo.gestor.domain.model.MovementType;

public record StockMovementResponseDTO(
    String id,
    String productId,
    String productName,
    MovementType type,
    Integer quantity,
    String reason,
    Instant date) {

}
