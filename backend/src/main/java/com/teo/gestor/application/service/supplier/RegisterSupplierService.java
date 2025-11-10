package com.teo.gestor.application.service.supplier;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Address;
import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.domain.usecases.supplier.RegisterSupplierUseCase;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;
import com.teo.gestor.infrastructure.persistence.repository.SupplierRepository;
import com.teo.gestor.presentation.dto.supplier.RegisterSupplierRequestDTO;
import com.teo.gestor.presentation.mapper.SupplierMapper;

@Service
public class RegisterSupplierService implements RegisterSupplierUseCase {

  private final SupplierRepository supplierRepository;
  private final SupplierMapper supplierMapper;

  public RegisterSupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
    this.supplierRepository = supplierRepository;
    this.supplierMapper = supplierMapper;
  }

  @Override
  public String handle(RegisterSupplierRequestDTO requestDTO) {
    if (this.supplierRepository.existsByCnpj(requestDTO.getCnpj()))
      return "Já existe um fornecedor com esse cnpj!";
    Address address = new Address(requestDTO.getCep(), requestDTO.getState(), requestDTO.getCity(),
        requestDTO.getStreet(), requestDTO.getNumber(), requestDTO.getDistrict());
    Supplier newSupplier = Supplier.create(requestDTO.getEnterpriseName(), requestDTO.getCnpj(), requestDTO.getPhone(),
        requestDTO.getEmail(), address);
    SupplierEntity supplierEntity = this.supplierMapper.toEntity(newSupplier);
    this.supplierRepository.save(supplierEntity);
    return "Fornecedor cadastrado com sucesso!";
  }

}
