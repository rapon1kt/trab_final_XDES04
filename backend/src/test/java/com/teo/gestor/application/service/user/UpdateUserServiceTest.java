package com.teo.gestor.application.service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.User;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper mapper;

  @InjectMocks
  private UpdateUserService updateUserService;

  @Mock
  private Authentication authentication;

  @Mock
  private SecurityContext securityContext;

  @BeforeEach
  void setUp() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  private void setupSecurityContext(String userId, String role) {
    lenient().when(authentication.getName()).thenReturn(userId);
    Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
    lenient().when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);
  }

  @Test
  @DisplayName("ADMIN deve conseguir atualizar qualquer usuário")
  void adminDeveAtualizarQualquerUsuario() {
    String adminId = "admin-id";
    String userIdToUpdate = "user-id-123";
    String newEmail = "new@email.com";
    String newPassword = "newPassword";
    String hashedNewPassword = "hashedNewPassword";

    setupSecurityContext(adminId, "ROLE_ADMIN");

    UserEntity userEntity = new UserEntity();
    User domainUser = User.create("Old Name", "old@email.com", "oldPass");

    when(userRepository.findById(userIdToUpdate)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(domainUser);
    when(userRepository.existsByEmail(newEmail)).thenReturn(false);
    when(passwordEncoder.encode(newPassword)).thenReturn(hashedNewPassword);

    String result = updateUserService.handle(userIdToUpdate, newEmail, newPassword);

    assertThat(result).isEqualTo("Informações alteradas com sucesso!");

    ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
    verify(userRepository).save(captor.capture());

    assertThat(domainUser.getEmail()).isEqualTo(newEmail);
    assertThat(domainUser.getPassword()).isEqualTo(hashedNewPassword);
  }

  @Test
  @DisplayName("Usuário deve conseguir atualizar a si mesmo")
  void usuarioDeveAtualizarASiMesmo() {
    // Arrange
    String userId = "user-id-123";
    String newEmail = "new@email.com";

    setupSecurityContext(userId, "ROLE_USER");

    UserEntity userEntity = new UserEntity();
    User domainUser = User.create("Old Name", "old@email.com", "oldPass");

    when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(domainUser);
    when(userRepository.existsByEmail(newEmail)).thenReturn(false);

    String result = updateUserService.handle(userId, newEmail, null);

    assertThat(result).isEqualTo("Informações alteradas com sucesso!");
    assertThat(domainUser.getEmail()).isEqualTo(newEmail);
    verify(passwordEncoder, never()).encode(any());
  }

  @Test
  @DisplayName("Usuário NÃO deve conseguir atualizar outro usuário")
  void usuarioNaoDeveAtualizarOutroUsuario() {
    String loggedInUserId = "user-id-123";
    String userIdToUpdate = "user-id-456";

    setupSecurityContext(loggedInUserId, "ROLE_USER");

    String result = updateUserService.handle(userIdToUpdate, "email", "pass");

    assertThat(result).isEqualTo("Você não tem permissão para fazer isso!");
    verify(userRepository, never()).findById(any());
    verify(userRepository, never()).save(any());
  }

  @Test
  @DisplayName("Deve lançar exceção se email já existir")
  void deveLancarExcecao_QuandoEmailJaRegistrado() {
    String userId = "user-id-123";
    String newEmail = "existing@email.com";
    setupSecurityContext(userId, "ROLE_USER");

    UserEntity userEntity = new UserEntity();
    User domainUser = User.create("Old Name", "old@email.com", "oldPass");

    when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(domainUser);
    when(userRepository.existsByEmail(newEmail)).thenReturn(true);

    assertThatThrownBy(() -> updateUserService.handle(userId, newEmail, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Esse e-mail já está registrado.");
  }
}