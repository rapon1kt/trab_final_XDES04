package com.teo.gestor.application.service.product;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Product;
import com.teo.gestor.domain.usecases.product.UpdateProductUseCase;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;
import com.teo.gestor.presentation.dto.product.UpdateProductRequestDTO;
import com.teo.gestor.presentation.mapper.ProductMapper;

@Service
public class UpdateProductService implements UpdateProductUseCase {

  private final ProductRepository productRepository;
  private final ProductMapper mapper;

  public UpdateProductService(ProductRepository productRepository, ProductMapper mapper) {
    this.productRepository = productRepository;
    this.mapper = mapper;
  }

  public String handle(String id, UpdateProductRequestDTO requestDTO) {
    ProductEntity productEntity = this.productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Produto não foi encontrado!"));
    Product product = mapper.toDomain(productEntity);
    Product updatedProduct = mapper.toUpdatedProduct(requestDTO);
    updatedProduct.setId(product.getId());
    productEntity = mapper.toEntity(updatedProduct);
    this.productRepository.save(productEntity);
    return "Produto atualizado com sucesso!";
  }

}
