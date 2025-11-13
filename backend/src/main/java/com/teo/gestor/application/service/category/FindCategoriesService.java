package com.teo.gestor.application.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Category;
import com.teo.gestor.domain.model.CategoryFilter;
import com.teo.gestor.domain.usecases.category.FindCategoriesUseCase;
import com.teo.gestor.infrastructure.persistence.entity.CategoryEntity;
import com.teo.gestor.infrastructure.persistence.repository.CategoryRepository;
import com.teo.gestor.presentation.mapper.CategoryMapper;

@Service
public class FindCategoriesService implements FindCategoriesUseCase {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public FindCategoriesService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  public List<Category> handle(CategoryFilter filter, Optional<String> value) {
    CategoryEntity categoryEntity;
    if (value == null || !value.isPresent() || value.isEmpty())
      return this.categoryRepository.findAll().stream().map(this.categoryMapper::toDomain).toList();
    String presentValue = value.get();
    switch (filter) {
      case NAME:
        categoryEntity = this.categoryRepository.findByName(presentValue)
            .orElseThrow(() -> new IllegalArgumentException("Não existe nenhum produto com esse nome!"));
        return List.of(categoryMapper.toDomain(categoryEntity));
      case ALL:
        return this.categoryRepository.findAll().stream().map(this.categoryMapper::toDomain).toList();
      default:
        throw new IllegalArgumentException("Método de filtro não aceitável!");
    }
  }

}
