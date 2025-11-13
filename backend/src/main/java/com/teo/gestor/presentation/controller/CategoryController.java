package com.teo.gestor.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teo.gestor.application.service.category.CreateCategoryService;
import com.teo.gestor.application.service.category.DeleteCategoryService;
import com.teo.gestor.application.service.category.FindCategoriesService;
import com.teo.gestor.application.service.category.UpdateCategoryService;
import com.teo.gestor.domain.model.CategoryFilter;
import com.teo.gestor.presentation.dto.category.CreateCategoryRequestDTO;
import com.teo.gestor.presentation.dto.product.UpdateProductRequestDTO;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  private final CreateCategoryService createCategoryService;
  private final FindCategoriesService findCategoriesService;
  private final UpdateCategoryService updateCategoryService;
  private final DeleteCategoryService deleteCategoryService;

  public CategoryController(CreateCategoryService createCategoryService, FindCategoriesService findCategoriesService,
      UpdateCategoryService updateCategoryService,
      DeleteCategoryService deleteCategoryService) {
    this.createCategoryService = createCategoryService;
    this.findCategoriesService = findCategoriesService;
    this.updateCategoryService = updateCategoryService;
    this.deleteCategoryService = deleteCategoryService;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody CreateCategoryRequestDTO requestDTO) {
    return ResponseEntity.status(201)
        .body(this.createCategoryService.handle(requestDTO.getName(), requestDTO.getDescription()));
  }

  @GetMapping
  public ResponseEntity<?> find(@RequestParam("filter") CategoryFilter filter,
      @RequestParam("value") Optional<String> value) {
    return ResponseEntity.ok(this.findCategoriesService.handle(filter, value));
  }

  @PatchMapping
  public ResponseEntity<?> update(@RequestParam("id") String id, @RequestBody UpdateProductRequestDTO requestDTO) {
    return ResponseEntity.ok(this.updateCategoryService.handle(id, requestDTO.getName(), requestDTO.getDescription()));
  }

  @DeleteMapping
  public ResponseEntity<?> delete(@RequestParam("id") String id) {
    return ResponseEntity.ok(this.deleteCategoryService.handle(id));
  }

}
