package com.teo.gestor.presentation.mapper;

import org.mapstruct.Mapper;

import com.teo.gestor.domain.model.Category;
import com.teo.gestor.infrastructure.persistence.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryEntity toEntity(Category category);

  Category toDomain(CategoryEntity categoryEntity);

}
