package com.teo.gestor.domain.usecases.product;

import java.util.List;
import java.util.Optional;

import com.teo.gestor.domain.model.Product;
import com.teo.gestor.domain.model.ProductFilter;

public interface FindProductUseCase {
  public List<Product> handle(ProductFilter filter, Optional<String> value);
}
