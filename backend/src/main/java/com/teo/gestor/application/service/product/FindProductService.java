package com.teo.gestor.application.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Product;
import com.teo.gestor.domain.model.ProductFilter;
import com.teo.gestor.domain.usecases.product.FindProductUseCase;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;
import com.teo.gestor.presentation.mapper.ProductMapper;

@Service
public class FindProductService implements FindProductUseCase {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public FindProductService(ProductRepository productRepository, ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  public List<Product> handle(ProductFilter filter, Optional<String> value) {
    ProductEntity productEntity;
    if (value == null || !value.isPresent() || value.isEmpty())
      return this.productRepository.findAll().stream().map(this.productMapper::toDomain).toList();
    String presentValue = value.get();
    switch (filter) {
      case NAME:
        productEntity = this.productRepository.findByName(presentValue)
            .orElseThrow(() -> new IllegalArgumentException("Não existe nenhum produto com esse nome!"));
        return List.of(productMapper.toDomain(productEntity));
      case CATEGORY:
        return this.productRepository.findByCategoryId(presentValue).stream().map(productMapper::toDomain).toList();
      case ALL:
        return this.productRepository.findAll().stream().map(this.productMapper::toDomain).toList();
      default:
        throw new IllegalArgumentException("Método de filtro não aceitável!");
    }
  }

}
