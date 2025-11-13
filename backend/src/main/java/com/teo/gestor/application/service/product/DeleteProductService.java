package com.teo.gestor.application.service.product;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.usecases.product.DeleteProductUseCase;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;

@Service
public class DeleteProductService implements DeleteProductUseCase {

  private final ProductRepository productRepository;

  public DeleteProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public String handle(String id) {
    this.productRepository.deleteById(id);
    return "Produto deletado com sucesso!";
  }

}
