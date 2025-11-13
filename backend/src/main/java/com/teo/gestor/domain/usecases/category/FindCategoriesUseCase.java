package com.teo.gestor.domain.usecases.category;

import java.util.List;
import java.util.Optional;

import com.teo.gestor.domain.model.Category;
import com.teo.gestor.domain.model.CategoryFilter;

public interface FindCategoriesUseCase {
  public List<Category> handle(CategoryFilter filter, Optional<String> value);
}
