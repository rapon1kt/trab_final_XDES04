package com.teo.gestor.application.service.supplier;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.domain.usecases.supplier.UpdateSupplierUseCase;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.mapper.SupplierMapper;

@Service
public class UpdateSupplierService implements UpdateSupplierUseCase {

  private final SupplierRepository supplierRepository;
  private final SupplierMapper mapper;

  public UpdateSupplierService(SupplierRepository supplierRepository, SupplierMapper mapper) {
    this.supplierRepository = supplierRepository;
    this.mapper = mapper;
  }

  @Override
  public String handle(String id, String enterpriseName, String email, String phone) {
    SupplierEntity supplierEntity = this.supplierRepository.findById(id)
        .orElseThrow((() -> new IllegalArgumentException("Fornecedor não encontrado!")));
    Supplier supplier = this.mapper.toDomain(supplierEntity);
    supplier.setEnterpriseName(enterpriseName != null ? enterpriseName : supplier.getEnterpriseName());
    supplier.setEmail(email != null ? email : supplier.getEmail());
    supplier.setPhone(phone != null ? phone : supplier.getPhone());
    supplierEntity = this.mapper.toEntity(supplier);
    this.supplierRepository.save(supplierEntity);
    return "Fornecedor foi atualizado com sucesso!";
  }

}
