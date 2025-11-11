package com.teo.gestor.application.service.user;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.Role;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.domain.model.UserFilter;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FindUsersServiceTest {

  @Mock
  private UserMapper userMapper;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private FindUsersService findUsersService;

  @Test
  @DisplayName("Deve retornar todos os usuários se o filtro estiver vazio")
  void deveRetornarTodosUsuarios_QuandoFiltroVazio() {
    UserEntity entity1 = new UserEntity();
    User domain1 = User.create("User 1", "email1", "pass");

    when(userRepository.findAll()).thenReturn(List.of(entity1));
    when(userMapper.toDomain(entity1)).thenReturn(domain1);

    List<User> result = findUsersService.handle(UserFilter.NAME, Optional.empty());

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(domain1);
    verify(userRepository).findAll();
  }

  @Test
  @DisplayName("Deve retornar usuário por NOME")
  void deveRetornarUsuarioPorNome() {
    String name = "Test User";
    UserEntity entity = new UserEntity();
    User domain = User.create(name, "email", "pass");

    when(userRepository.findByName(name)).thenReturn(Optional.of(entity));
    when(userMapper.toDomain(entity)).thenReturn(domain);

    List<User> result = findUsersService.handle(UserFilter.NAME, Optional.of(name));

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo(name);
  }

  @Test
  @DisplayName("Deve lançar exceção se NOME não for encontrado")
  void deveLancarExcecao_QuandoNomeNaoEncontrado() {
    String name = "Not Found";
    when(userRepository.findByName(name)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> findUsersService.handle(UserFilter.NAME, Optional.of(name)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Nenhuma conta encontrada!");
  }

  @Test
  @DisplayName("Deve retornar usuários por ROLE")
  void deveRetornarUsuariosPorRole() {
    UserEntity entity = new UserEntity();
    User domain = User.create("Admin", "admin", "pass");

    when(userRepository.findByRole(Role.ADMIN)).thenReturn(List.of(entity));
    when(userMapper.toDomain(entity)).thenReturn(domain);

    List<User> result = findUsersService.handle(UserFilter.ROLE, Optional.of("ADMIN"));

    assertThat(result).hasSize(1);
    verify(userRepository).findByRole(Role.ADMIN);
  }

  @Test
  @DisplayName("Deve lançar exceção se ROLE for inválida")
  void deveLancarExcecao_QuandoRoleInvalida() {
    String invalidRole = "INVALID_ROLE_STRING";

    assertThatThrownBy(() -> findUsersService.handle(UserFilter.ROLE, Optional.of(invalidRole)))
        .isInstanceOf(IllegalArgumentException.class);
  }
}