package com.teo.gestor.domain.usecases.product;

import com.teo.gestor.presentation.dto.product.RegisterProductRequestDTO;

public interface RegisterProductUseCase {
  public String handle(RegisterProductRequestDTO requestDTO);
}
