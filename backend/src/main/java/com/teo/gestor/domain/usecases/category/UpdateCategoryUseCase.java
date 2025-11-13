package com.teo.gestor.domain.usecases.category;

public interface UpdateCategoryUseCase {
  public String handle(String id, String name, String description);
}
