package com.teo.gestor.application.service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.Role;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DeleteUserServiceTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper mapper;

  @InjectMocks
  private DeleteUserService deleteUserService;

  @Mock
  private Authentication authentication;

  @Mock
  private SecurityContext securityContext;

  @BeforeEach
  void setUp() {
    lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  private void setupSecurityContext(String userId) {
    lenient().when(authentication.getName()).thenReturn(userId);
  }

  private UserEntity createMockAdminEntity(String id, String hashedPassword) {
    UserEntity adminEntity = new UserEntity();
    adminEntity.setId(id);
    adminEntity.setRole(Role.ADMIN);
    adminEntity.setPassword(hashedPassword);
    return adminEntity;
  }

  private User createMockAdminDomain(String id, String hashedPassword) {
    User adminDomain = User.create("Admin", "admin@email.com", hashedPassword);
    adminDomain.setId(id);
    adminDomain.setRole(Role.ADMIN);
    return adminDomain;
  }

  @Test
  @DisplayName("ADMIN deve deletar usuário com sucesso se a senha estiver correta")
  void adminDeveDeletarUsuario_QuandoSenhaCorreta() {
    String adminId = "admin-id";
    String adminHashedPass = "hashedAdminPass";
    String adminRawPass = "rawAdminPass123";
    String userIdToDelete = "user-id-123";

    setupSecurityContext(adminId);

    UserEntity adminEntity = createMockAdminEntity(adminId, adminHashedPass);
    User adminDomain = createMockAdminDomain(adminId, adminHashedPass);

    when(userRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));
    when(mapper.toDomain(adminEntity)).thenReturn(adminDomain);
    when(passwordEncoder.matches(adminRawPass, adminHashedPass)).thenReturn(true);

    String result = deleteUserService.handle(userIdToDelete, adminRawPass);

    assertThat(result).isEqualTo("Conta deletada com sucesso!");
    verify(userRepository).deleteById(userIdToDelete);
  }

  @Test
  @DisplayName("Deve lançar exceção se a senha do ADMIN estiver errada")
  void deveLancarExcecao_QuandoSenhaAdminErrada() {
    String adminId = "admin-id";
    String adminHashedPass = "hashedAdminPass";
    String wrongRawPass = "wrongPassword";
    String userIdToDelete = "user-id-123";

    setupSecurityContext(adminId);

    UserEntity adminEntity = createMockAdminEntity(adminId, adminHashedPass);
    User adminDomain = createMockAdminDomain(adminId, adminHashedPass);

    when(userRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));
    when(mapper.toDomain(adminEntity)).thenReturn(adminDomain);
    when(passwordEncoder.matches(wrongRawPass, adminHashedPass)).thenReturn(false);

    assertThatThrownBy(() -> deleteUserService.handle(userIdToDelete, wrongRawPass))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Senha errada, digite novamente!");

    verify(userRepository, never()).deleteById(any());
  }

  @Test
  @DisplayName("Deve lançar exceção se o usuário logado não for ADMIN")
  void deveLancarExcecao_QuandoUsuarioNaoForAdmin() {
    String userId = "user-id";
    String userIdToDelete = "user-id-123";

    setupSecurityContext(userId);

    UserEntity userEntity = new UserEntity(); // Usuário comum, sem Role.ADMIN
    userEntity.setRole(Role.OPERATOR);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

    assertThatThrownBy(() -> deleteUserService.handle(userIdToDelete, "anyPass"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Você não tem permissão para fazer isso!");

    verify(userRepository, never()).deleteById(any());
  }

  @Test
  @DisplayName("Deve lançar exceção se o ID da conta a ser deletada for nulo")
  void deveLancarExcecao_QuandoIdForNulo() {
    assertThatThrownBy(() -> deleteUserService.handle(null, "anyPass"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Informe o id do usuário a ser deletado!");
  }
}