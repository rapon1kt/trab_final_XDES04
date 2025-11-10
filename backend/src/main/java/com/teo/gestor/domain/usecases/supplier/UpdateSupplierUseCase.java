package com.teo.gestor.domain.usecases.supplier;

public interface UpdateSupplierUseCase {
  public String handle(String id, String enterpriseName, String email, String phone);
}
