package com.teo.gestor.application.service.category;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Category;
import com.teo.gestor.domain.usecases.category.UpdateCategoryUseCase;
import com.teo.gestor.infrastructure.persistence.entity.CategoryEntity;
import com.teo.gestor.infrastructure.persistence.repository.CategoryRepository;
import com.teo.gestor.presentation.mapper.CategoryMapper;

@Service
public class UpdateCategoryService implements UpdateCategoryUseCase {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public UpdateCategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = mapper;
  }

  public String handle(String id, String name, String description) {
    CategoryEntity userEntity = this.categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada!"));
    Category category = categoryMapper.toDomain(userEntity);
    if (name != null) {
      category.setName(!name.isBlank() ? name : category.getName());
    }
    if (description != null) {
      category.setDescription(!description.isBlank() ? description : category.getDescription());
    }
    this.categoryRepository.save(categoryMapper.toEntity(category));
    return "Informações alteradas com sucesso!";
  }

}
