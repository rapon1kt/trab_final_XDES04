package com.teo.gestor.presentation.dto.product;

import java.math.BigDecimal;

public class RegisterProductRequestDTO {
  private String name;
  private String description;
  private String categoryId;
  private String supplierId;
  private Number quantity;
  private BigDecimal buyPrice;
  private BigDecimal salePrice;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public Number getQuantity() {
    return quantity;
  }

  public void setQuantity(Number quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(BigDecimal buyPrice) {
    this.buyPrice = buyPrice;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

}
