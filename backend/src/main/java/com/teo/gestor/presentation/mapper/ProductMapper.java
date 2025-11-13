package com.teo.gestor.presentation.mapper;

import org.mapstruct.Mapper;

import com.teo.gestor.domain.model.Product;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.presentation.dto.product.UpdateProductRequestDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductEntity toEntity(Product product);

  Product toDomain(ProductEntity productEntity);

  Product toUpdatedProduct(UpdateProductRequestDTO requestDTO);

}
