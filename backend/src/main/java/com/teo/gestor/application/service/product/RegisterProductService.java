package com.teo.gestor.application.service.product;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Product;
import com.teo.gestor.domain.usecases.product.RegisterProductUseCase;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;
import com.teo.gestor.presentation.dto.product.RegisterProductRequestDTO;
import com.teo.gestor.presentation.mapper.ProductMapper;

@Service
public class RegisterProductService implements RegisterProductUseCase {

  private final ProductRepository productRepository;
  private final ProductMapper mapper;

  public RegisterProductService(ProductRepository productRepository, ProductMapper mapper) {
    this.productRepository = productRepository;
    this.mapper = mapper;
  }

  @Override
  public String handle(RegisterProductRequestDTO requestDTO) {
    Product product = Product.create(requestDTO.getName(), requestDTO.getDescription(), requestDTO.getCategoryId(),
        requestDTO.getSupplierId(), requestDTO.getQuantity(), requestDTO.getBuyPrice(), requestDTO.getSalePrice());
    ProductEntity entity = mapper.toEntity(product);
    this.productRepository.save(entity);
    return "Produto cadastrado com sucesso!";
  }

}
