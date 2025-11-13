package com.teo.gestor.application.service.supplier;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.usecases.supplier.DeleteSupplierUseCase;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;

@Service
public class DeleteSupplierService implements DeleteSupplierUseCase {

  private final SupplierRepository supplierRepository;

  public DeleteSupplierService(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  @Override
  public String handle(String id) {
    this.supplierRepository.deleteById(id);
    return "Fornecedor removido com sucesso!";
  }

}
