package com.teo.gestor.application.service.category;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Category;
import com.teo.gestor.domain.usecases.category.CreateCategoryUseCase;
import com.teo.gestor.infrastructure.persistence.entity.CategoryEntity;
import com.teo.gestor.infrastructure.persistence.repository.CategoryRepository;
import com.teo.gestor.presentation.mapper.CategoryMapper;

@Service
public class CreateCategoryService implements CreateCategoryUseCase {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper mapper;

  public CreateCategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
    this.categoryRepository = categoryRepository;
    this.mapper = mapper;
  }

  @Override
  public String handle(String name, String description) {
    Category category = new Category();
    category.setName(name);
    category.setDescription(description);
    CategoryEntity entity = mapper.toEntity(category);
    this.categoryRepository.save(entity);
    return "Categoria cadastrado com sucesso!";
  }

}
