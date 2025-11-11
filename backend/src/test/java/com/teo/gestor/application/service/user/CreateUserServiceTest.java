package com.teo.gestor.application.service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.domain.model.User;
import com.teo.gestor.infrastructure.config.security.PasswordEncoderService;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

  @Mock
  private UserMapper mapper;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoderService encoder;

  @InjectMocks
  private CreateUserService createUserService;

  @Test
  @DisplayName("Deve criar um novo usuário com sucesso")
  void deveCriarNovoUsuarioComSucesso() {
    String id = "user-id";
    String name = "Test User";
    String email = "test@user.com";
    String rawPassword = "password123";
    String hashedPassword = "hashedPassword123";

    User domainUser = User.create(name, email, hashedPassword);
    UserEntity entity = new UserEntity();
    entity.setId(id);
    entity.setName(name);
    entity.setEmail(email);
    entity.setPassword(hashedPassword);

    when(encoder.encode(rawPassword)).thenReturn(hashedPassword);
    when(userRepository.save(any(UserEntity.class))).thenReturn(entity);
    when(mapper.toEntity(any(User.class))).thenReturn(entity);
    when(mapper.toDomain(entity)).thenReturn(domainUser);

    User result = createUserService.handle(name, email, rawPassword);

    assertThat(result).isNotNull();
    assertThat(result.getEmail()).isEqualTo(email);
    assertThat(result.getPassword()).isEqualTo(hashedPassword);

    ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
    verify(userRepository).save(captor.capture());

    assertThat(captor.getValue().getId()).isEqualTo(id);
    assertThat(captor.getValue().getName()).isEqualTo(name);
    assertThat(captor.getValue().getEmail()).isEqualTo(email);
    assertThat(captor.getValue().getPassword()).isEqualTo(hashedPassword);
  }
}