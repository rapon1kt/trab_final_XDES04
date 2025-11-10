package com.teo.gestor.application.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teo.gestor.domain.model.Role;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.usecases.user.DeleteUserUseCase;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;

@Service
public class DeleteUserService implements DeleteUserUseCase {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper mapper;

  public DeleteUserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper mapper) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  @Override
  public String handle(String accountId, String password) {
    if (accountId == null || accountId.isEmpty())
      throw new IllegalArgumentException("Informe o id do usuário a ser deletado!");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity adminUserEntity = this.userRepository.findById(auth.getName())
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));
    if (!adminUserEntity.getRole().equals(Role.ADMIN))
      throw new IllegalArgumentException("Você não tem permissão para fazer isso!");
    User adminUser = mapper.toDomain(adminUserEntity);
    if (passwordEncoder.matches(password, adminUser.getPassword())) {
      this.userRepository.deleteById(accountId);
      return "Conta deletada com sucesso!";
    }
    throw new IllegalArgumentException("Senha errada, digite novamente!");
  }

}
