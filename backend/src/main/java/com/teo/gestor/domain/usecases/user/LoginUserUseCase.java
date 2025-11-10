package com.teo.gestor.domain.usecases.user;

public interface LoginUserUseCase {
  public String handle(String email, String password);
}
