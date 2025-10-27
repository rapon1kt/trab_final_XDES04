package com.teo.gestor.presentation.mapper;

import org.mapstruct.Mapper;

import com.teo.gestor.domain.model.User;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserEntity toEntity(User user);

  User toDomain(UserEntity userEntity);

}
