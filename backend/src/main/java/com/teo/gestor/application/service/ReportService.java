package com.teo.gestor.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teo.gestor.infrastructure.persistence.entity.CategoryEntity;
import com.teo.gestor.infrastructure.persistence.entity.ProductEntity;
import com.teo.gestor.infrastructure.persistence.repository.CategoryRepository;
import com.teo.gestor.infrastructure.persistence.repository.ProductRepository;
import com.teo.gestor.presentation.dto.ReportStockByCategoryDTO;

@Service
public class ReportService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ReportService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  public List<ReportStockByCategoryDTO> getStockByCategory() {
    List<ProductEntity> products = productRepository.findAll();
    List<CategoryEntity> categories = categoryRepository.findAll();

    Map<String, String> categoryNames = categories.stream()
        .collect(Collectors.toMap(CategoryEntity::getId, CategoryEntity::getName));

    Map<String, List<ProductEntity>> grouped = products.stream()
        .filter(p -> p.getCategoryId() != null)
        .collect(Collectors.groupingBy(ProductEntity::getCategoryId));

    List<ReportStockByCategoryDTO> report = new ArrayList<>();

    grouped.forEach((catId, prodList) -> {
      String catName = categoryNames.getOrDefault(catId, "Sem Categoria");

      int totalQty = prodList.stream()
          .mapToInt(p -> p.getQuantity() != null ? p.getQuantity().intValue() : 0)
          .sum();

      BigDecimal totalVal = prodList.stream()
          .map(p -> {
            BigDecimal price = p.getBuyPrice() != null ? p.getBuyPrice() : BigDecimal.ZERO;
            BigDecimal qty = p.getQuantity() != null ? new BigDecimal(p.getQuantity().toString()) : BigDecimal.ZERO;
            return price.multiply(qty);
          })
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      report.add(new ReportStockByCategoryDTO(catName, totalQty, totalVal));
    });

    return report;
  }
}
