package com.teo.gestor.presentation.dto;

import java.math.BigDecimal;

public record ReportStockByCategoryDTO(
    String categoryName,
    Integer totalQuantity,
    BigDecimal totalValue) {

}
