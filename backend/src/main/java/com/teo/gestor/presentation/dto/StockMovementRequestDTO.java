package com.teo.gestor.presentation.dto;

import com.teo.gestor.domain.model.MovementType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StockMovementRequestDTO(
    @NotBlank(message = "O ID do produto é obrigatório") String productId,

    @NotNull(message = "O tipo de movimentação é obrigatório") MovementType type,

    @NotNull @Min(value = 1, message = "A quantidade deve ser no mínimo 1") Integer quantity,

    @NotBlank(message = "O motivo é obrigatório") String reason) {

}
