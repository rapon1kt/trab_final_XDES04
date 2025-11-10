package com.teo.gestor.application.service.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.teo.gestor.application.service.JWTService;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.usecases.user.LoginUserUseCase;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;

@Service
public class LoginUserService implements LoginUserUseCase {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;
  private final UserMapper mapper;

  public LoginUserService(UserRepository userRepository, AuthenticationManager authenticationManager,
      JWTService jwtService, UserMapper mapper) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.mapper = mapper;
  }

  @Override
  public String handle(String email, String password) {
    UserEntity userEntity = this.userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("This account cannot be found."));
    User user = this.mapper.toDomain(userEntity);
    Authentication authentication = this.authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getName(), password));
    if (!authentication.isAuthenticated())
      throw new IllegalArgumentException("This account cannot be found.");
    return jwtService.generateToken(user);
  }

}
