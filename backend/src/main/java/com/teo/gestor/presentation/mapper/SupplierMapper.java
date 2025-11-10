package com.teo.gestor.presentation.mapper;

import org.mapstruct.Mapper;

import com.teo.gestor.domain.model.Supplier;
import com.teo.gestor.infrastructure.persistence.entity.SupplierEntity;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

  Supplier toDomain(SupplierEntity entity);

  SupplierEntity toEntity(Supplier supplier);

}
