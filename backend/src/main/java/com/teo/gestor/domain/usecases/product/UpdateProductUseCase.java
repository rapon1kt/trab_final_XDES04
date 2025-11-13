package com.teo.gestor.domain.usecases.product;

import com.teo.gestor.presentation.dto.product.UpdateProductRequestDTO;

public interface UpdateProductUseCase {
  public String handle(String id, UpdateProductRequestDTO requestDTO);
}
