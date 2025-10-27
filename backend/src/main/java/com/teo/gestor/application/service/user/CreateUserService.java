package com.teo.gestor.application.service.user;

import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.usecases.CreateUserUseCase;
import com.teo.gestor.infrastructure.config.security.PasswordEncoderService;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;

@Service
public class CreateUserService implements CreateUserUseCase {

  private final UserMapper mapper;
  private final UserRepository userRepository;
  private final PasswordEncoderService passwordEncoderService;

  public CreateUserService(UserMapper mapper, UserRepository userRepository,
      PasswordEncoderService passwordEncoderService) {
    this.mapper = mapper;
    this.userRepository = userRepository;
    this.passwordEncoderService = passwordEncoderService;
  }

  @Override
  public User handle(String name, String email, String password) {
    String hashedPassword = this.passwordEncoderService.encode(password);
    User newUser = User.create(name, email, hashedPassword);
    UserEntity savedUser = this.userRepository.save(mapper.toEntity(newUser));
    return mapper.toDomain(savedUser);
  }

}
