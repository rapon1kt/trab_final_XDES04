package com.teo.gestor.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.product.DeleteProductService;
import com.teo.gestor.application.service.product.FindProductService;
import com.teo.gestor.application.service.product.RegisterProductService;
import com.teo.gestor.application.service.product.UpdateProductService;
import com.teo.gestor.domain.model.ProductFilter;
import com.teo.gestor.presentation.dto.product.RegisterProductRequestDTO;
import com.teo.gestor.presentation.dto.product.UpdateProductRequestDTO;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final RegisterProductService registerProductService;
  private final FindProductService findProductService;
  private final DeleteProductService deleteProductService;
  private final UpdateProductService updateProductService;

  public ProductController(RegisterProductService registerProductService, FindProductService findProductService,
      DeleteProductService deleteProductService, UpdateProductService updateProductService) {
    this.registerProductService = registerProductService;
    this.findProductService = findProductService;
    this.deleteProductService = deleteProductService;
    this.updateProductService = updateProductService;
  }

  @GetMapping
  public ResponseEntity<?> find(@RequestParam("filter") ProductFilter filter,
      @RequestParam("value") Optional<String> value) {
    return ResponseEntity.ok(this.findProductService.handle(filter, value));
  }

  @PostMapping
  public ResponseEntity<?> register(@RequestBody RegisterProductRequestDTO requestDTO) {
    return ResponseEntity.status(201).body(this.registerProductService.handle(requestDTO));
  }

  @PutMapping
  public ResponseEntity<?> update(@RequestParam("id") String id, @RequestBody UpdateProductRequestDTO requestDTO) {
    return ResponseEntity.ok(this.updateProductService.handle(id, requestDTO));
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@RequestParam("id") String id) {
    return ResponseEntity.status(201).body(this.deleteProductService.handle(id));
  }
}
