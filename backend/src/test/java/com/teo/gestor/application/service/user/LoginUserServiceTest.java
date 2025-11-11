package com.teo.gestor.application.service.user;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.teo.gestor.application.service.JWTService;
import com.teo.gestor.domain.model.User;
import com.teo.gestor.infrastructure.persistence.entity.UserEntity;
import com.teo.gestor.infrastructure.persistence.repository.UserRepository;
import com.teo.gestor.presentation.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JWTService jwtService;

  @Mock
  private UserMapper mapper;

  @InjectMocks
  private LoginUserService loginUserService;

  @Test
  @DisplayName("Deve logar com sucesso e retornar um token JWT")
  void deveLogarComSucessoERetornarToken() {
    String email = "test@user.com";
    String password = "password123";
    String userName = "Test User";
    String jwtToken = "mock.jwt.token";

    UserEntity userEntity = new UserEntity();
    User domainUser = User.create(userName, email, "hashedPass");

    Authentication authMock = mock(Authentication.class);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(domainUser);
    when(authMock.isAuthenticated()).thenReturn(true);
    when(authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userName, password))).thenReturn(authMock);
    when(jwtService.generateToken(domainUser)).thenReturn(jwtToken);

    String result = loginUserService.handle(email, password);

    assertThat(result).isEqualTo(jwtToken);
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService).generateToken(domainUser);
  }

  @Test
  @DisplayName("Deve lançar exceção se usuário não for encontrado")
  void deveLancarExcecao_QuandoUsuarioNaoEncontrado() {
    String email = "notfound@user.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> loginUserService.handle(email, "pass"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("This account cannot be found.");
  }

  @Test
  @DisplayName("Deve lançar exceção se autenticação falhar")
  void deveLancarExcecao_QuandoAutenticacaoFalhar() {
    String email = "test@user.com";
    String password = "wrongPassword";
    String userName = "Test User";

    UserEntity userEntity = new UserEntity();
    User domainUser = User.create(userName, email, "hashedPass");

    Authentication authMock = mock(Authentication.class);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(domainUser);
    when(authMock.isAuthenticated()).thenReturn(false);
    when(authenticationManager.authenticate(any())).thenReturn(authMock);

    assertThatThrownBy(() -> loginUserService.handle(email, password))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("This account cannot be found.");
  }
}