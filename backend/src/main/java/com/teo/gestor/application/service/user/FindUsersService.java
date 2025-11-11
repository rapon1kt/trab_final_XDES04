package com.teo.gestor.application.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Role;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.model.UserFilter;
import com.teo.gestor.domain.usecases.user.FindUsersUseCase;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;

@Service
public class FindUsersService implements FindUsersUseCase {

  private final UserMapper userMapper;
  private final UserRepository userRepository;

  public FindUsersService(UserMapper userMapper, UserRepository userRepository) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
  }

  @Override
  public List<User> handle(UserFilter filter, Optional<String> value) {
    UserEntity userEntity;
    if (value == null || !value.isPresent() || value.isEmpty())
      return this.userRepository.findAll().stream().map(this.userMapper::toDomain).toList();
    String presentValue = value.get();
    switch (filter) {
      case NAME:
        userEntity = this.userRepository.findByName(presentValue)
            .orElseThrow(() -> new IllegalArgumentException("Nenhuma conta encontrada!"));
        return List.of(this.userMapper.toDomain(userEntity));
      case EMAIL:
        userEntity = this.userRepository.findByEmail(presentValue)
            .orElseThrow(() -> new IllegalArgumentException("Nenhuma conta encontrada!"));
        return List.of(this.userMapper.toDomain(userEntity));
      case ROLE:
        return this.userRepository.findByRole(Role.valueOf(presentValue)).stream().map(this.userMapper::toDomain)
            .toList();
      case ALL:
        return this.userRepository.findAll().stream().map(this.userMapper::toDomain).toList();
      default:
        throw new IllegalArgumentException("Método de filtro não aceitável!");
    }
  }

}
