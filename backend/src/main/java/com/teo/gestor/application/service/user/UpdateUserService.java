package com.teo.gestor.application.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.usecases.UpdateUserUseCase;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;

@Service
public class UpdateUserService implements UpdateUserUseCase {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper mapper;

  public UpdateUserService(PasswordEncoder encoder, UserRepository userRepository, UserMapper mapper) {
    this.passwordEncoder = encoder;
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  @Override
  public String handle(String userId, String email, String password) {
    if (verifyPermission(userId)) {
      UserEntity userEntity = this.userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
      User user = mapper.toDomain(userEntity);
      if (password != null) {
        user.setPassword(!password.isBlank() ? passwordEncoder.encode(password) : user.getPassword());
      }
      if (email != null) {
        validateEmail(email);
        user.setEmail(!email.isBlank() ? email : user.getEmail());
      }
      this.userRepository.save(mapper.toEntity(user));
      return "Informações alteradas com sucesso!";
    }
    return "Você não tem permissão para fazer isso!";
  }

  private boolean verifyPermission(String userId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN"))
      return true;
    if (userId.equals(auth.getName()))
      return true;
    return false;
  }

  private void validateEmail(String email) {
    if (this.userRepository.existsByEmail(email))
      throw new IllegalArgumentException("Esse e-mail já está registrado.");
  }

}
