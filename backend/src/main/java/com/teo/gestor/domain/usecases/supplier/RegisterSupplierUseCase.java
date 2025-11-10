package com.teo.gestor.domain.usecases.supplier;

import com.teo.gestor.presentation.dto.supplier.RegisterSupplierRequestDTO;

public interface RegisterSupplierUseCase {
  public String handle(RegisterSupplierRequestDTO requestDTO);
}
