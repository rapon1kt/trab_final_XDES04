package com.teo.gestor.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Product {
  private String id;
  private String name;
  private String description;
  private String categoryId;
  private String supplierId;
  private Number quantity;
  private BigDecimal buyPrice;
  private BigDecimal salePrice;
  private Instant validDate;

  public Product() {

  }

  public Product(String id, String name, String description, String categoryId, String supplierId, Number quantity,
      BigDecimal buyPrice, BigDecimal salePrice, Instant validDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.categoryId = categoryId;
    this.supplierId = supplierId;
    this.quantity = quantity;
    this.buyPrice = buyPrice;
    this.salePrice = salePrice;
    this.validDate = validDate;
  }

  public static Product create(String name, String description, String categoryId, String supplierId, Number quantity,
      BigDecimal buyPrice, BigDecimal salePrice) {
    Product newProduct = new Product();
    newProduct.setName(name);
    newProduct.setDescription(description);
    newProduct.setCategoryId(categoryId);
    newProduct.setSupplierId(supplierId);
    newProduct.setQuantity(quantity);
    newProduct.setBuyPrice(buyPrice);
    newProduct.setSalePrice(salePrice);
    return newProduct;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public Instant getValidDate() {
    return validDate;
  }

  public void setValidDate(Instant validDate) {
    this.validDate = validDate;
  }

}
