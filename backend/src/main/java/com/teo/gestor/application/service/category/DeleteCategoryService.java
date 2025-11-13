package com.teo.gestor.application.service.category;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.usecases.category.DeleteCategoryUseCase;
import com.teo.gestor.infrastructure.persistence.repository.CategoryRepository;

@Service
public class DeleteCategoryService implements DeleteCategoryUseCase {

  private final CategoryRepository categoryRepository;

  public DeleteCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public String handle(String id) {
    this.categoryRepository.deleteById(id);
    return "Categoria deletada com sucesso!";
  }

}
