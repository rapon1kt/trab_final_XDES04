package com.teo.gestor.application.service.supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.domain.model.SupplierFilter;
import com.teo.gestor.domain.usecases.supplier.FindSuppliersUseCase;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.mapper.SupplierMapper;

@Service
public class FindSuppliersService implements FindSuppliersUseCase {

  private final SupplierRepository supplierRepository;
  private final SupplierMapper supplierMapper;

  public FindSuppliersService(SupplierRepository supplierRepository, SupplierMapper mapper) {
    this.supplierRepository = supplierRepository;
    this.supplierMapper = mapper;
  }

  @Override
  public List<Supplier> handle(SupplierFilter filter, Optional<String> value) {
    SupplierEntity supplierEntity;
    if (value == null || !value.isPresent() || value.isEmpty())
      return this.supplierRepository.findAll().stream().map(this.supplierMapper::toDomain).toList();
    String presentValue = value.get();
    switch (filter) {
      case ENTERPRISENAME:
        return this.supplierRepository.findByEnterpriseName(presentValue).stream().map(supplierMapper::toDomain)
            .toList();
      case CNPJ:
        return this.supplierRepository.findByCnpj(presentValue).stream().map(supplierMapper::toDomain).toList();
      case ID:
        supplierEntity = this.supplierRepository.findById(presentValue)
            .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado!"));
        return List.of(supplierMapper.toDomain(supplierEntity));
      default:
        throw new IllegalArgumentException("Método de filtro não aceitável!");
    }
  }

}
